package Mudor.repository;

import Mudor.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    Optional<Artist> findByName(String name);

    Optional<Artist> findByIdArtistMusicBrainz (String idMusicBrainz);

}
