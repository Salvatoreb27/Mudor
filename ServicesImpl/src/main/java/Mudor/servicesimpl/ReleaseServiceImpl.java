package Mudor.servicesimpl;

import Mudor.DTO.ArtistDTO;
import Mudor.DTO.ReleaseDTO;
import Mudor.entity.Artist;
import Mudor.entity.Release;
import Mudor.entity.spec.ReleaseSpecifications;
import Mudor.repository.ArtistRepository;
import Mudor.repository.ReleaseRepository;
import Mudor.services.ReleaseService;
import com.mysql.cj.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ReleaseRepository releaseRepository;
    @PersistenceContext
    private EntityManager entityManager;



    private ArtistDTO mapTOArtistDTO(Artist artist) {
        List<ReleaseDTO> releaseDTOList = mapTODTOList(artist.getReleases());
        ArtistDTO artistDTO = ArtistDTO.builder()
                .idArtistMusicBrainz(artist.getIdArtistMusicBrainz())
                .name(artist.getName())
                .description(artist.getDescription())
                .country(artist.getCountry())
                .genres(artist.getGenres())
                .releaseDTOList(releaseDTOList)
                .build();
        return artistDTO;
    }

    private List<ArtistDTO> mapTOArtistDTOList(List<Artist> artistList) {
        return artistList.stream()
                .map((Artist artist) -> this.mapTOArtistDTO(artist))
                .collect(Collectors.toList());
    }

    private Artist mapToArtistEntity(ArtistDTO artistDTO) {
        List<Release> releaseList = mapTOEntityList(artistDTO.getReleaseDTOList());
        Artist artist = Artist.builder()
                .idArtistMusicBrainz(artistDTO.getIdArtistMusicBrainz())
                .name(artistDTO.getName())
                .description(artistDTO.getDescription())
                .genres(artistDTO.getGenres())
                .country(artistDTO.getCountry())
                .releases(releaseList)
                .build();
        return artist;
    }

    private List<Artist> mapTOArtistEntityList(List<ArtistDTO> artistDTOList) {
        return artistDTOList.stream()
                .map((ArtistDTO artistDTO) -> this.mapToArtistEntity(artistDTO))
                .collect(Collectors.toList());
    }
    @Override
    public ReleaseDTO mapTODTO(Release release) {
        List<ArtistDTO> artistDTOList = mapTOArtistDTOList(release.getArtists());
        ReleaseDTO releaseDTO = ReleaseDTO.builder()
                .idReleaseGroupMusicBrainz(release.getIdReleaseGroupMusicBrainz())
                .idReleaseMusicBrainz(release.getIdReleaseMusicBrainz())
                .title(release.getTitle())
                .kind(release.getKind())
                .coverArt(release.getCoverArt())
                .dateOfRelease(release.getDateOfRelease())
                .tracks(release.getTracks())
                .genres(release.getGenres())
                .artistDTOList(artistDTOList)
                .build();
        return releaseDTO;
    }

    @Override
    public List<ReleaseDTO> mapTODTOList(List<Release> releaseList) {
        return releaseList.stream()
                .map((Release release) -> this.mapTODTO(release))
                .collect(Collectors.toList());
    }

    @Override
    public Release mapToEntity(ReleaseDTO releaseDTO) {
        List<Artist> artistList = mapTOArtistEntityList(releaseDTO.getArtistDTOList());
        Release release = Release.builder()
                .idReleaseGroupMusicBrainz(releaseDTO.getIdReleaseGroupMusicBrainz())
                .idReleaseMusicBrainz(releaseDTO.getIdReleaseMusicBrainz())
                .title(releaseDTO.getTitle())
                .kind(releaseDTO.getKind())
                .coverArt(releaseDTO.getCoverArt())
                .dateOfRelease(releaseDTO.getDateOfRelease())
                .tracks(releaseDTO.getTracks())
                .genres(releaseDTO.getGenres())
                .artists(artistList)
                .build();
        return release;
    }

    @Override
    public List<Release> mapTOEntityList(List<ReleaseDTO> releaseDTOList) {
        return releaseDTOList.stream()
                .map((ReleaseDTO releaseDTO) -> this.mapToEntity(releaseDTO))
                .collect(Collectors.toList());
    }

    public List<Release> getAll() {
        return releaseRepository.findAll();
    }

    public List<Release> getReleasesByArtistName(String artistName) {
        return releaseRepository.findByArtistsName(artistName);
    }

    @Override
    public List<Release> getReleasesByKindAndArtistsName(String kind, String artistName) {
        return releaseRepository.findByKindAndArtistsName(kind, artistName);
    }

    @Override
    public List<Release> findReleasesByKind(String kind) {
        return releaseRepository.findByKind(kind);
    }

    @Override
    public List<ReleaseDTO> getDTOs() {
        List<Release> releaseList = new ArrayList<>(releaseRepository.findAll());
        return mapTODTOList(releaseList);
    }

    @Override
    public ReleaseDTO getDTO(Integer id) {
        Optional<Release> release = releaseRepository.findById(id);
        if (release.isPresent()) {
            return mapTODTO(release.get());
        } else {
            return null;
        }
    }



    @Override
    public Release getReleaseGroupByIdMusicBrainz(String idMusicBrainz) {
        Optional <Release> release = releaseRepository.findByIdReleaseGroupMusicBrainz(idMusicBrainz);
        if (release.isPresent()) {
            return release.get();
        } else {
            return null;
        }
    }

