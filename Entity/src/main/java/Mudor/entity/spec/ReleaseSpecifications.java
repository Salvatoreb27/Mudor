package Mudor.entity.spec;

import Mudor.entity.Artist;
import Mudor.entity.Release;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * ReleaseSpecifications è una classe che fornisce specifiche per le query per l'entità Release.
 * Contiene metodi statici per creare specifiche di ricerca per vari campi dell'entità Release.
 */
public class ReleaseSpecifications {

    /**
     * Crea una specifica per corrispondere all'ID della release.
     *
     * @param idRelease l'ID della release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeIdRelease(Integer idRelease) {
        if (idRelease == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("idRelease"), "%" + idRelease + "%");
    }

    /**
     * Crea una specifica per corrispondere all'ID del gruppo di release su MusicBrainz.
     *
     * @param idReleaseGroupMusicBrainz l'ID del gruppo di release su MusicBrainz da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeIdReleaseGroupMusicBrainz(String idReleaseGroupMusicBrainz) {
        if (idReleaseGroupMusicBrainz == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("idReleaseGroupMusicBrainz"), "%" + idReleaseGroupMusicBrainz + "%");
    }

    /**
     * Crea una specifica per corrispondere all'ID della release su MusicBrainz.
     *
     * @param idReleaseMusicBrainz l'ID della release su MusicBrainz da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeIdReleaseMusicBrainz(String idReleaseMusicBrainz) {
        if (idReleaseMusicBrainz == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("idReleaseMusicBrainz"), "%" + idReleaseMusicBrainz + "%");
    }

    /**
     * Crea una specifica per corrispondere al titolo della release.
     *
     * @param title il titolo della release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeTitle(String title) {
        if (title == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    /**
     * Crea una specifica per corrispondere al tipo di release.
     *
     * @param kind il tipo di release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeKind(String kind) {
        if (kind == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("kind"), "%" + kind + "%");
    }

    /**
     * Crea una specifica per corrispondere all'URL della copertina della release.
     *
     * @param covertArt l'URL della copertina della release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeCoverArt(String covertArt) {
        if (covertArt == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("coverArt"), "%" + covertArt + "%");
    }

    /**
     * Crea una specifica per corrispondere alla data di rilascio della release.
     *
     * @param dateOfRelease la data di rilascio della release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeDateOfRelease(String dateOfRelease) {
        if (dateOfRelease == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("dateOfRelease"), "%" + dateOfRelease + "%");
    }

    /**
     * Crea una specifica per corrispondere alle tracce della release.
     *
     * @param tracks la lista delle tracce della release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeTracks(List<String> tracks) {
        if (tracks == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("tracks"), "%" + tracks + "%");
    }

    /**
     * Crea una specifica per corrispondere ai generi della release.
     *
     * @param genres la lista dei generi della release da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeGenres(List<String> genres) {
        if (genres == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("genres"), "%" + genres + "%");
    }

    /**
     * Crea una specifica per corrispondere al nome di un artista associato alla release.
     *
     * @param name il nome dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Release> likeArtistRelease(String name) {
        return (root, query, criteriaBuilder) -> {
            Join<Release, Artist> releaseArtistJoin = root.join("artist");
            return criteriaBuilder.equal(releaseArtistJoin.get("name"), name);
        };
    }

    /**
     * Crea una specifica per cercare una release per tipo e nome dell'artista associato.
     *
     * @param kind il tipo di release da cercare
     * @param name il nome dell'artista associato da cercare
     * @return la specifica creata
     */
    public static Specification<Release> findByKindAndArtistsName(String kind, String name) {
        return (root, query, cb) -> {
            Join<Release, Artist> releaseArtistJoin = root.join("artists");
            return cb.and(
                    cb.equal(releaseArtistJoin.get("name"), name),
                    cb.equal(root.get("kind"), kind)
            );
        };
    }
}
