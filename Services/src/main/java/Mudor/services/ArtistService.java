package Mudor.services;

import Mudor.DTO.ArtistDTO;
import Mudor.entity.Artist;
import Mudor.entity.Release;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * L'interfaccia ArtistService fornisce operazioni specifiche per la gestione degli artisti nel sistema.
 * Estende l'interfaccia MapAndCRUDService per le operazioni CRUD di base e di mapping tra DTO e entità.
 */
@Service
public interface ArtistService extends MapAndCRUDService<ArtistDTO, Artist, Integer> {

    /**
     * Aggiorna un'entità artista specificata dall'ID.
     *
     * @param artist l'entità artista da aggiornare
     * @param id l'ID dell'artista da aggiornare
     */
    void updateByEntity(Artist artist, Integer id);

    /**
     * Ottiene un artista per l'ID su MusicBrainz.
     *
     * @param idMusicBrainz l'ID su MusicBrainz dell'artista da cercare
     * @return l'artista corrispondente all'ID su MusicBrainz, se presente, altrimenti null
     */
    Artist getArtistByIdMusicBrainz(String idMusicBrainz);

    /**
     * Ottiene una lista di artisti con criteri specifici.
     *
     * @param idArtist l'ID dell'artista
     * @param idArtistMusicBrainz l'ID su MusicBrainz dell'artista
     * @param name il nome dell'artista
     * @param description la descrizione dell'artista
     * @param country il paese dell'artista
     * @param genres i generi musicali dell'artista
     * @param title il titolo associato all'artista
     * @return una lista di DTO degli artisti che corrispondono ai criteri specificati
     */
    List<ArtistDTO> getArtistBy(Integer idArtist, String idArtistMusicBrainz, String name, String description, String country, List<String> genres, String title);
}