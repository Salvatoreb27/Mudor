package Mudor.services;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * L'interfaccia MudorFinderService fornisce metodi per la ricerca e la costruzione di informazioni
 * relative agli artisti e alle release musicali.
 */
@Service
public interface MudorFinderService {
    /**
     * Ricerca tutte le informazioni relative ad un artista tramite API MusicBrainz.
     *
     * @param name il nome dell'artista
     * @return un json contenente i risultati della ricerca dell'artista.
     */
    String searchArtistMusicBrainz(String name);
    /**
     * Ricerca l'id (quello di MusicBrainz) di un artista tramite API MusicBrainz.
     *
     * @param name il nome dell'artista
     * @return l'id (quello di MusicBrainz) dell'artista
     */
    String getArtistIdMusicBrainz(String name);
    /**
     * Ricerca la pagina contenente informazioni su un artista tramite API MusicBrainz.
     *
     * @param name il nome dell'artista associato agli album in studio
     * @return un json riferito alla pagina che identifica l'artista.
     */
    String searchArtistPageMusicBrainz(String name);
    /**
     * Ricerca la pagina contenente informazioni sugli album in studio di un artista tramite API MusicBrainz.
     *
     * @param name il nome dell'artista associato agli album in studio
     * @return un json contenente informazioni su tutti gli album in studio.
     */
    String searchAlbumInStudioPageMusicBrainz(String name);
    /**
     * Ricerca tutti gli album in studio di un artista tramite API MusicBrainz.
     *
     * @param name il nome dell'artista associato agli album in studio
     * @return una lista contenente gli album in studio dell'artista.
     */
    List<String> extractReleasesTitlesMusicBrainz(String name);

    /**
     * Ricerca informazioni riguardanti una release-group di un artista tramite API MusicBrainz.
     *
     * @param title il titolo del gruppo di release
     * @param artistName il nome dell'artista associato al gruppo di release
     * @return il json del gruppo di release
     */
    String getOneReleaseGroupInfo(String title, String artistName);
    /**
     * Ricerca informazioni riguardanti tutte i release-group di un artista tramite API MusicBrainz.
     *
     * @param name il nome dell'artista associato ai gruppi di release
     * @return una lista contenente i json di tutti i gruppi di release.
     */
    List<String> getAllReleaseGroupsInfo(String name);
    /**
     * Ricerca l'id(quello di MusicBrainz) della prima versione rilasciata di una release di un artista tramite API MusicBrainz,
     *
     * @param title il titolo della release
     * @param artistName il nome dell'artista associato alle release
     * @return l'id della prima versione rilasciata della release specificata dal titolo fornito come parametro.
     */
    String getOneFirstReleaseOfAReleaseGroupOfAnArtist(String title, String artistName);
    /**
     * Ricerca l'id(quello di MusicBrainz) della prima versione rilasciata di tutte le release (discografia) di un artista tramite API MusicBrainz,
     *
     * @param name il nome dell'artista associato alle release
     * @return una lista contenente tutti gli id delle prime versioni rilasciate di tutte le release
     */
    List<String> getAllFirstReleasesOfAllReleaseGroupsOfAnArtist(String name);

    /**
     * Ricerca una release tramite API MusicBrainz, ne estrae i dati con cui crea e salva un oggetto Release persistentemente.
     *
     * @param releaseName il nome della release da costruire
     * @param artistName il nome dell'artista associato alla release
     * @return una ResponseEntity che rappresenta lo stato della richiesta
     */
    ResponseEntity<String> releaseConstruct(String releaseName, String artistName);

    /**
     * Aggiunge le tracce ad una release specificata dal titolo e dal nome dell'artista.
     *
     * @param title il titolo della release
     * @param artistName il nome dell'artista associato alla release
     */
    void addTracksForOneRelease(String title, String artistName);

    /**
     * Aggiunge l'url contenente la copertina ad una release specificata dal titolo e dal nome dell'artista.
     *
     * @param title il titolo della release
     * @param artistName il nome dell'artista associato alla release
     */
    void addCoverArtForOneRelease(String title, String artistName);

    /**
     * Aggiunge un'intera discografia di un artista, ricercandola per nome tramite API MusicBrainz.
     * Estratti i dati dall'API, crea con questi i relativi oggetti e li salva persistentemente.
     *
     * @param name il nome della release da costruire
     * @return una ResponseEntity che rappresenta lo stato della richiesta
     */
    ResponseEntity<String> discographyConstruct(String name);

    /**
     * Aggiunge le tracce di un intera discografia agli oggetti Release persistenti corrispondenti.
     * L'esecuzione di questo metodo richiede la persistenza:
     * -dell'oggetto Artista riferito al nome passato come parametro;
     * -di tutti gli oggetti Release riferiti alla discografia dell'Artista riferito al nome passato come parametro.
     *
     * @param name il nome dell'artista
     */
    void addTracksForArtist(String name);

    /**
     * Aggiunge gli URL delle copertine di un intera discografia agli oggetti Release persistenti corrispondenti.
     * L'esecuzione di questo metodo richiede la persistenza:
     * -dell'oggetto Artista riferito al nome passato come parametro;
     * -di tutti gli oggetti Release riferiti alla discografia dell'Artista riferito al nome passato come parametro.
     *
     * @param name il nome dell'artista
     */
    void addCoverArtForArtist(String name);
}
