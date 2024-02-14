package Mudor.entity.spec;


import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class AlbumLiveSpecifications {

    public static Specification<AlbumLive> likeIdAlbumLive(Integer idAlbumLive) {
        if (idAlbumLive == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idAlbumLive")), "%" + idAlbumLive + "%");
        };
    }

    public static Specification<AlbumLive> likeTitoloAlbumLive(String titoloAlbumLive) {
        if (titoloAlbumLive == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("titoloAlbumLive")), "%" + titoloAlbumLive + "%");
        };
    }

    public static Specification<AlbumLive> likeDataRilascio(Date dataRilascio) {
        if (dataRilascio == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("dataRilascio")), "%" + dataRilascio + "%");
        };
    }

    public static Specification<AlbumLive> likeBrani(List<String> brani) {
        if (brani == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("brani")), "%" + brani + "%");
        };
    }

    public static Specification<AlbumLive> likeGeneri(List<String> generi) {
        if (generi == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("generi")), "%" + generi + "%");
        };
    }

    public static Specification<AlbumLive> likeArtistaAlbum(String nome) {

        return (root, query, criteriaBuilder) -> {

            Join<AlbumLive, ArtistaMusicale> albumLiveArtistaMusicaleJoin = root.join("artistaMusicale");

            return criteriaBuilder.equal(albumLiveArtistaMusicaleJoin.get("nome"), nome);
        };
    }
}
