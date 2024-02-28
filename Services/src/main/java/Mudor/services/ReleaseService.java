package Mudor.services;

import Mudor.DTO.ReleaseDTO;
import Mudor.entity.Release;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReleaseService extends MapAndCRUDService<ReleaseDTO, Release, Integer>{

    List<Release> getReleasesByKindAndArtistsName (String kind, String artistName);
    List<Release> findReleasesByKind (String kind);
    Release getReleaseGroupByIdMusicBrainz (String idMusicBrainz);
    List<Release> getReleasesByArtistName(String artistName);
    void updateByEntity(Release release, Integer id);
    List<Release> getAll();
    List<ReleaseDTO> getReleaseBy(Integer idRelease, String idReleaseMusicBrainz, String title, String kind, String coverArt, String dateOfRelease, List<String> tracks, List<String> genres, String name);
}
