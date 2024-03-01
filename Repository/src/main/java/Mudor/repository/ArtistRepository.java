package Mudor.repository;

import Mudor.entity.Artist;
import Mudor.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * L'interfaccia ArtistRepository fornisce metodi per interagire con l'entità Artist nel database.
 * Estende JpaRepository per operazioni CRUD di base e JpaSpecificationExecutor per eseguire query con specifiche JPA.
 */
public interface ArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist> {

    /**
     * Trova un artista per nome.
     *
     * @param name il nome dell'artista da cercare
     * @return un Optional contenente l'artista se trovato, altrimenti un Optional vuoto
     */
    Optional<Artist> findByName(String name);

    /**
     * Trova un artista per l'ID su MusicBrainz.
     *
     * @param idMusicBrainz l'ID su MusicBrainz dell'artista da cercare
     * @return un Optional contenente l'artista se trovato, altrimenti un Optional vuoto
     */
    Optional<Artist> findByIdArtistMusicBrainz(String idMusicBrainz);
}