//    @Override
//    public Optional<List<Release>> getReleasesByArtist(String name) {
//        List<Artist> artists = new ArrayList<>();
//        Artist artist = new Artist();
//        artist.setName(name);
//        artists.add(artist);
//     return releaseRepository.findByArtists(artists);
//    }

    @Override
    public Release add(ReleaseDTO releaseDTO) {
        Release release = mapToEntity(releaseDTO);
        return releaseRepository.save(release);
    }

    @Override
    public void update(ReleaseDTO releaseDTO, Integer id) {
        List<Artist> artistList = mapTOArtistEntityList(releaseDTO.getArtistDTOList());
        Release searchedRelease = releaseRepository.findById(id).orElseThrow(() -> new RuntimeException("La release ricercata non è presente"));
        searchedRelease.setIdReleaseGroupMusicBrainz(releaseDTO.getIdReleaseGroupMusicBrainz() == null ? searchedRelease.getIdReleaseGroupMusicBrainz() : releaseDTO.getIdReleaseGroupMusicBrainz());
        searchedRelease.setIdReleaseMusicBrainz(releaseDTO.getIdReleaseMusicBrainz() == null ? searchedRelease.getIdReleaseMusicBrainz() : releaseDTO.getIdReleaseMusicBrainz());
        searchedRelease.setTitle(releaseDTO.getTitle() == null ? searchedRelease.getTitle() : releaseDTO.getTitle());
        searchedRelease.setKind(releaseDTO.getKind() == null ? searchedRelease.getKind() : releaseDTO.getKind());
        searchedRelease.setCoverArt(releaseDTO.getCoverArt() == null ? searchedRelease.getCoverArt() : releaseDTO.getCoverArt());
        searchedRelease.setDateOfRelease(releaseDTO.getDateOfRelease() == null ? searchedRelease.getDateOfRelease() : releaseDTO.getDateOfRelease());
        searchedRelease.setTracks(releaseDTO.getTracks() == null ? searchedRelease.getTracks() : releaseDTO.getTracks());
        searchedRelease.setGenres(releaseDTO.getGenres() == null ? searchedRelease.getGenres() : releaseDTO.getGenres());
        searchedRelease.setArtists(artistList == null ? searchedRelease.getArtists() : artistList);
        releaseRepository.save(searchedRelease);
    }

    public void updateByEntity(Release release, Integer id) {
        Release searchedRelease = releaseRepository.findById(id).orElseThrow(() -> new RuntimeException("La release ricercata non è presente"));
        searchedRelease.setIdReleaseGroupMusicBrainz(release.getIdReleaseGroupMusicBrainz() == null ? searchedRelease.getIdReleaseGroupMusicBrainz() : release.getIdReleaseGroupMusicBrainz());
        searchedRelease.setIdReleaseMusicBrainz(release.getIdReleaseMusicBrainz() == null ? searchedRelease.getIdReleaseMusicBrainz() : release.getIdReleaseMusicBrainz());
        searchedRelease.setTitle(release.getTitle() == null ? searchedRelease.getTitle() : release.getTitle());
        searchedRelease.setKind(release.getKind() == null ? searchedRelease.getKind() : release.getKind());
        searchedRelease.setCoverArt(release.getCoverArt() == null ? searchedRelease.getCoverArt() : release.getCoverArt());
        searchedRelease.setDateOfRelease(release.getDateOfRelease() == null ? searchedRelease.getDateOfRelease() : release.getDateOfRelease());
        searchedRelease.setTracks(release.getTracks() == null ? searchedRelease.getTracks() : release.getTracks());
        searchedRelease.setGenres(release.getGenres() == null ? searchedRelease.getGenres() : release.getGenres());
        searchedRelease.setArtists(release.getArtists() == null ? searchedRelease.getArtists() : release.getArtists());
        releaseRepository.save(searchedRelease);
    }

    @Override
    public void delete(Integer id) {
        if (releaseRepository.existsById(id)) {
            releaseRepository.deleteById(id);
        }
    }

    private List<Specification<Release>> createSpecifications(Integer idRelease, String idReleaseMusicBrainz, String title, String kind, String coverArt, String dateOfRelease, List<String> tracks, List<String> genres, String name) {
        List<Specification<Release>> specifications = new ArrayList<>();
        if (idRelease != null) {
            specifications.add(ReleaseSpecifications.likeIdRelease(idRelease));
        }
        if (idReleaseMusicBrainz != null) {
            specifications.add(ReleaseSpecifications.likeIdReleaseMusicBrainz(idReleaseMusicBrainz));
        }
        if (title != null) {
            specifications.add(ReleaseSpecifications.likeTitle(title));
        }
        if (kind != null) {
            specifications.add(ReleaseSpecifications.likeKind(kind));
        }
        if (coverArt != null) {
            specifications.add(ReleaseSpecifications.likeCoverArt(coverArt));
        }
        if (dateOfRelease != null) {
            specifications.add(ReleaseSpecifications.likeDateOfRelease(dateOfRelease));
        }
        if (tracks != null) {
            specifications.add(ReleaseSpecifications.likeTracks(tracks));
        }
        if (genres != null) {
            specifications.add(ReleaseSpecifications.likeGenres(genres));
        }
        if (name != null) {
            specifications.add(ReleaseSpecifications.likeArtistRelease(name));
        }
        return specifications;
    }

    private Specification<Release> combineSpecifications(List<Specification<Release>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }



    @Override
    public List<ReleaseDTO> getReleaseBy(Integer idRelease, String idReleaseMusicBrainz, String title, String kind, String coverArt, String dateOfRelease, List<String> tracks, List<String> genres, String name) {
        List<Specification<Release>> specifications = createSpecifications(idRelease, idReleaseMusicBrainz, title, kind, coverArt, dateOfRelease, tracks, genres, name);
        Specification<Release> combinedSpecification = combineSpecifications(specifications);

        List<Release> releaseList = releaseRepository.findAll((Sort) combinedSpecification);

        return releaseList.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }
}

