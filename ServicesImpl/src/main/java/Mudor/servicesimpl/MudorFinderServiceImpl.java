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


/**
 * Implementazione del servizio MudorFinderService che fornisce funzionalità per trovare e gestire informazioni su release e artisti.
 * Questa classe fornisce metodi per interagire con le release e gli artisti nel sistema, inclusi metodi per recuperare, aggiungere e aggiornare informazioni.
 */
@Service
public class MudorFinderServiceImpl implements MudorFinderService {

    @Autowired
    ReleaseService releaseService;
    @Autowired
    ArtistService artistService;

    /**
     * Cerca un Artista su MusicBrainz utilizzando il nome specificato.
     *
     * @param name il nome dell'Artista da cercare su MusicBrainz
     * @return una stringa JSON contenente i risultati della ricerca
     * oppure un messaggio di errore se l'artista non viene trovato
     * o se si verifica un errore durante la ricerca
     */
    public String searchArtistMusicBrainz(String name) {
        String inputName = name;

        try {
            // Attende per un secondo prima di effettuare la ricerca
            Thread.sleep(1000);

            // Codifica il nome dell'artista in UTF-8 per l'URL
            String encodedName = URLEncoder.encode(inputName, "UTF-8");

            // Costruisce l'URL di ricerca su MusicBrainz
            String searchURL = "http://musicbrainz.org/ws/2/artist/?query=artist:" + encodedName + "&fmt=json";

            // Utilizza RestTemplate per effettuare la richiesta HTTP GET
            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.getForObject(searchURL, String.class);

            return jsonString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Artist Not Found";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error while sleeping";
        }
    }


