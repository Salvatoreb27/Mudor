package Mudor.entity.spec;

import Mudor.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ArtistSpecifications {

    public static Specification<Artist> likeIdArtist(Integer idArtist) {
        if (idArtist == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idArtist")), "%" + idArtist + "%");
        };
    }

    public static Specification<Artist> likeIdArtistMusicBrainz(String idArtistMusicBrainz) {
        if (idArtistMusicBrainz == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idArtistMusicBrainz")), "%" + idArtistMusicBrainz + "%");
        };
    }

    public static Specification<Artist> likeName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("name")), "%" + name + "%");
        };
    }

    public static Specification<Artist> likeDescription(String description) {
        if (description == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("description")), "%" + description + "%");
        };
    }

    public static Specification<Artist> likeCountry(String country) {
        if (country == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("country")), "%" + country + "%");
        };
    }
    public static Specification<Artist> likeGenres(List<String> genres) {
        if (genres == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("genres")), "%" + genres + "%");
        };
    }

    public static Specification<Artist> likeTitleRelease(String title) {

        return (root, query, criteriaBuilder) -> {

            Join<Release, Artist> ReleaseArtistMusicaleJoin = root.join("release");

            return criteriaBuilder.equal(ReleaseArtistMusicaleJoin.get("title"), title);
        };
    }

}
