package Mudor.entity.spec;

import Mudor.entity.Artist;
import Mudor.entity.Release;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class ReleaseSpecifications {

    public static Specification<Release> likeIdRelease(Integer idRelease) {
        if (idRelease == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idRelease")), "%" + idRelease + "%");
        };
    }

    public static Specification<Release> likeIdReleaseMusicBrainz(String idReleaseMusicBrainz) {
        if (idReleaseMusicBrainz == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("idReleaseMusicBrainz")), "%" + idReleaseMusicBrainz + "%");
        };
    }

    public static Specification<Release> likeTitle(String title) {
        if (title == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("title")), "%" + title + "%");
        };
    }

    public static Specification<Release> likeKind(String kind) {
        if (kind == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("title")), "%" + kind + "%");
        };
    }

    public static Specification<Release> likeCoverArt(String covertArt) {
        if (covertArt == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("covertArt")), "%" + covertArt + "%");
        };
    }

    public static Specification<Release> likeDateOfRelease(String dateOfRelease) {
        if (dateOfRelease == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("dateOfRelease")), "%" + dateOfRelease + "%");
        };
    }

    public static Specification<Release> likeTracks(List<String> tracks) {
        if (tracks == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("tracks")), "%" + tracks + "%");
        };
    }

    public static Specification<Release> likeGenres(List<String> genres) {
        if (genres == null) {
            return null;
        }
        return (root, query, cb) -> {
            return cb.like(root.get(("genres")), "%" + genres + "%");
        };
    }

    public static Specification<Release> likeArtistRelease(String name) {

        return (root, query, criteriaBuilder) -> {

            Join<Release, Artist> ReleaseArtistJoin = root.join("artist");

            return criteriaBuilder.equal(ReleaseArtistJoin.get("name"), name);
        };
    }
}
