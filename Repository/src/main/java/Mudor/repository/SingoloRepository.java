package Mudor.repository;

import Mudor.entity.ArtistaMusicale;
import Mudor.entity.Singolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SingoloRepository extends JpaRepository<Singolo, Integer> {
    Optional<Singolo> findByTitoloSingolo(String titoloSingolo);


}