    /**
     * Ottiene l'ID MusicBrainz di un Artista utilizzando il nome specificato.
     *
     * @param name il nome dell'artista di cui ottenere l'ID MusicBrainz
     * @return l'ID MusicBrainz dell'Artista, se trovato; altrimenti, restituisce null
     * oppure un messaggio di errore se si verifica un problema durante la ricerca
     */
    public String getArtistIdMusicBrainz(String name) {
        String artistId = null;

        try {
            // Ottiene la stringa JSON dei risultati della ricerca dell'artista
            String artistJson = String.valueOf(searchArtistMusicBrainz(name));

            // Parsa la stringa JSON per ottenere l'ID dell'artista
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(artistJson);
            JsonNode artistsNode = rootNode.path("artists");

            // Se esiste almeno un artista nei risultati, ottiene l'ID del primo artista
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


    /**
     * Cerca la pagina MusicBrainz di un Artista utilizzando il nome specificato.
     *
     * @param name il nome dell' Artista di cui cercare la pagina MusicBrainz
     * @return una stringa JSON contenente le informazioni sulla pagina dell' Artista, se trovata;
     * altrimenti, restituisce null oppure un messaggio di errore se si verifica un problema durante la ricerca
     */
    public String searchArtistPageMusicBrainz(String name) {
        String artistJson = null;
        try {
            // Ottiene l'ID MusicBrainz dell'artista utilizzando il nome specificato
            String artistId = String.valueOf(getArtistIdMusicBrainz(name));

            // Costruisce l'URL per la pagina MusicBrainz dell'artista utilizzando l'ID ottenuto
            String artistUrl = "http://musicbrainz.org/ws/2/artist/" + artistId + "?inc=url-rels+release-groups+genres&fmt=json";

            // Attendere per un secondo per evitare sovraccarichi sulla richiesta HTTP
            Thread.sleep(1000);

            // Effettua una richiesta HTTP per ottenere la stringa JSON della pagina dell'artista
            RestTemplate restTemplate = new RestTemplate();
            artistJson = restTemplate.getForObject(artistUrl, String.class);

        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return artistJson;
    }


    /**
     * Cerca gli album in studio di un artista su MusicBrainz utilizzando il nome specificato.
     *
     * @param name il nome dell'artista di cui cercare gli album in studio
     * @return una stringa JSON contenente le informazioni sugli album in studio dell'artista, se trovati;
     * altrimenti, restituisce null o un messaggio di errore se si verifica un problema durante la ricerca
     */
    public String searchAlbumInStudioPageMusicBrainz(String name) {
        String albumsJson = null;
        try {
            // Ottiene l'ID MusicBrainz dell'artista utilizzando il nome specificato
            RestTemplate restTemplate = new RestTemplate();
            String artistId = String.valueOf(getArtistIdMusicBrainz(name));

            // Attendere per un secondo per evitare sovraccarichi sulla richiesta HTTP
            Thread.sleep(1000);

            // Costruisce l'URL per ottenere gli album in studio dell'artista utilizzando l'ID ottenuto
            String albumURL = "http://musicbrainz.org/ws/2/release?artist=" + artistId + "&type=album&status=official&fmt=json";

            // Effettua una richiesta HTTP per ottenere la stringa JSON degli album in studio dell'artista
            albumsJson = restTemplate.getForObject(albumURL, String.class);

        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return albumsJson;
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un album.
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un album; false altrimenti
     */
    private static boolean isAlbum(JsonNode releaseGroup) {
        JsonNode primaryTypeNode = releaseGroup.get("primary-type");

        // Verifica se il nodo primary-type esiste e se il suo valore è "Album"
        if (primaryTypeNode != null && "Album".equals(primaryTypeNode.textValue())) {
            JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

            // Verifica se il nodo secondary-types esiste e se è vuoto
            if (secondaryTypesNode != null && secondaryTypesNode.size() == 0) {
                return true; // L'elemento è un album se non ha tipi secondari
            } else {
                return false; // L'elemento ha tipi secondari, quindi non è un album
            }
        } else {
            return false; // Il nodo primary-type non esiste o non è "Album"
        }
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un album di raccolta (compilation).
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un album di raccolta (compilation); false altrimenti
     */
    private static boolean isCompilationAlbum(JsonNode releaseGroup) {
        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        // Verifica se il nodo secondary-types esiste ed è un array
        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {
            // Itera attraverso gli elementi del nodo secondary-types
            for (JsonNode typeNode : secondaryTypesNode) {
                // Verifica se uno degli elementi è "Compilation"
                if ("Compilation".equals(typeNode.asText())) {
                    return true; // L'elemento è una compilation
                }
            }
        }
        return false; // Nessun elemento "Compilation" trovato
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un album live.
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un album live; false altrimenti
     */
    private static boolean isLiveAlbum(JsonNode releaseGroup) {
        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        // Verifica se il nodo secondary-types esiste ed è un array
        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {
            // Itera attraverso gli elementi del nodo secondary-types
            for (JsonNode typeNode : secondaryTypesNode) {
                // Verifica se uno degli elementi è "Live"
                if ("Live".equals(typeNode.asText())) {
                    return true; // L'elemento è un album live
                }
            }
        }
        return false; // Nessun elemento "Live" trovato
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un album demo.
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un album demo; false altrimenti
     */
    private static boolean isDemoAlbum(JsonNode releaseGroup) {
        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        // Verifica se il nodo secondary-types esiste ed è un array
        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {
            // Itera attraverso gli elementi del nodo secondary-types
            for (JsonNode typeNode : secondaryTypesNode) {
                // Verifica se uno degli elementi è "Demo"
                if ("Demo".equals(typeNode.asText())) {
                    return true; // L'elemento è un album demo
                }
            }
        }
        return false; // Nessun elemento "Demo" trovato
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un album di remix.
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un album di remix; false altrimenti
     */
    private static boolean isRemixAlbum(JsonNode releaseGroup) {
        JsonNode secondaryTypesNode = releaseGroup.get("secondary-types");

        // Verifica se il nodo secondary-types esiste ed è un array
        if (secondaryTypesNode != null && secondaryTypesNode.isArray()) {
            // Itera attraverso gli elementi del nodo secondary-types
            for (JsonNode typeNode : secondaryTypesNode) {
                // Verifica se uno degli elementi è "Remix"
                if ("Remix".equals(typeNode.asText())) {
                    return true; // L'elemento è un album di remix
                }
            }
        }
        return false; // Nessun elemento "Remix" trovato
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un singolo (album singolo).
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un singolo; false altrimenti
     */
    private static boolean isSingleAlbum(JsonNode releaseGroup) {
        // Verifica se il primary-type del nodo è "Single"
        return "Single".equals(releaseGroup.get("primary-type").asText());
    }


    /**
     * Verifica se il nodo JSON specificato rappresenta un EP (Extended Play).
     *
     * @param releaseGroup il nodo JSON da verificare
     * @return true se il nodo rappresenta un EP; false altrimenti
     */
    private static boolean isEPAlbum(JsonNode releaseGroup) {
        // Verifica se il primary-type del nodo è "EP"
        return "EP".equals(releaseGroup.get("primary-type").asText());
    }


    /**
     * Estrae i titoli degli album associati a un artista dal database MusicBrainz.
     *
     * @param name il nome dell'artista di cui si vogliono estrarre i titoli degli album
     * @return una lista di stringhe contenente i titoli degli album associati all'artista, ordinati per data di rilascio
     */
    public List<String> extractReleasesTitlesMusicBrainz(String name) {
        List<String> albumList = new ArrayList<>();

        try {
            // Ottiene il JSON della pagina dell'artista dal database MusicBrainz
            String singerPageJson = String.valueOf(searchArtistPageMusicBrainz(name));

            // Analizza il JSON per estrarre i titoli degli album
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(singerPageJson);
            JsonNode releaseGroups = jsonNode.get("release-groups");

            // Itera su ciascun release-group per estrarre i titoli degli album e le relative date di rilascio
            for (JsonNode releaseGroup : releaseGroups) {
                String albumTitle = releaseGroup.get("title").asText();
                String releaseDate = releaseGroup.get("first-release-date").asText();
                String albumInfo = releaseDate + " - " + albumTitle;
                albumList.add(albumInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ordina la lista degli album per data di rilascio
        Collections.sort(albumList);

        return albumList;
    }

    /**
     * Ottiene le informazioni su un release-group specifico da MusicBrainz.
     *
     * @param title      il titolo del release-group
     * @param artistName il nome dell'artista associato al release-group
     * @return una stringa JSON contenente le informazioni sul release-group specificato
     */
    public String getOneReleaseGroupInfo(String title, String artistName) {
        String idMusicBrainz = null;

        // Ottiene il release-group associato al titolo e all'artista specificati
        Release artistReleaseGroup = releaseService.getReleaseByTitleAndArtistName(title, artistName);
        idMusicBrainz = artistReleaseGroup.getIdReleaseGroupMusicBrainz();

        String jsonResponse = null;

        try {
            // Introduce un ritardo di 1 secondo per evitare il sovraccarico del server
            Thread.sleep(1000);

            // Effettua una richiesta REST per ottenere le informazioni sul release-group da MusicBrainz
            RestTemplate restTemplate = new RestTemplate();
            jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release-group/" + idMusicBrainz + "?inc=releases&fmt=json", String.class);

        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        return jsonResponse;
    }


    /**
     * Ottiene le informazioni su tutti i release group associati a un determinato artista da MusicBrainz.
     *
     * @param name il nome dell'artista di cui ottenere le informazioni sui release group
     * @return una lista di stringhe JSON contenenti le informazioni sui gruppi di rilascio associati all'artista specificato
     */
    public List<String> getAllReleaseGroupsInfo(String name) {

        String idMusicBrainz = null;

        // Lista per memorizzare gli ID dei gruppi di rilascio associati all'artista
        List<String> idMusicBrainzList = new ArrayList<>();

        // Ottiene tutti i gruppi di rilascio associati all'artista
        List<Release> artistReleaseGroups = releaseService.getReleasesByArtistName(name);

        // Estrae gli ID dei gruppi di rilascio e li aggiunge alla lista
        for (Release releaseGroup : artistReleaseGroups) {
            idMusicBrainz = releaseGroup.getIdReleaseGroupMusicBrainz();
            idMusicBrainzList.add(idMusicBrainz);
        }

        // Lista per memorizzare le risposte JSON ottenute per ciascun release-group
        List<String> releasesJsonResponses = new ArrayList<>();

        // Ottiene le informazioni per ciascun release-group tramite le richieste REST a MusicBrainz
        for (String id : idMusicBrainzList) {
            try {
                // Introduce un ritardo di 1 secondo per evitare il sovraccarico del server
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


    /**
     * Ottiene la prima versione rilasciata della release associata a un determinato titolo e nome dell'artista da MusicBrainz.
     * Aggiorna anche il rilascio nel database con l'ID del primo rilascio ottenuto.
     *
     * @param title      il titolo del release-group
     * @param artistName il nome dell'artista associato al release-group
     * @return l'ID del primo rilascio del release-group, se presente; altrimenti, null
     */
    @Transactional
    public String getOneFirstReleaseOfAReleaseGroupOfAnArtist(String title, String artistName) {

        String jsonResponse;
        String firstReleaseOfAReleaseGroup = null;

        try {
            // Ottiene le informazioni sul release-group
            jsonResponse = getOneReleaseGroupInfo(title, artistName);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Ottiene l'ID del release-group dal JSON ottenuto
            String idReleaseGroupMusicBrainz = jsonNode.get("id").asText();

            // Converti la stringa JSON in un oggetto JSONObject
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Ottiene l'array di rilasci dal JSON
            JSONArray releasesArray = jsonObject.getJSONArray("releases");

            // Verifica se ci sono rilasci nel release-group
            if (releasesArray.length() > 0) {
                // Ottiene il primo rilascio dall'array
                JSONObject firstRelease = releasesArray.getJSONObject(0);
                // Ottiene l'ID del primo rilascio
                String firstReleaseId = firstRelease.getString("id");
                // Imposta l'ID del primo rilascio come valore di ritorno
                firstReleaseOfAReleaseGroup = firstReleaseId;

                // Ottiene il release-group dal repository
                Release release = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz);
                // Aggiorna l'ID del primo rilascio nel release-group nel database
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

    /**
     * Ottiene la prima versione rilasciata (first-release) di tutti i release-group di un artista da MusicBrainz.
     * Aggiorna anche i rilasci nel database con gli ID dei primi rilasci ottenuti.
     *
     * @param name il nome dell'artista di cui ottenere le first-release dei release-group
     * @return una lista di ID dei primi rilasci di tutti i release-group dell'artista
     */
    @Transactional
    public List<String> getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(String name) {

        List<String> jsonResponses;
        List<String> firstReleasesOfAllAlbums = new ArrayList<>();

        try {
            // Ottiene le informazioni su tutti i gruppi di rilascio dell'artista
            jsonResponses = getAllReleaseGroupsInfo(name);

            // Itera su ciascuna risposta JSON per ottenere il primo rilascio di ogni release-group
            for (String jsonResponse : jsonResponses) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                String idReleaseGroupMusicBrainz = jsonNode.get("id").asText();

                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray releasesArray = jsonObject.getJSONArray("releases");

                // Verifica se ci sono rilasci nel release-group
                if (releasesArray.length() > 0) {
                    // Ottiene il primo rilascio dall'array
                    JSONObject firstRelease = releasesArray.getJSONObject(0);
                    // Ottiene l'ID del primo rilascio
                    String firstReleaseId = firstRelease.getString("id");
                    // Aggiunge l'ID del primo rilascio alla lista dei primi rilasci di tutti i gruppi di rilascio
                    firstReleasesOfAllAlbums.add(firstReleaseId);

                    // Ottiene il release-group dal repository
                    Release release = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz);
                    // Aggiorna l'ID del primo rilascio nel release-group nel database
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

    /**
     * Costruisce e aggiunge una nuova release per un determinato artista.
     * Aggiorna anche le informazioni dell'artista e della release nel database.
     *
     * @param releaseName il nome della release da aggiungere
     * @param artistName  il nome dell'artista al quale aggiungere il rilascio
     * @return ResponseEntity con un messaggio che indica il successo o il fallimento dell'operazione
     */
    public ResponseEntity<String> releaseConstruct(String releaseName, String artistName) {

        try {
            // Ottiene le informazioni sull'artista dalla pagina MusicBrainz
            String singerPageJson = String.valueOf(searchArtistPageMusicBrainz(artistName));

            // Mappa le informazioni sull'artista dalla risposta JSON
            ObjectMapper artistMapper = new ObjectMapper();

            JsonNode rootNode = artistMapper.readTree(singerPageJson);

            // Ottiene gli attributi dell'artista
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

            // Costruisce e aggiorna l'entità dell'artista nel database
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

            // Ottiene le informazioni sui release-group dall'artista
            JsonNode releaseGroups = jsonNode.get("release-groups");

            Release releaseToAdd = new Release();

            // Itera su ciascuna release-group per trovare il rilascio specificato
            for (JsonNode releaseGroup : releaseGroups) {
                if (releaseGroup.get("title").asText().equalsIgnoreCase(releaseName)) {
                    String kind = "";

                    // Ottiene le informazioni sulla release
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

                    // Costruisce e aggiorna l'entità release nel database
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
                    // Associa la release all'artista nel database
                    releaseToAdd.setArtists(artistList);
                    releaseService.updateByEntity(releaseToAdd, releaseToAdd.getIdRelease());


                    for (Artist artist : artistList) {
                        artist.getReleases().add(releaseToAdd);
                        artistService.updateByEntity(artist, artist.getIdArtist());
                    }
                }


            }
            // Costruisce e aggiunge le informazioni sulle tracce e le copertine della release
            addTracksForOneRelease(releaseName, artistName);
            addCoverArtForOneRelease(releaseName, artistName);

            // Restituisce una ResponseEntity che indica un errore durante il mapping o l'elaborazione JSON
        } catch (JsonMappingException jsonMappingException) {
            jsonMappingException.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while mapping JSON");

        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while processing JSON");
        }
        // Restituisce una ResponseEntity che indica il successo dell'operazione
        return ResponseEntity.ok("Release --> " + releaseName + " of artist --> " + artistName + " correctly drained and added!");
    }

    /**
     * Aggiunge e aggiorna le tracce per una singola release specificata da titolo e nome dell'artista.
     * Questo metodo recupera le informazioni sulla release specificata dal titolo e dal nome dell'artista,
     * ottiene l'ID della prima versione della release di un gruppo di release dell'artista, e aggiunge o aggiorna le tracce
     * della release in base alle informazioni ottenute dal servizio MusicBrainz.
     *
     * @param title      il titolo della release
     * @param artistName il nome dell'artista associato alla release
     */
    public void addTracksForOneRelease(String title, String artistName) {

        try {
            // Ottiene la release specificata dal titolo e dal nome dell'artista
            Release release = releaseService.getReleaseByTitleAndArtistName(title, artistName);

            // Ottiene l'ID del primo rilascio di un release-group dell'artista
            String releaseId = getOneFirstReleaseOfAReleaseGroupOfAnArtist(title, artistName);

            // Introduce una pausa di 1000 millisecondi per evitare il sovraccarico del servizio MusicBrainz
            Thread.sleep(1000);

            // Utilizza RestTemplate per recuperare le informazioni sulla release dal servizio MusicBrainz
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release/" + releaseId + "?inc=recordings&fmt=json", String.class);

            // Estrae i titoli delle tracce dal JSON ottenuto
            List<String> trackTitles = getTrackTitles(jsonResponse);

            // Verifica se l'ID della release corrisponde all'ID ottenuto e se il numero di tracce è cambiato
            if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
                if (release.getTracks().size() != trackTitles.size()) {

                    // Aggiorna i titoli delle tracce della release e aggiorna la release nel database
                    release.setTracks(trackTitles);
                    releaseService.updateByEntity(release, release.getIdRelease());
                }
            }


        } catch (InterruptedException interruptedException) {
            // Gestisce l'eccezione se il thread viene interrotto durante la pausa
            interruptedException.printStackTrace();
        }

    }

    /**
     * Aggiunge l'immagine di copertina per una singola release specificata dal titolo e nome dell'artista.
     * Questo metodo recupera le informazioni sulla release specificata dal titolo e dal nome dell'artista,
     * ottiene l'ID del primo rilascio di un release-group dell'artista, e aggiunge l'URL dell'immagine
     * di copertina della release se non è già presente nel database.
     *
     * @param title      il titolo della release
     * @param artistName il nome dell'artista associato alla release
     */
    public void addCoverArtForOneRelease(String title, String artistName) {

        // Ottiene la release specificata dal titolo e dal nome dell'artista
        Release release = releaseService.getReleaseByTitleAndArtistName(title, artistName);

        // Ottiene l'ID del primo rilascio di un release-group dell'artista
        String releaseId = getOneFirstReleaseOfAReleaseGroupOfAnArtist(title, artistName);

        // Costruisce l'URL per l'immagine di copertina della release
        String imageUrl = "https://coverartarchive.org/release/" + releaseId + "/front";

        // Verifica se l'ID della release corrisponde all'ID ottenuto e se l'immagine di copertina non è già presente
        if (release.getIdReleaseMusicBrainz().equals(releaseId)) {
            if (release.getCoverArt().isEmpty()) {
                // Aggiorna l'URL dell'immagine di copertina della release e aggiorna la release nel database
                release.setCoverArt(imageUrl);
                releaseService.updateByEntity(release, release.getIdRelease());
            }
        }

    }

    /**
     * Costruisce e aggiorna le informazioni sulla discografia di un artista specificato dal nome.
     * Questo metodo recupera le informazioni sull'artista dal servizio MusicBrainz,
     * incluso l'ID MusicBrainz, il nome, il paese di origine, la descrizione, gli URL delle relazioni e i generi.
     * Successivamente, analizza le informazioni sulle releases dell'artista, aggiornando o aggiungendo
     * le release nel database, insieme alle loro informazioni, come il titolo, la data di rilascio e il tipo.
     * Infine, associa le release agli artisti e viceversa, aggiunge le tracce e le copertine per ciascuna release.
     *
     * @param name il nome dell'artista di cui costruire e aggiornare le informazioni della discografia
     * @return ResponseEntity che indica lo stato dell'operazione di costruzione e aggiornamento delle informazioni della discografia
     */
    @Transactional
    public ResponseEntity<String> discographyConstruct(String name) {

        try {
            // Ottiene le informazioni sull'artista dal servizio MusicBrainz
            String singerPageJson = String.valueOf(searchArtistPageMusicBrainz(name));
            ObjectMapper artistMapper = new ObjectMapper();
            JsonNode rootNode = artistMapper.readTree(singerPageJson);

            // Estrae le informazioni sull'artista
            String idMusicBrainz = rootNode.get("id").asText();
            String artistName = rootNode.get("name").asText();
            String country = rootNode.get("area").get("name").asText();
            String disambiguation = rootNode.get("disambiguation").asText();

            // Estrae gli URL delle relazioni dell'artista
            List<String> relationUrls = new ArrayList<>();
            JsonNode relationsNode = rootNode.path("relations");
            for (JsonNode relationNode : relationsNode) {
                JsonNode urlNode = relationNode.path("url");
                String resource = urlNode.path("resource").asText();
                relationUrls.add(resource);
            }

            // Estrae i generi dell'artista
            JsonNode genresNode = rootNode.get("genres");
            List<String> genresOfArtist = new ArrayList<>();
            for (JsonNode genereNode : genresNode) {
                String genre = genereNode.get("name").asText();
                genresOfArtist.add(genre);
            }


            // Costruisce un oggetto ArtistDTO per l'artista
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

            // Verifica se l'artista esiste già nel database
            if (artistService.getArtistByIdMusicBrainz(idMusicBrainz) != null) {
                Integer id = artistService.getArtistByIdMusicBrainz(idMusicBrainz).getIdArtist();
                artistUpdated = artistService.update(artistDTO, id);
                artistList.add(artistUpdated);

            } else {
                artistSaved = artistService.add(artistDTO);
                artistList.add(artistSaved);
            }

            // Estrae le informazioni sulle release dell'artista
            ObjectMapper releaseMapper = new ObjectMapper();
            JsonNode jsonNode = releaseMapper.readTree(singerPageJson);
            JsonNode releaseGroups = jsonNode.get("release-groups");


            List<Release> releaseList = new ArrayList<>();

            // Itera sulle release dell'artista
            for (JsonNode releaseGroup : releaseGroups) {

                // Determina il tipo di album
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

                // Estrae le informazioni sulla release
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

                // Costruisce un oggetto ReleaseDTO per la release
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

                // Verifica se la release esiste già nel database
                if (releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz) != null) {
                    Integer id = releaseService.getReleaseGroupByIdMusicBrainz(idReleaseGroupMusicBrainz).getIdRelease();
                    releaseService.update(releaseDTO, id);

                } else {
                    releaseSaved = releaseService.add(releaseDTO);
                    releaseList.add(releaseSaved);
                }
            }
            // Associa le release agli artisti e viceversa
            for (Release release : releaseList) {
                release.setArtists(artistList);
                releaseService.updateByEntity(release, release.getIdRelease());
            }

            for (Artist artist : artistList) {
                artist.setReleases(releaseList);
                artistService.updateByEntity(artist, artist.getIdArtist());
            }

            // Aggiunge le tracce e le copertine per ciascuna release
            addTracksForArtist(name);
            addCoverArtForArtist(name);

        } catch (JsonMappingException jsonMappingException) {
            jsonMappingException.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while mapping JSON");

        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while processing JSON");

        }
        // Restituisce una ResponseEntity indicando lo stato dell'operazione
        return ResponseEntity.ok("Discography of artist -->" + name + " correctly drained and added!");
    }

    /**
     * Aggiunge e aggiorna le tracce per un artista specificato dal nome.
     * Questo metodo recupera tutte le release dell'artista dal servizio di gestione delle release,
     * quindi ottiene gli ID delle prime release di tutti i gruppi di rilascio dell'artista.
     * Successivamente, per ciascun ID di release, effettua una richiesta al servizio MusicBrainz per ottenere
     * le informazioni sulle tracce associate a quella release.
     * Se le tracce differiscono da quelle attualmente memorizzate nel database per quella release,
     * aggiorna le tracce nel database.
     *
     * @param name il nome dell'artista di cui aggiornare le tracce delle release
     */
    public void addTracksForArtist(String name) {

        try {
            // Ottiene tutte le release dell'artista dal servizio di gestione delle release
            List<Release> albumReleaseByArtist = releaseService.getReleasesByArtistName(name);

            // Ottiene gli ID delle prime release di tutti i gruppi di rilascio dell'artista
            List<String> releasesIds = getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(name);

            // Itera su tutti gli ID di release
            for (String releaseId : releasesIds) {

                // Introduce una pausa di 1000 millisecondi per evitare il sovraccarico del servizio MusicBrainz
                Thread.sleep(1000);
                RestTemplate restTemplate = new RestTemplate();
                String jsonResponse = restTemplate.getForObject("https://musicbrainz.org/ws/2/release/" + releaseId + "?inc=recordings&fmt=json", String.class);

                // Ottiene i titoli delle tracce dalla risposta JSON
                List<String> trackTitles = getTrackTitles(jsonResponse);

                // Itera su tutte le release dell'artista
                for (Release release : albumReleaseByArtist) {

                    // Verifica se l'ID della release corrisponde all'ID ottenuto
                    if (release.getIdReleaseMusicBrainz().equals(releaseId)) {

                        // Verifica se il numero di tracce è cambiato
                        if (release.getTracks().size() != trackTitles.size()) {

                            // Aggiorna le tracce della release nel database
                            release.setTracks(trackTitles);
                            releaseService.updateByEntity(release, release.getIdRelease());
                        }
                    }
                }
            }
        } catch (InterruptedException interruptedException) {
            // Gestisce l'eccezione se il thread viene interrotto durante la pausa
            interruptedException.printStackTrace();
        }


    }


    /**
     * Ottiene i titoli delle tracce da una risposta JSON ottenuta dal servizio MusicBrainz.
     *
     * @param jsonResponse la risposta JSON ottenuta dal servizio MusicBrainz
     * @return una lista di stringhe contenente i titoli delle tracce
     */
    private static List<String> getTrackTitles(String jsonResponse) {
        List<String> trackTitles = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray mediaArray = jsonObject.getJSONArray("media");

            for (int i = 0; i < mediaArray.length(); i++) {

                //Per ciascuna prima versione della release cercata otteniamo le tracce
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                JSONArray tracksArray = mediaObject.getJSONArray("tracks");
                for (int j = 0; j < tracksArray.length(); j++) {
                    JSONObject trackObject = tracksArray.getJSONObject(j);
                    String title = trackObject.getString("title");
                    trackTitles.add(title);
                }
            }

        } catch (JSONException jsonException) {
            // Gestisce l'eccezione se si verifica un errore durante il parsing del JSON
            jsonException.printStackTrace();
        }
        return trackTitles;
    }

    /**
     * Aggiunge l'immagine di copertina per un artista specificato dal nome.
     * Questo metodo recupera tutte le release dell'artista dal servizio di gestione delle release,
     * quindi ottiene gli ID delle prime release di tutti i gruppi di rilascio dell'artista.
     * Successivamente, per ciascun ID di release, costruisce l'URL dell'immagine di copertina
     * e aggiorna la copertina della release nel database se non è già presente.
     *
     * @param name il nome dell'artista di cui aggiungere l'immagine di copertina per le release
     */
    public void addCoverArtForArtist(String name) {

        // Ottiene tutte le release dell'artista dal servizio di gestione delle release
        List<Release> albumReleaseByArtist = releaseService.getReleasesByArtistName(name);

        // Ottiene gli ID delle prime release di tutti i gruppi di rilascio dell'artista
        List<String> releasesIds = getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(name);

        // Itera su tutti gli ID di release
        for (String releaseId : releasesIds) {


            // Costruisce l'URL dell'immagine di copertina
            String imageUrl = "https://coverartarchive.org/release/" + releaseId + "/front";

            // Itera su tutte le release dell'artista
            for (Release release : albumReleaseByArtist) {

                // Verifica se l'ID della release corrisponde all'ID ottenuto
                if (release.getIdReleaseMusicBrainz().equals(releaseId)) {

                    // Verifica se la copertina della release è vuota
                    if (release.getCoverArt().isEmpty()) {

                        // Aggiorna l'URL dell'immagine di copertina della release nel database
                        release.setCoverArt(imageUrl);
                        releaseService.updateByEntity(release, release.getIdRelease());
                    }
                }
            }
        }
    }
}


