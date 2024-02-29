package Mudor.servicesimpl;


import Mudor.DTO.ArtistDTO;
import Mudor.DTO.ReleaseDTO;
import Mudor.entity.Artist;
import Mudor.entity.Release;
import Mudor.services.ArtistService;
import Mudor.services.MudorFinderService;

import Mudor.services.ReleaseService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class MudorFinderServiceImpl implements MudorFinderService {

    @Autowired
    ReleaseService releaseService;
    @Autowired
    ArtistService artistService;

    public String searchSingerMusicBrainz(String name) {

        String inputName = name;

        try {

            Thread.sleep(1000);

            String encodedName = URLEncoder.encode(inputName, "UTF-8");

            String searchURL = "http://musicbrainz.org/ws/2/artist/?query=artist:" + encodedName + "&fmt=json";

            RestTemplate restTemplate = new RestTemplate();

            String jsonString = restTemplate.getForObject(searchURL, String.class);

            return jsonString;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Artist Not Found";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Errore while sleeping";
        }
    }


    public String getSingerIdMusicBrainz(String name) {

        String artistId = null;

        try {
            Thread.sleep(1000);

            String artistJson = String.valueOf(searchSingerMusicBrainz(name));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(artistJson);
            JsonNode artistsNode = rootNode.path("artists");

            if (artistsNode.isArray() && artistsNode.size() > 0) {
                JsonNode artistNode = artistsNode.get(0);
                artistId = artistNode.path("id").asText();
            }

        } catch (Exception e) {
            e.printStackTrace();

            return "Error while getting ID artist";
        }
        return artistId;
    }


    public Object searchSingerPageMusicBrainz(String name) {
        String artistJson = null;
        try {

            Thread.sleep(1000);

            String artistId = String.valueOf(getSingerIdMusicBrainz(name));
            String artistUrl = "http://musicbrainz.org/ws/2/artist/" + artistId + "?inc=url-rels+release-groups+genres&fmt=json";

            Thread.sleep(1000);
            RestTemplate restTemplate = new RestTemplate();
            artistJson = restTemplate.getForObject(artistUrl, String.class);

        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return artistJson;
    }


    public Object searchAlbumInStudioPageMusicBrainz(String name) {

        String albumsJson = null;

        try {

            Thread.sleep(1000);

            RestTemplate restTemplate = new RestTemplate();
            String artistId = String.valueOf(getSingerIdMusicBrainz(name));
            String albumURL = "http://musicbrainz.org/ws/2/release?artist=" + artistId + "&type=album&status=official&fmt=json";
            albumsJson = restTemplate.getForObject(albumURL, String.class);


        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        return albumsJson;
    }

    private static boolean isAlbum(JsonNode releaseGroup) {
        if ("Album".equals(releaseGroup.get("primary-type"))) {
            JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");
            if (secondaryTypesNode == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private static boolean isCompilationAlbum(JsonNode releaseGroup) {

        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {

            for (JsonNode typeNode : secondaryTypesNode) {

                if ("Compilation".equals(typeNode.asText())) {

                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isLiveAlbum(JsonNode releaseGroup) {

        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {

            for (JsonNode typeNode : secondaryTypesNode) {

                if ("Live".equals(typeNode.asText())) {

                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isDemoAlbum(JsonNode releaseGroup) {

        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {

            for (JsonNode typeNode : secondaryTypesNode) {

                if ("Demo".equals(typeNode.asText())) {

                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isRemixAlbum(JsonNode releaseGroup) {

        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {

            for (JsonNode typeNode : secondaryTypesNode) {

                if ("Remix".equals(typeNode.asText())) {

                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSingleAlbum(JsonNode releaseGroup) {

        return "Single".equals(releaseGroup.get("primary-type").asText());
    }

    private static boolean isEPAlbum(JsonNode releaseGroup) {

        return "EP".equals(releaseGroup.get("primary-type").asText());
    }

    public Object extractAlbumTitlesMusicBrainz(String name) {

        List<String> albumList = new ArrayList<>();

        try {
            Thread.sleep(1000);
            String singerPageJson = String.valueOf(searchSingerPageMusicBrainz(name));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(singerPageJson);
            JsonNode releaseGroups = jsonNode.get("release-groups");

            for (JsonNode releaseGroup : releaseGroups) {

                String albumTitle = releaseGroup.get("title").asText();
                String releaseDate = releaseGroup.get("first-release-date").asText();
                String albumInfo = releaseDate + " - " + albumTitle;
                albumList.add(albumInfo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collections.sort(albumList);

        return albumList;
    }


    public List<String> getAlbumsInfoMusicBrainz(String name) {

        String idMusicBrainz = null;

        String kind = "Album";

        List<String> idMusicBrainzList = new ArrayList<>();

        List<Release> artistReleaseGroups = releaseService.getReleasesByKindAndArtistsName(kind, name);

        for (Release releaseGroup : artistReleaseGroups) {
            idMusicBrainz = releaseGroup.getIdReleaseGroupMusicBrainz();
            idMusicBrainzList.add(idMusicBrainz);
        }
        List<String> releasesJsonResponses = new ArrayList<>();

        for (String id : idMusicBrainzList) {

            try {
                Thread.sleep(1000);

                RestTemplate restTemplate = new RestTemplate();
                String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release-group/" + id + "?inc=releases&fmt=json", String.class);
                releasesJsonResponses.add(jsonResponse);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        return releasesJsonResponses;
    }

    @Transactional
    public List<String> getAlbumReleasesOfReleaseGroup(String name) {

        List<String> jsonResponses;
        List<String> firstReleasesOfAllAlbums = new ArrayList<>();


        try {

            Thread.sleep(1000);
            jsonResponses = getAlbumsInfoMusicBrainz(name);

            for (String jsonResponse : jsonResponses) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                String idReleaseGroupMusicBrainz = jsonNode.get("id").asText();
                System.out.println(idReleaseGroupMusicBrainz);


                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray releasesArray = jsonObject.getJSONArray("releases");

                if (releasesArray.length() > 0) {

                    JSONObject firstRelease = releasesArray.getJSONObject(0);
                    String firstReleaseId = firstRelease.getString("id");
                    firstReleasesOfAllAlbums.add(firstReleaseId);

                    Release release = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz);
                    release.setIdReleaseMusicBrainz(firstReleaseId);
                    releaseService.updateByEntity(release, release.getIdRelease());
                }
            }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();

        } catch (JsonMappingException jsonMappingException) {
            jsonMappingException.printStackTrace();

        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();

        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return firstReleasesOfAllAlbums;
    }


    @Transactional
    public void mudorConstruct(String name) {

        try {
            Thread.sleep(1000);
            String singerPageJson = String.valueOf(searchSingerPageMusicBrainz(name));
            ObjectMapper artistMapper = new ObjectMapper();
            JsonNode rootNode = artistMapper.readTree(singerPageJson);

            String idMusicBrainz = rootNode.get("id").asText();
            String artistName = rootNode.get("name").asText();
            String country = rootNode.get("area").get("name").asText();
            String disambiguation = rootNode.get("disambiguation").asText();

            List <String> relationUrls = new ArrayList<>();
            JsonNode relationsNode = rootNode.path("relations");
            for (JsonNode relationNode : relationsNode) {
                JsonNode urlNode = relationNode.path("url");
                String resource = urlNode.path("resource").asText();
                relationUrls.add(resource);
            }

            JsonNode genresNode = rootNode.get("genres");
            List<String> genresOfArtist = new ArrayList<>();
            for (JsonNode genereNode : genresNode) {
                String genre = genereNode.get("name").asText();
                genresOfArtist.add(genre);
            }
            List<Artist> artistList = new ArrayList<>();
            List<ReleaseDTO> releaseDTOFakeList = new ArrayList<>();
            ArtistDTO artistDTO = ArtistDTO.builder()
                    .idArtistMusicBrainz(idMusicBrainz)
                    .name(artistName)
                    .relationURLs(relationUrls)
                    .genres(genresOfArtist)
                    .description(disambiguation)
                    .releaseDTOList(releaseDTOFakeList)
                    .country(country)
                    .build();

            if (artistService.getArtistByIdMusicBrainz(idMusicBrainz) != null) {
                Integer id = artistService.getArtistByIdMusicBrainz(idMusicBrainz).getIdArtist();
                artistService.update(artistDTO, id);

            } else {
                Artist artistSaved = artistService.add(artistDTO);
                artistList.add(artistSaved);
            }

            ObjectMapper releaseMapper = new ObjectMapper();
            JsonNode jsonNode = releaseMapper.readTree(singerPageJson);
            JsonNode releaseGroups = jsonNode.get("release-groups");


            List<Release> releaseList = new ArrayList<>();

            for (JsonNode releaseGroup : releaseGroups) {

                String kind = "";

                if (isCompilationAlbum(releaseGroup)) {
                    kind = "Compilation";
                } else if (isLiveAlbum(releaseGroup)) {
                    kind = "Live";
                } else if (isSingleAlbum(releaseGroup)) {
                    kind = "Single";
                } else if (isDemoAlbum(releaseGroup)) {
                    kind = "Demo";
                } else if (isEPAlbum(releaseGroup)) {
                    kind = "EP";
                } else if (isAlbum(releaseGroup)) {
                    kind = "Album";
                } else if (isRemixAlbum(releaseGroup)) {
                    kind = "Remix";
                }

                String albumTitle = releaseGroup.get("title").asText();
                String releaseDate = releaseGroup.get("first-release-date").asText();
                String idReleaseGroupMusicBrainz = releaseGroup.get("id").asText();

                List<String> genresList = new ArrayList<>();

                JsonNode genres = releaseGroup.get("genres");
                for (JsonNode genre : genres) {
                    String genreName = genre.get("name").asText();
                    genresList.add(genreName);
                    System.out.println("Genre: " + genreName);
                }

                List<String> tracksFakeList = new ArrayList<>();
                List<ArtistDTO> artistDTOFakeList = new ArrayList<>();

                ReleaseDTO releaseDTO = ReleaseDTO.builder()
                        .title(albumTitle)
                        .idReleaseGroupMusicBrainz(idReleaseGroupMusicBrainz)
                        .dateOfRelease(releaseDate)
                        .coverArt("")
                        .tracks(tracksFakeList)
                        .kind(kind)
                        .genres(genresList)
                        .artistDTOList(artistDTOFakeList)
                        .build();

                if (releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz) != null) {
                    Integer id = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz).getIdRelease();
                    releaseService.update(releaseDTO, id);

                } else {
                    Release releaseSaved = releaseService.add(releaseDTO);
                    releaseList.add(releaseSaved);
                }
            }

        } catch(JsonMappingException e){
                throw new RuntimeException(e);

            } catch(JsonProcessingException e){
                throw new RuntimeException(e);

            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        public void constructTracksForAlbumsOfArtist (String name){

            List<Release> releasesByArtist = releaseService.getReleasesByArtistName(name);

            List<Release> albumReleaseByArtist = new ArrayList<>();

            for (Release release : releasesByArtist) {
                if (release.getKind().equalsIgnoreCase("Album")) {
                    albumReleaseByArtist.add(release);
                }
            }

            List<String> releasesIds = getAlbumReleasesOfReleaseGroup(name);

            try {

                for (String releaseId : releasesIds) {

                    Thread.sleep(1000);
                    RestTemplate restTemplate = new RestTemplate();
                    String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release/" + releaseId + "?inc=recordings&fmt=json", String.class);

                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    JSONArray mediaArray = jsonObject.getJSONArray("media");
                    List<String> trackTitles = new ArrayList<>();
                    for (int i = 0; i < mediaArray.length(); i++) {

                        //Per ciascun album andiamo a vedere quali sono i relativi brani
                        JSONObject mediaObject = mediaArray.getJSONObject(i);
                        JSONArray tracksArray = mediaObject.getJSONArray("tracks");
                        for (int j = 0; j < tracksArray.length(); j++) {
                            JSONObject trackObject = tracksArray.getJSONObject(j);
                            String title = trackObject.getString("title");
                            System.out.println(title);
                            trackTitles.add(title);


                        }
                    }
                    for (Release release : albumReleaseByArtist) {
                        if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
                            release.setTracks(trackTitles);
                            releaseService.updateByEntity(release, release.getIdRelease());
                        }
                    }
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }

//    public void mudorConstruct(String name) {
//
//        //Definizione del JSON della pagina Music Brainz Dell'Artista
//        String singerPageJson = String.valueOf(searchSingerPageMusicBrainz(name));
//
//        //Formattazione date per il parsin della data
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//
//            //Qui otteniamo i titoli di tutti gli album pubblicati dall'artista
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(singerPageJson);
//            JsonNode releaseGroups = jsonNode.get("release-groups");
//
//            //Per ciascun album ci prendiamo il titolo e la data di rilascio
//            for (JsonNode releaseGroup : releaseGroups) {
//                if (!isCompilationAlbum(releaseGroup) && !isLiveAlbum(releaseGroup) && !isSingleAlbum(releaseGroup) && !isDemoAlbum(releaseGroup)) {
//                    String albumTitle = releaseGroup.get("title").asText();
//                    String releaseDate = releaseGroup.get("first-release-date").asText();
//
//                    //Per ciascun album creiamo un nuovo oggetto album
//                    AlbumInStudioDTO albumInStudioDTO = new AlbumInStudioDTO();
//
//                    //Per ciascun album settiamo qual'è l'id di music brainz dell'artista
//                    albumInStudioDTO.setIdAlbumInStudioMusicBrainz(getSingerIdMusicBrainz(name));
//
//                    //Per ciascun album settiamo il titolo
//                    albumInStudioDTO.setTitoloAlbumInStudio(albumTitle);
//
//                    //Per ciascun album settiamo la data di rilascio
//                    Date date = dateFormat.parse(releaseDate);
//                    albumInStudioDTO.setDataRilascio(date);
//
//                    List<String> genresList = new ArrayList<>();
//
//                    //Per ciascun album andiamo a vedere quali sono i generi
//                    JSONObject jsonGenreObject = new JSONObject(singerPageJson);
//                    JSONArray releaseGroupsArray = jsonGenreObject.getJSONArray("release-groups");
//
//                    for (int i = 0; i < releaseGroupsArray.length(); i++) {
//                        JSONObject releaseGroupObject = releaseGroupsArray.getJSONObject(i);
//                        JSONArray genres = releaseGroupObject.getJSONArray("genres");
//
//                        for (int j = 0; j < genres.length(); j++) {
//                            JSONObject genre = genres.getJSONObject(j);
//                            String genreName = genre.getString("name");
//                            genresList.add(genreName);
//                        }
//                    }
//                    //Per ciascun album settiamo la lista di generi
//                    albumInStudioDTO.setGeneri(genresList);
//
//                    //Per ciascun album andiamo a vedere qual è la release specifica
//                    List<String> releasesIds = getReleasesOfReleaseGroup(name);
//                    for (String releaseId : releasesIds) {
//                        RestTemplate restTemplate = new RestTemplate();
//                        String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release/" + releaseId + "?inc=recordings&fmt=json", String.class);
//
//                        JSONObject jsonObject = new JSONObject(jsonResponse);
//                        JSONArray mediaArray = jsonObject.getJSONArray("media");
//                        List<String> trackTitles = new ArrayList<>();
//                        for (int i = 0; i < mediaArray.length(); i++) {
//
//                            //Per ciascun album andiamo a vedere quali sono i relativi brani
//                            JSONObject mediaObject = mediaArray.getJSONObject(i);
//                            JSONArray tracksArray = mediaObject.getJSONArray("tracks");
//                            for (int j = 0; j < tracksArray.length(); j++) {
//                                JSONObject trackObject = tracksArray.getJSONObject(j);
//                                String title = trackObject.getString("title");
//                                trackTitles.add(title);
//                            }
//                        }
//                        //Per ciascun album settiamo gli stessi brani della relativa release
//                        albumInStudioDTO.setBrani(trackTitles);
//                    }
//                    //Infine salviamo l'album
//                    albumInStudioService.add(albumInStudioDTO);
//                }
//            }
//        } catch (JsonMappingException e) {
//            throw new RuntimeException(e);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}


//        public Object searchDiscographySpecPage (String name){
//            name = getArtistNameFromJson(name);
//            RestTemplate restTemplate = new RestTemplate();
//            String jsonString = "";
//            if (name.contains(" (cantante)")) {
//                name = name.replace(" (cantante)", "");
//            } else if (name.contains("(gruppo musicale)")) {
//                name = name.replace("(gruppo musicale)", "");
//                if (name.contains("The")) {
//                    name = name.replace("The", "");
//                }
//            } else if (name.contains("(rapper)")) {
//                name = name.replace("(rapper)", "");
//            }
//            jsonString = restTemplate.getForObject("https://it.wikipedia.org/w/api.php?action=parse&page=Discografia_di_" + name + "&format=json", String.class);
//            if (jsonString.contains("error")) {
//
//                char firstChar = Character.toLowerCase(name.charAt(0));
//                if (firstChar == 'a' || firstChar == 'e' || firstChar == 'i' || firstChar == 'o' || firstChar == 'u') {
//                    jsonString = restTemplate.getForObject("https://it.wikipedia.org/w/api.php?action=parse&page=Discografia_degli_" + name + "&format=json", String.class);
//                } else {
//                    jsonString = restTemplate.getForObject("https://it.wikipedia.org/w/api.php?action=parse&page=Discografia_dei_" + name + "&format=json", String.class);
//                    if (jsonString.contains("error")) {
//                        jsonString = restTemplate.getForObject("https://it.wikipedia.org/w/api.php?action=parse&page=Discografia_delle_" + name + "&format=json", String.class);
//                    }
//                }
//            }
//            return jsonString;
//        }

//    public Object createJsonForEveryAlbumInStudioOfArtist(String name) {
//        RestTemplate restTemplate = new RestTemplate();
//        List<String> albumsInStudio = extractAlbumTitles(name);
//        List<String> jsonsOfAlbums = null;
//        if (name.contains("(cantante)")){
//            name.replace("(cantante)", "");
//
//        }
//        for (String album : albumsInStudio) {
//            String jsonString = restTemplate.getForObject("https://it.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles=" + album + "_(" + name + ")", String.class);
//            jsonsOfAlbums.add(jsonString);
//        }
//
//        return jsonsOfAlbums;
//    }
//    }
//    public List<AlbumInStudio> popolaDiscografiaConAlbumInStudio (String name) {
//        List<String> albumsInStudio = extractAlbumTitles(name);
//        RestTemplate restTemplate = new RestTemplate();
//        for (String album : albumsInStudio) {
//        }
//        return null;
//    }


