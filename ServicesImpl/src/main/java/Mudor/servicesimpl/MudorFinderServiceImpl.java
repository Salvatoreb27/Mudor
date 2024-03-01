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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    public String searchSingerPageMusicBrainz(String name) {
        String artistJson = null;
        try {


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


    public String searchAlbumInStudioPageMusicBrainz(String name) {

        String albumsJson = null;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String artistId = String.valueOf(getSingerIdMusicBrainz(name));
            Thread.sleep(1000);
            String albumURL = "http://musicbrainz.org/ws/2/release?artist=" + artistId + "&type=album&status=official&fmt=json";
            albumsJson = restTemplate.getForObject(albumURL, String.class);


        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        return albumsJson;
    }

    private static boolean isAlbum(JsonNode releaseGroup) {
        JsonNode primaryTypeNode = releaseGroup.get("primary-type");

        if (primaryTypeNode != null && "Album".equals(primaryTypeNode.textValue())) {
            JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

            if (secondaryTypesNode != null && secondaryTypesNode.size() == 0) {

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

    public List<String> extractAlbumTitlesMusicBrainz(String name) {

        List<String> albumList = new ArrayList<>();

        try {
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
        }

        Collections.sort(albumList);

        return albumList;
    }
    public String getOneReleaseGroupInfo(String title, String artistName) {

        String idMusicBrainz = null;

        Release artistReleaseGroup = releaseService.getReleaseByTitleAndArtistName(title, artistName);
            idMusicBrainz = artistReleaseGroup.getIdReleaseGroupMusicBrainz();

            String jsonResponse = null;

            try {
                Thread.sleep(1000);

                RestTemplate restTemplate = new RestTemplate();
                 jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release-group/" + idMusicBrainz + "?inc=releases&fmt=json", String.class);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

        return jsonResponse;
    }

    public List<String> getAllReleaseGroupsInfo(String name) {

        String idMusicBrainz = null;

        List<String> idMusicBrainzList = new ArrayList<>();


        List<Release> artistReleaseGroups = releaseService.getReleasesByArtistName(name);

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
    public String getOneFirstReleaseOfAReleaseGroupOfAnArtist(String title, String artistName) {

        String jsonResponse;
        String firstReleaseOfAReleaseGroup = null;

        try {

            jsonResponse = getOneReleaseGroupInfo(title, artistName);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                String idReleaseGroupMusicBrainz = jsonNode.get("id").asText();

                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray releasesArray = jsonObject.getJSONArray("releases");

                if (releasesArray.length() > 0) {

                    JSONObject firstRelease = releasesArray.getJSONObject(0);
                    String firstReleaseId = firstRelease.getString("id");
                    firstReleaseOfAReleaseGroup = firstReleaseId;

                    Release release = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz);
                    release.setIdReleaseMusicBrainz(firstReleaseId);
                    releaseService.updateByEntity(release, release.getIdRelease());
                }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();

        } catch (JsonMappingException jsonMappingException) {
            jsonMappingException.printStackTrace();

        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();

        }
        return firstReleaseOfAReleaseGroup;
    }
    @Transactional
    public List<String> getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(String name) {

        List<String> jsonResponses;
        List<String> firstReleasesOfAllAlbums = new ArrayList<>();

        try {

            jsonResponses = getAllReleaseGroupsInfo(name);

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

        }
        return firstReleasesOfAllAlbums;
    }

    public ResponseEntity<String> releaseConstruct (String releaseName, String artistName) {

        try {
            String singerPageJson = String.valueOf(searchSingerPageMusicBrainz(artistName));
            ObjectMapper artistMapper = new ObjectMapper();
            JsonNode rootNode = artistMapper.readTree(singerPageJson);

            String idMusicBrainz = rootNode.get("id").asText();
            String name = rootNode.get("name").asText();
            String country = rootNode.get("area").get("name").asText();
            String disambiguation = rootNode.get("disambiguation").asText();

            List<String> relationUrls = new ArrayList<>();
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
                    .name(name)
                    .relationURLs(relationUrls)
                    .genres(genresOfArtist)
                    .description(disambiguation)
                    .releaseDTOList(releaseDTOFakeList)
                    .country(country)
                    .build();
            Artist artistSaved;
            Artist artistUpdated;
            if (artistService.getArtistByIdMusicBrainz(idMusicBrainz) != null) {
                Integer id = artistService.getArtistByIdMusicBrainz(idMusicBrainz).getIdArtist();
                artistUpdated = artistService.update(artistDTO, id);
                artistList.add(artistUpdated);
            } else {
                artistSaved = artistService.add(artistDTO);
                artistList.add(artistSaved);
            }

            ObjectMapper releaseMapper = new ObjectMapper();
            JsonNode jsonNode = releaseMapper.readTree(singerPageJson);
            JsonNode releaseGroups = jsonNode.get("release-groups");

            Release releaseToAdd = new Release();

            for (JsonNode releaseGroup : releaseGroups) {
                if (releaseGroup.get("title").asText().equalsIgnoreCase(releaseName)) {
                    String kind = "";
                    String albumTitle = releaseGroup.get("title").asText();
                    String releaseDate = releaseGroup.get("first-release-date").asText();
                    String idReleaseGroupMusicBrainz = releaseGroup.get("id").asText();

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

                    Release releaseSaved;

                    if (releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz) != null) {
                        Integer id = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz).getIdRelease();
                        releaseService.update(releaseDTO, id);

                    } else {
                        releaseSaved = releaseService.add(releaseDTO);
                        releaseToAdd = releaseSaved;
                    }
                    releaseToAdd.setArtists(artistList);
                    releaseService.updateByEntity(releaseToAdd, releaseToAdd.getIdRelease());


                    for (Artist artist : artistList) {
                        artist.getReleases().add(releaseToAdd);
                        artistService.updateByEntity(artist, artist.getIdArtist());
                    }
                }


            }
            constructTracksForOneRelease(releaseName, artistName);
            constructCoverArtForOneRelease(releaseName, artistName);

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Release " + releaseName + " dell'artista " + artistName + " aggiunta correttamente");
    }

    public void constructTracksForOneRelease(String title, String artistName) {

        try {
            Release release = releaseService.getReleaseByTitleAndArtistName(title, artistName);

            String releaseId = getOneFirstReleaseOfAReleaseGroupOfAnArtist(title, artistName);


                Thread.sleep(1000);
                RestTemplate restTemplate = new RestTemplate();
                String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release/" + releaseId + "?inc=recordings&fmt=json", String.class);

                List<String> trackTitles = getTrackTitles(jsonResponse);

                    if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
                        if (release.getTracks().size() != trackTitles.size()) {
                            release.setTracks(trackTitles);
                            releaseService.updateByEntity(release, release.getIdRelease());
                        }
                    }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void constructCoverArtForOneRelease(String title, String artistName) {

        Release release = releaseService.getReleaseByTitleAndArtistName(title, artistName);

        String releaseId = getOneFirstReleaseOfAReleaseGroupOfAnArtist(title, artistName);

            String imageUrl = "https://coverartarchive.org/release/" + releaseId + "/front";

                if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
                    if (release.getCoverArt().isEmpty()) {
                        release.setCoverArt(imageUrl);
                        releaseService.updateByEntity(release, release.getIdRelease());
                    }
                }

    }

    @Transactional
    public ResponseEntity<String> mudorConstruct(String name) {

        try {

            String singerPageJson = String.valueOf(searchSingerPageMusicBrainz(name));
            ObjectMapper artistMapper = new ObjectMapper();
            JsonNode rootNode = artistMapper.readTree(singerPageJson);

            String idMusicBrainz = rootNode.get("id").asText();
            String artistName = rootNode.get("name").asText();
            String country = rootNode.get("area").get("name").asText();
            String disambiguation = rootNode.get("disambiguation").asText();

            List<String> relationUrls = new ArrayList<>();
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
            Artist artistSaved;
            Artist artistUpdated;
            if (artistService.getArtistByIdMusicBrainz(idMusicBrainz) != null) {
                Integer id = artistService.getArtistByIdMusicBrainz(idMusicBrainz).getIdArtist();
                artistUpdated = artistService.update(artistDTO, id);
                artistList.add(artistUpdated);

            } else {
                artistSaved = artistService.add(artistDTO);
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

                Release releaseSaved;

                if (releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz) != null) {
                    Integer id = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz).getIdRelease();
                    releaseService.update(releaseDTO, id);

                } else {
                    releaseSaved = releaseService.add(releaseDTO);
                    releaseList.add(releaseSaved);
                }
            }
            for (Release release : releaseList) {
                release.setArtists(artistList);
                releaseService.updateByEntity(release, release.getIdRelease());
            }

            for (Artist artist : artistList) {
                artist.setReleases(releaseList);
                artistService.updateByEntity(artist, artist.getIdArtist());
            }

            constructTracksForArtist(name);
            constructCoverArtForArtist(name);

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);

        }
        return ResponseEntity.ok("Discografia dell'artista " + name + " aggiunta correttamente");
    }

    public void constructTracksForArtist(String name) {

        try {
            List<Release> albumReleaseByArtist = releaseService.getReleasesByArtistName(name);

            List<String> releasesIds = getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(name);


            for (String releaseId : releasesIds) {

                Thread.sleep(1000);
                RestTemplate restTemplate = new RestTemplate();
                String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release/" + releaseId + "?inc=recordings&fmt=json", String.class);

                List<String> trackTitles = getTrackTitles(jsonResponse);
                for (Release release : albumReleaseByArtist) {
                    if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
                        if (release.getTracks().size() != trackTitles.size()) {
                            release.setTracks(trackTitles);
                            releaseService.updateByEntity(release, release.getIdRelease());
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    private static List<String> getTrackTitles(String jsonResponse) {
        List<String> trackTitles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray mediaArray = jsonObject.getJSONArray("media");

            for (int i = 0; i < mediaArray.length(); i++) {

                //Per ciascun album andiamo a vedere quali sono i relativi brani
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                JSONArray tracksArray = mediaObject.getJSONArray("tracks");
                for (int j = 0; j < tracksArray.length(); j++) {
                    JSONObject trackObject = tracksArray.getJSONObject(j);
                    String title = trackObject.getString("title");
                    trackTitles.add(title);
                }
            }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return trackTitles;
    }

    public void constructCoverArtForArtist(String name) {

            List<Release> albumReleaseByArtist = releaseService.getReleasesByArtistName(name);

            List<String> releasesIds = getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(name);

            for (String releaseId : releasesIds) {


                String imageUrl = "https://coverartarchive.org/release/" + releaseId + "/front";

                for (Release release : albumReleaseByArtist) {
                    if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
                        if (release.getCoverArt().isEmpty()) {
                            release.setCoverArt(imageUrl);
                            releaseService.updateByEntity(release, release.getIdRelease());
                        }
                    }
                }
            }
    }
}


