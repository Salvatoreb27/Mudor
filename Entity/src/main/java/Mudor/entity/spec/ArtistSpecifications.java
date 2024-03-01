package Mudor.entity.spec;

import Mudor.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * ArtistSpecifications è una classe che fornisce specifiche per la query per l'entità Artist.
 * Contiene metodi statici per creare specifiche di ricerca per vari campi dell'entità Artist.
 */
public class ArtistSpecifications {

    /**
     * Crea una specifica per corrispondere all'ID dell'artista.
     *
     * @param idArtist l'ID dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeIdArtist(Integer idArtist) {
        if (idArtist == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("idArtist"), "%" + idArtist + "%");
    }

    /**
     * Crea una specifica per corrispondere all'ID MusicBrainz dell'artista.
     *
     * @param idArtistMusicBrainz l'ID MusicBrainz dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeIdArtistMusicBrainz(String idArtistMusicBrainz) {
        if (idArtistMusicBrainz == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("idArtistMusicBrainz"), "%" + idArtistMusicBrainz + "%");
    }

    /**
     * Crea una specifica per corrispondere al nome dell'artista.
     *
     * @param name il nome dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    /**
     * Crea una specifica per corrispondere alla descrizione dell'artista.
     *
     * @param description la descrizione dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeDescription(String description) {
        if (description == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("description"), "%" + description + "%");
    }

    /**
     * Crea una specifica per corrispondere al paese dell'artista.
     *
     * @param country il paese dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeCountry(String country) {
        if (country == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("country"), "%" + country + "%");
    }

    /**
     * Crea una specifica per corrispondere ai generi dell'artista.
     *
     * @param genres la lista dei generi dell'artista da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeGenres(List<String> genres) {
        if (genres == null) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get("genres"), "%" + genres + "%");
    }

    /**
     * Crea una specifica per corrispondere al titolo di una release associata all'artista.
     *
     * @param title il titolo della release da cercare
     * @return la specifica creata
     */
    public static Specification<Artist> likeTitleRelease(String title) {
        return (root, query, cb) -> {
            Join<Artist, Release> releaseJoin = root.join("release");
            return cb.equal(releaseJoin.get("title"), title);
        };
    }
}