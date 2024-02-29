package Mudor.repository;

import Mudor.entity.Artist;
import Mudor.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist> {

    Optional<Artist> findByName(String name);

    Optional<Artist> findByIdArtistMusicBrainz (String idMusicBrainz);

}
