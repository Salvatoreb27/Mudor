package Mudor.services;

import Mudor.DTO.ReleaseDTO;
import Mudor.entity.Release;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * L'interfaccia ReleaseService fornisce operazioni specifiche per la gestione delle release nel sistema.
 * Estende l'interfaccia MapAndCRUDService per le operazioni CRUD di base e di mapping tra DTO e entità.
 */
@Service
public interface ReleaseService extends MapAndCRUDService<ReleaseDTO, Release, Integer> {

    /**
     * Ottiene una release group per l'ID su MusicBrainz.
     *
     * @param idMusicBrainz l'ID su MusicBrainz del release group da cercare
     * @return il release group corrispondente all'ID su MusicBrainz, se presente, altrimenti null
     */
    Release getReleaseGroupByIdMusicBrainz(String idMusicBrainz);

    /**
     * Ottiene una release per il titolo e il nome dell'artista specificati.
     *
     * @param title il titolo della release
     * @param artistName il nome dell'artista associato alla release
     * @return la release corrispondente al titolo e al nome dell'artista, se presente, altrimenti null
     */
    Release getReleaseByTitleAndArtistName(String title, String artistName);

    /**
     * Ottiene tutte le release associate al nome dell'artista specificato.
     *
     * @param artistName il nome dell'artista associato alle release da cercare
     * @return una lista di release associate all'artista specificato
     */
    List<Release> getReleasesByArtistName(String artistName);

    /**
     * Aggiorna una release specificata dall'entità release e dall'ID.
     *
     * @param release l'entità release da aggiornare
     * @param id l'ID della release da aggiornare
     */
    void updateByEntity(Release release, Integer id);

    /**
     * Ottiene tutte le release presenti nel sistema.
     *
     * @return una lista di tutte le release nel sistema
     */
    List<Release> getAll();

    /**
     * Ottiene una lista di release con criteri specifici.
     *
     * @param idRelease l'ID della release
     * @param idReleaseGroupMusicBrainz l'ID del release group su MusicBrainz
     * @param idReleaseMusicBrainz l'ID della release su MusicBrainz
     * @param title il titolo della release
     * @param kind il tipo di release
     * @param coverArt l'URL della copertina della release
     * @param dateOfRelease la data di uscita della release
     * @param tracks la lista delle tracce della release
     * @param genres la lista dei generi della release
     * @param name il nome associato alla release
     * @return una lista di DTO delle release che corrispondono ai criteri specificati
     */
    List<ReleaseDTO> getReleaseBy(Integer idRelease, String idReleaseGroupMusicBrainz, String idReleaseMusicBrainz, String title, String kind, String coverArt, String dateOfRelease, List<String> tracks, List<String> genres, String name);
}
