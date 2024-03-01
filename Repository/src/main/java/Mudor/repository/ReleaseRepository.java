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

public interface ReleaseRepository extends JpaRepository<Release, Integer>, JpaSpecificationExecutor<Release> {


    Optional<Release> findByTitle(String title);
    List <Release> findByKindAndArtistsName (String kind, String artistName);
    List<Release> findByKind (String kind);
    Release findReleaseByTitleAndArtistsName (String title, String artistname);
    List<Release> findReleasesByArtistsName(String artistName);
    Optional<Release> findByIdReleaseGroupMusicBrainz(String idMusicBrainz);
}
