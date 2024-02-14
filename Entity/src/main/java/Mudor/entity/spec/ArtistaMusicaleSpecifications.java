package Mudor.entity.spec;

import Mudor.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class ArtistaMusicaleSpecifications {

    public static Specification<ArtistaMusicale> likeIdArtistaMusicale(Integer idArtistaMusicale) {
        if (idArtistaMusicale == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idArtistaMusicale")), "%" + idArtistaMusicale + "%");
        };
    }

    public static Specification<ArtistaMusicale> likeNome(String nome) {
        if (nome == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("nome")), "%" + nome + "%");
        };
    }

    public static Specification<ArtistaMusicale> likeDescrizione(String descrizione) {
        if (descrizione == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("descrizione")), "%" + descrizione + "%");
        };
    }

    public static Specification<ArtistaMusicale> likePaeseDOrigine(String paeseDOrigine) {
        if (paeseDOrigine == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("paeseDOrigine")), "%" + paeseDOrigine + "%");
        };
    }
    public static Specification<ArtistaMusicale> likeGeneri(List<String> generi) {
        if (generi == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("generi")), "%" + generi + "%");
        };
    }

    public static Specification<ArtistaMusicale> likeTitoloAlbumInStudio(String titoloAlbumInStudio) {

        return (root, query, criteriaBuilder) -> {

            Join<AlbumInStudio, ArtistaMusicale> albumInStudioArtistaMusicaleJoin = root.join("albumInStudio");

            return criteriaBuilder.equal(albumInStudioArtistaMusicaleJoin.get("titoloAlbumInStdio"), titoloAlbumInStudio);
        };
    }
    public static Specification<ArtistaMusicale> likeTitoloAlbumLive(String titoloAlbumLive) {

        return (root, query, criteriaBuilder) -> {

            Join<AlbumLive, ArtistaMusicale> albumLiveArtistaMusicaleJoin = root.join("albumLive");

            return criteriaBuilder.equal(albumLiveArtistaMusicaleJoin.get("titoloAlbumLive"), titoloAlbumLive);
        };
    }
    public static Specification<ArtistaMusicale> likeTitoloSingolo(String titoloSingolo) {

        return (root, query, criteriaBuilder) -> {

            Join<Singolo, ArtistaMusicale> singoloArtistaMusicaleJoin = root.join("singolo");

            return criteriaBuilder.equal(singoloArtistaMusicaleJoin.get("titoloSingolo"), titoloSingolo);
        };
    }

    public static Specification<ArtistaMusicale> likeTitoloRaccolta(String titoloRaccolta) {

        return (root, query, criteriaBuilder) -> {

            Join<Raccolta, ArtistaMusicale> raccoltaArtistaMusicaleJoin = root.join("raccolta");

            return criteriaBuilder.equal(raccoltaArtistaMusicaleJoin.get("titoloRaccolta"), titoloRaccolta);
        };
    }

}
