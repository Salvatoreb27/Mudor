package Mudor.entity.spec;

import Mudor.entity.AlbumInStudio;
import Mudor.entity.ArtistaMusicale;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class AlbumInStudioSpecifications {

    public static Specification<AlbumInStudio> likeIdAlbumInStudio(Integer idAlbumInStudio) {
        if (idAlbumInStudio == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idAlbumInStudio")), "%" + idAlbumInStudio + "%");
        };
    }

    public static Specification<AlbumInStudio> likeTitoloAlbumInStudio(String titoloAlbumInStudio) {
        if (titoloAlbumInStudio == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("titoloAlbumInStudio")), "%" + titoloAlbumInStudio + "%");
        };
    }

    public static Specification<AlbumInStudio> likeDataRilascio(Date dataRilascio) {
        if (dataRilascio == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("dataRilascio")), "%" + dataRilascio + "%");
        };
    }

    public static Specification<AlbumInStudio> likeBrani(List<String> brani) {
        if (brani == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("brani")), "%" + brani + "%");
        };
    }

    public static Specification<AlbumInStudio> likeGeneri(List<String> generi) {
        if (generi == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("generi")), "%" + generi + "%");
        };
    }

    public static Specification<AlbumInStudio> likeArtistaAlbum(String nome) {

        return (root, query, criteriaBuilder) -> {

            Join<AlbumInStudio, ArtistaMusicale> albumInStudioArtistaMusicaleJoin = root.join("artistaMusicale");

            return criteriaBuilder.equal(albumInStudioArtistaMusicaleJoin.get("nome"), nome);
        };
    }
}
