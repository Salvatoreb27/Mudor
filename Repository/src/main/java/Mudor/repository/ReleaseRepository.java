package Mudor.repository;

import Mudor.entity.Artist;
import Mudor.entity.Release;
import com.mysql.cj.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * L'interfaccia ReleaseRepository fornisce metodi per interagire con l'entità Release nel database.
 * Estende JpaRepository per operazioni CRUD di base e JpaSpecificationExecutor per eseguire query con specifiche JPA.
 */
public interface ReleaseRepository extends JpaRepository<Release, Integer>, JpaSpecificationExecutor<Release> {


    /**
     * Trova le release di un determinato tipo.
     *
     * @param kind il tipo di release da cercare
     * @return una lista di release che corrispondono al tipo specificato
     */
    List<Release> findByKind(String kind);

    /**
     * Trova una release per titolo e nome dell'artista associato.
     *
     * @param title il titolo della release da cercare
     * @param artistName il nome dell'artista associato alla release
     * @return la release trovata
     */
    Release findReleaseByTitleAndArtistsName(String title, String artistName);

    /**
     * Trova le release associate a un artista specifico.
     *
     * @param artistName il nome dell'artista associato alle release
     * @return una lista di release associate all'artista specificato
     */
    List<Release> findReleasesByArtistsName(String artistName);

    /**
     * Trova una release per l'ID del gruppo di release su MusicBrainz.
     *
     * @param idMusicBrainz l'ID del gruppo di release su MusicBrainz della release da cercare
     * @return un Optional contenente la release se trovata, altrimenti un Optional vuoto
     */
    Optional<Release> findByIdReleaseGroupMusicBrainz(String idMusicBrainz);
}
