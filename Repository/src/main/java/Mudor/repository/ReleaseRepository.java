package Mudor.repository;

import Mudor.entity.Artist;
import Mudor.entity.Release;
import com.mysql.cj.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {


    Optional<Release> findByTitle(String title);

    List <Release> findByKindAndArtistsName (String kind, String artistName);
    List<Release> findByKind (String kind);
    List<Release> findByArtistsName(String artistName);

    Optional<Release> findByIdReleaseGroupMusicBrainz(String idMusicBrainz);
}
