package Mudor.servicesimpl;

import Mudor.DTO.*;
import Mudor.entity.*;
import Mudor.entity.spec.ArtistSpecifications;
import Mudor.repository.ArtistRepository;
import Mudor.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ReleaseService releaseService;

    @Override
    public Artist getArtistById(Integer id) {
        Optional<Artist> artistaMusicale = artistRepository.findById(id);
        if (artistaMusicale.isPresent()) {
            return artistaMusicale.get();
        } else {
            return null;
        }
    }

    @Override
    public Artist getArtistByName(String name) {
        Optional<Artist> artist = artistRepository.findByName(name);
        if (artist.isPresent()) {
            return artist.get();
        } else {
            return null;
        }
    }

    @Override
    public Artist getArtistByIdMusicBrainz(String idMusicBrainz) {
        Optional<Artist> artist = artistRepository.findByIdArtistMusicBrainz(idMusicBrainz);
        if (artist.isPresent()) {
            return artist.get();
        } else {
            return null;
        }
    }

    @Override
    public ArtistDTO mapTODTO(Artist artist) {
        List<ReleaseDTO> releaseDTOList = releaseService.mapTODTOList(artist.getReleases());
        ArtistDTO artistDTO = ArtistDTO.builder()
                .idArtistMusicBrainz(artist.getIdArtistMusicBrainz())
                .relationURLs(artist.getRelationURLs())
                .name(artist.getName())
                .description(artist.getDescription())
                .country(artist.getCountry())
                .genres(artist.getGenres())
                .releaseDTOList(releaseDTOList)
                .build();
        return artistDTO;
    }

    @Override
    public List<ArtistDTO> mapTODTOList(List<Artist> artistList) {
        return artistList.stream()
                .map((Artist artist) -> this.mapTODTO(artist))
                .collect(Collectors.toList());
    }

    @Override
    public Artist mapToEntity(ArtistDTO artistDTO) {
        List<Release> releaseList = releaseService.mapTOEntityList(artistDTO.getReleaseDTOList());
        Artist artist = Artist.builder()
                .idArtistMusicBrainz(artistDTO.getIdArtistMusicBrainz())
                .relationURLs(artistDTO.getRelationURLs())
                .name(artistDTO.getName())
                .description(artistDTO.getDescription())
                .genres(artistDTO.getGenres())
                .country(artistDTO.getCountry())
                .releases(releaseList)
                .build();
        return artist;
    }

    @Override
    public List<Artist> mapTOEntityList(List<ArtistDTO> artistDTOList) {
        return artistDTOList.stream()
                .map((ArtistDTO artistDTO) -> this.mapToEntity(artistDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArtistDTO> getDTOs() {
        List<Artist> artists = new ArrayList<>(artistRepository.findAll());
        return mapTODTOList(artists);
    }

    @Override
    public ArtistDTO getDTO(Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent()) {
            return mapTODTO(artist.get());
        } else {
            return null;
        }
    }

    @Override
    public Artist add(ArtistDTO artistDTO) {
        Artist artist = mapToEntity(artistDTO);
        return artistRepository.save(artist);
    }


    @Override
    public void update(ArtistDTO artistDTO, Integer id) {
        List<Release> releaseList = releaseService.mapTOEntityList(artistDTO.getReleaseDTOList());
        Artist searchedArtist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("L'Artista ricercato non è presente"));
        searchedArtist.setIdArtistMusicBrainz(artistDTO.getIdArtistMusicBrainz() == null ? searchedArtist.getIdArtistMusicBrainz() : artistDTO.getIdArtistMusicBrainz());
        searchedArtist.setName(artistDTO.getName() == null ? searchedArtist.getName() : artistDTO.getName());
        searchedArtist.setRelationURLs(artistDTO.getRelationURLs() == null ? searchedArtist.getRelationURLs() : artistDTO.getRelationURLs());
        searchedArtist.setDescription(artistDTO.getDescription() == null ? searchedArtist.getDescription() : artistDTO.getDescription());
        searchedArtist.setGenres(artistDTO.getGenres() == null ? searchedArtist.getGenres() : artistDTO.getGenres());
        searchedArtist.setCountry(artistDTO.getCountry() == null ? searchedArtist.getCountry() : artistDTO.getCountry());
        searchedArtist.setReleases(releaseList == null ? searchedArtist.getReleases() : releaseList);
        artistRepository.save(searchedArtist);
    }


    @Override
    public void updateByEntity(Artist artist, Integer id) {
        List<Release> releaseList = artist.getReleases();
        Artist searchedArtist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("L'Artista ricercato non è presente"));
        searchedArtist.setIdArtistMusicBrainz(artist.getIdArtistMusicBrainz() == null ? searchedArtist.getIdArtistMusicBrainz() : artist.getIdArtistMusicBrainz());
        searchedArtist.setName(artist.getName() == null ? searchedArtist.getName() : artist.getName());
        searchedArtist.setRelationURLs(artist.getRelationURLs() == null ? searchedArtist.getRelationURLs() : artist.getRelationURLs());
        searchedArtist.setDescription(artist.getDescription() == null ? searchedArtist.getDescription() : artist.getDescription());
        searchedArtist.setGenres(artist.getGenres() == null ? searchedArtist.getGenres() : artist.getGenres());
        searchedArtist.setCountry(artist.getCountry() == null ? searchedArtist.getCountry() : artist.getCountry());
        searchedArtist.setReleases(releaseList == null ? searchedArtist.getReleases() : releaseList);
        artistRepository.save(searchedArtist);
    }

    @Override
    public void delete(Integer id) {
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
        }
    }

    private List<Specification<Artist>> createSpecifications(Integer idArtist, String idArtistMusicBrainz, String name, String description, String country, List<String> genres, String title) {
        List<Specification<Artist>> specifications = new ArrayList<>();
        if (idArtist != null) {
            specifications.add(ArtistSpecifications.likeIdArtist(idArtist));
        }
        if (idArtistMusicBrainz != null) {
            specifications.add(ArtistSpecifications.likeIdArtistMusicBrainz(idArtistMusicBrainz));
        }
        if (name != null) {
            specifications.add(ArtistSpecifications.likeName(name));
        }
        if (description != null) {
            specifications.add(ArtistSpecifications.likeDescription(description));
        }
        if (country != null) {
            specifications.add(ArtistSpecifications.likeCountry(country));
        }
        if (genres != null) {
            specifications.add(ArtistSpecifications.likeGenres(genres));
        }
        if (title != null) {
            specifications.add(ArtistSpecifications.likeTitleRelease(title));
        }
        return specifications;
    }
    private Specification<Artist> combineSpecifications(List<Specification<Artist>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    @Override
    public List<ArtistDTO> getArtistBy(Integer idArtist, String idArtistMusicBrainz, String name, String description, String country, List<String> genres, String title) {
        List<Specification<Artist>> specifications = createSpecifications(idArtist, idArtistMusicBrainz, name, description, country, genres, title);
        Specification<Artist> combinedSpecification = combineSpecifications(specifications);

        List<Artist> artistsList= artistRepository.findAll((Sort) combinedSpecification);

        return artistsList.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }

}
