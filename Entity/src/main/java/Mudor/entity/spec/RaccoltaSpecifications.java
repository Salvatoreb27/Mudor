package Mudor.entity.spec;

import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.entity.Raccolta;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class RaccoltaSpecifications {

    public static Specification<Raccolta> likeIdRaccolta(Integer idRaccolta) {
        if (idRaccolta == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idRaccolta")), "%" + idRaccolta + "%");
        };
    }

    public static Specification<Raccolta> likeTitoloRaccolta(String titoloRaccolta) {
        if (titoloRaccolta == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("titoloRaccolta")), "%" + titoloRaccolta + "%");
        };
    }

    public static Specification<Raccolta> likeDataRilascio(Date dataRilascio) {
        if (dataRilascio == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("dataRilascio")), "%" + dataRilascio + "%");
        };
    }

    public static Specification<Raccolta> likeBrani(List<String> brani) {
        if (brani == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("brani")), "%" + brani + "%");
        };
    }

    public static Specification<Raccolta> likeGeneri(List<String> generi) {
        if (generi == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("generi")), "%" + generi + "%");
        };
    }

    public static Specification<Raccolta> likeArtistaAlbum(String nome) {

        return (root, query, criteriaBuilder) -> {

            Join<Raccolta, ArtistaMusicale> raccoltaArtistaMusicaleJoin = root.join("artistaMusicale");

            return criteriaBuilder.equal(raccoltaArtistaMusicaleJoin.get("nome"), nome);
        };
    }
}
