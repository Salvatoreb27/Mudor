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

/**
 * Implementazione del servizio per la gestione degli artisti.
 */
@Service
public class ArtistServiceImpl implements ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ReleaseService releaseService;

    /**
     * Ottiene un'entità artista per l'ID su MusicBrainz specificato.
     *
     * @param idMusicBrainz l'ID su MusicBrainz dell'artista da cercare
     * @return l'entità artista corrispondente all'ID su MusicBrainz, se presente, altrimenti null
     */
    @Override
    public Artist getArtistByIdMusicBrainz(String idMusicBrainz) {
        Optional<Artist> artist = artistRepository.findByIdArtistMusicBrainz(idMusicBrainz);
        if (artist.isPresent()) {
            return artist.get();
        } else {
            return null;
        }
    }

    /**
     * Converte un'entità artista in un DTO (Data Transfer Object) corrispondente.
     *
     * @param artist l'entità artista da convertire in DTO
     * @return un DTO che rappresenta l'artista, inclusi i dettagli delle release associate
     */
    @Override
    public ArtistDTO mapTODTO(Artist artist) {
        // Ottiene la lista di DTO delle release associate all'artista
        List<ReleaseDTO> releaseDTOList = releaseService.mapTODTOList(artist.getReleases());

        // Costruisce il DTO dell'artista con i dettagli delle release
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


    /**
     * Converte una lista di entità artisti in una lista di DTO (Data Transfer Object) corrispondenti.
     *
     * @param artistList la lista di entità artisti da convertire in DTO
     * @return una lista di DTO che rappresentano gli artisti, inclusi i dettagli delle release associate
     */
    @Override
    public List<ArtistDTO> mapTODTOList(List<Artist> artistList) {
        // Utilizza stream per mappare ogni entità artista nella sua controparte DTO
        return artistList.stream()
                .map((Artist artist) -> this.mapTODTO(artist))
                .collect(Collectors.toList()); // Raccolta i risultati in una lista
    }


    /**
     * Converte un DTO (Data Transfer Object) artista in un'entità corrispondente.
     *
     * @param artistDTO il DTO artista da convertire in un'entità
     * @return un'entità artista corrispondente al DTO, inclusi i dettagli delle release associate
     */
    @Override
    public Artist mapToEntity(ArtistDTO artistDTO) {
        // Converte la lista di DTO delle release in una lista di entità delle release
        List<Release> releaseList = releaseService.mapTOEntityList(artistDTO.getReleaseDTOList());

        // Costruisce l'entità artista con i dettagli delle release associate
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


    /**
     * Converte una lista di DTO (Data Transfer Object) artisti in una lista di entità corrispondenti.
     *
     * @param artistDTOList la lista di DTO artisti da convertire in entità
     * @return una lista di entità artisti corrispondenti ai DTO, inclusi i dettagli delle release associate
     */
    @Override
    public List<Artist> mapTOEntityList(List<ArtistDTO> artistDTOList) {
        // Utilizza stream per mappare ogni DTO artista nella sua controparte entità
        return artistDTOList.stream()
                .map((ArtistDTO artistDTO) -> this.mapToEntity(artistDTO))
                .collect(Collectors.toList()); // Raccolta i risultati in una lista
    }


    /**
     * Ottiene una lista di DTO (Data Transfer Object) che rappresentano tutti gli artisti presenti nel repository.
     *
     * @return una lista di DTO che rappresentano gli artisti, inclusi i dettagli delle release associate
     */
    @Override
    public List<ArtistDTO> getDTOs() {
        // Ottiene tutti gli artisti dal repository
        List<Artist> artists = new ArrayList<>(artistRepository.findAll());

        // Converte la lista di entità artisti in una lista di DTO artisti
        return mapTODTOList(artists);
    }


    /**
     * Ottiene il DTO (Data Transfer Object) che rappresenta l'artista con l'ID specificato.
     *
     * @param id l'ID dell'artista da cercare
     * @return il DTO che rappresenta l'artista con l'ID specificato, inclusi i dettagli delle release associate, se presente, altrimenti null
     */
    @Override
    public ArtistDTO getDTO(Integer id) {
        // Cerca l'artista nel repository tramite l'ID
        Optional<Artist> artist = artistRepository.findById(id);

        // Se l'artista è presente, converte l'entità artista in un DTO e restituisce
        // altrimenti restituisce null
        return artist.map(this::mapTODTO).orElse(null);
    }


    /**
     * Aggiunge un nuovo artista utilizzando i dati forniti nel DTO.
     *
     * @param artistDTO il DTO che contiene i dati dell'artista da aggiungere
     * @return l'entità artista aggiunta al repository
     */
    @Override
    public Artist add(ArtistDTO artistDTO) {
        // Converte il DTO dell'artista in un'entità artista
        Artist artist = mapToEntity(artistDTO);

        // Salva l'entità artista nel repository e restituisce l'entità aggiunta
        return artistRepository.save(artist);
    }


    /**
     * Aggiorna le informazioni di un artista esistente con i dati forniti nel DTO.
     *
     * @param artistDTO il DTO che contiene i dati aggiornati dell'artista
     * @param id l'ID dell'artista da aggiornare
     * @return l'entità artista aggiornata nel repository, o un artista vuoto se l'aggiornamento non è riuscito a causa di un'eccezione
     */
    @Override
    public Artist update(ArtistDTO artistDTO, Integer id) {
        // Crea un artista vuoto per gestire eventuali eccezioni
        Artist searchedArtist = new Artist();

        try {
            // Converte la lista di DTO delle release in una lista di entità delle release
            List<Release> releaseList = releaseService.mapTOEntityList(artistDTO.getReleaseDTOList());

            // Cerca l'artista nel repository tramite l'ID, altrimenti solleva un'eccezione se non presente
            searchedArtist = artistRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Artist not found while searching"));

            // Aggiorna le informazioni dell'artista con i dati forniti nel DTO, se i dati non sono nulli
            searchedArtist.setIdArtistMusicBrainz(artistDTO.getIdArtistMusicBrainz() == null ? searchedArtist.getIdArtistMusicBrainz() : artistDTO.getIdArtistMusicBrainz());
            searchedArtist.setName(artistDTO.getName() == null ? searchedArtist.getName() : artistDTO.getName());
            searchedArtist.setRelationURLs(artistDTO.getRelationURLs() == null ? searchedArtist.getRelationURLs() : artistDTO.getRelationURLs());
            searchedArtist.setDescription(artistDTO.getDescription() == null ? searchedArtist.getDescription() : artistDTO.getDescription());
            searchedArtist.setGenres(artistDTO.getGenres() == null ? searchedArtist.getGenres() : artistDTO.getGenres());
            searchedArtist.setCountry(artistDTO.getCountry() == null ? searchedArtist.getCountry() : artistDTO.getCountry());
            searchedArtist.setReleases(releaseList == null ? searchedArtist.getReleases() : releaseList);

            // Salva l'artista aggiornato nel repository
            searchedArtist = artistRepository.save(searchedArtist);

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        }

        // Restituisce l'entità artista aggiornata o un artista vuoto in caso di eccezione
        return searchedArtist;
    }



    @Override
    public void updateByEntity(Artist artist, Integer id) {
        List<Release> releaseList = artist.getReleases();
        try {
            Artist searchedArtist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found while searching"));
            searchedArtist.setIdArtistMusicBrainz(artist.getIdArtistMusicBrainz() == null ? searchedArtist.getIdArtistMusicBrainz() : artist.getIdArtistMusicBrainz());
            searchedArtist.setName(artist.getName() == null ? searchedArtist.getName() : artist.getName());
            searchedArtist.setRelationURLs(artist.getRelationURLs() == null ? searchedArtist.getRelationURLs() : artist.getRelationURLs());
            searchedArtist.setDescription(artist.getDescription() == null ? searchedArtist.getDescription() : artist.getDescription());
            searchedArtist.setGenres(artist.getGenres() == null ? searchedArtist.getGenres() : artist.getGenres());
            searchedArtist.setCountry(artist.getCountry() == null ? searchedArtist.getCountry() : artist.getCountry());
            searchedArtist.setReleases(releaseList == null ? searchedArtist.getReleases() : releaseList);
            artistRepository.save(searchedArtist);

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        }
    }

    /**
     * Cancella l'artista corrispondente all'ID specificato.
     *
     * @param id l'ID dell'artista da cancellare
     */
    @Override
    public void delete(Integer id) {
        // Verifica se esiste un'artista con l'ID specificato e lo cancella se presente
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
        }
    }


    /**
     * Crea una lista di specificazioni per la ricerca degli artisti in base ai criteri specificati.
     *
     * @param idArtist           l'ID dell'artista
     * @param idArtistMusicBrainz l'ID dell'artista su MusicBrainz
     * @param name               il nome dell'artista
     * @param description        la descrizione dell'artista
     * @param country            il paese dell'artista
     * @param genres             i generi musicali dell'artista
     * @param title              il titolo di una release dell'artista
     * @return una lista di specificazioni per la ricerca degli artisti
     */
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


    /**
     * Combina una lista di specificazioni per la ricerca degli artisti utilizzando l'operatore logico AND.
     *
     * @param specifications la lista di specificazioni da combinare
     * @return la specificazione combinata con l'operatore logico AND, o null se la lista è vuota
     */
    private Specification<Artist> combineSpecifications(List<Specification<Artist>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }


    /**
     * Ottiene una lista di artisti in base ai criteri specificati.
     *
     * @param idArtist            l'ID dell'artista
     * @param idArtistMusicBrainz l'ID dell'artista su MusicBrainz
     * @param name                il nome dell'artista
     * @param description         la descrizione dell'artista
     * @param country             il paese dell'artista
     * @param genres              la lista dei generi dell'artista
     * @param title               il titolo della release associata all'artista
     * @return una lista di ArtistDTO che soddisfano i criteri specificati
     */
    @Override
    public List<ArtistDTO> getArtistBy(Integer idArtist, String idArtistMusicBrainz, String name, String description, String country, List<String> genres, String title) {
        List<Specification<Artist>> specifications = createSpecifications(idArtist, idArtistMusicBrainz, name, description, country, genres, title);
        Specification<Artist> combinedSpecification = combineSpecifications(specifications);

        List<Artist> artistsList = artistRepository.findAll(combinedSpecification);

        return artistsList.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }

}
