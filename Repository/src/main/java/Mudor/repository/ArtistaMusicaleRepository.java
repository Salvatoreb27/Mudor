package Mudor.repository;

import Mudor.entity.ArtistaMusicale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaMusicaleRepository extends JpaRepository<ArtistaMusicale, Integer> {

    Optional<ArtistaMusicale> findByNomeArtistaMusicale(String nome);
}
