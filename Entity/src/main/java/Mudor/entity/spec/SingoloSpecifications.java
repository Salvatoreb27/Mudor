package Mudor.entity.spec;

import Mudor.entity.ArtistaMusicale;
import Mudor.entity.Raccolta;
import Mudor.entity.Singolo;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class SingoloSpecifications {
    public static Specification<Singolo> likeIdSingolo(Integer idSingolo) {
        if (idSingolo == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idSingolo")), "%" + idSingolo + "%");
        };
    }

    public static Specification<Singolo> likeTitoloSingolo(String titoloSingolo) {
        if (titoloSingolo == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("titoloSingolo")), "%" + titoloSingolo + "%");
        };
    }

    public static Specification<Singolo> likeDataRilascio(Date dataRilascio) {
        if (dataRilascio == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("dataRilascio")), "%" + dataRilascio + "%");
        };
    }

    public static Specification<Singolo> likeBrani(List<String> brani) {
        if (brani == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("brani")), "%" + brani + "%");
        };
    }
    public static Specification<Singolo> likeGeneri(List<String> generi) {
        if (generi == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("generi")), "%" + generi + "%");
        };
    }

    public static Specification<Singolo> likeArtistaSingolo(String nome) {

        return (root, query, criteriaBuilder) -> {

            Join<Singolo, ArtistaMusicale> singoloArtistaMusicaleJoin = root.join("artistaMusicale");

            return criteriaBuilder.equal(singoloArtistaMusicaleJoin.get("nome"), nome);
        };
    }
}
