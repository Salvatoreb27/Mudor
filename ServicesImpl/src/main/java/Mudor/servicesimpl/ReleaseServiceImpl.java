package Mudor.servicesimpl;

import Mudor.DTO.ArtistDTO;
import Mudor.DTO.ReleaseDTO;
import Mudor.entity.Artist;
import Mudor.entity.Release;
import Mudor.entity.spec.ReleaseSpecifications;
import Mudor.repository.ArtistRepository;
import Mudor.repository.ReleaseRepository;
import Mudor.services.ReleaseService;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementazione del servizio ReleaseService che fornisce funzionalità per gestire le release nel sistema.
 * Questa classe fornisce metodi per interagire con le release nel database, inclusi metodi per recuperare, aggiungere e aggiornare le release.
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ReleaseRepository releaseRepository;
    @PersistenceContext
    private EntityManager entityManager;



    /**
     * Mappa un'entità artista in un DTO di artista.
     *
     * @param artist l'entità artista da mappare
     * @return l'oggetto DTO di artista corrispondente
     */
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

    /**
     * Mappa una lista di entità artista in una lista di DTO di artisti.
     *
     * @param artistList la lista di entità artisti da mappare
     * @return la lista di oggetti DTO di artisti corrispondente
     */
    private List<ArtistDTO> mapTOArtistDTOList(List<Artist> artistList) {
        return artistList.stream()
                .map((Artist artist) -> this.mapTOArtistDTO(artist))
                .collect(Collectors.toList());
    }

    /**
     * Mappa un DTO di artista in un'entità artista.
     *
     * @param artistDTO il DTO di artista da mappare
     * @return l'entità artista corrispondente
     */
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

    /**
     * Mappa una lista di DTO di artisti in una lista di entità artisti.
     *
     * @param artistDTOList la lista di DTO di artisti da mappare
     * @return la lista di entità artisti corrispondente
     */
    private List<Artist> mapTOArtistEntityList(List<ArtistDTO> artistDTOList) {
        return artistDTOList.stream()
                .map((ArtistDTO artistDTO) -> this.mapToArtistEntity(artistDTO))
                .collect(Collectors.toList());
    }

    /**
     * Mappa un'entità di release in un DTO di release.
     *
     * @param release l'entità di release da mappare
     * @return l'oggetto DTO di release corrispondente
     */
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


    /**
     * Mappa una lista di entità di release in una lista di DTO di release.
     *
     * @param releaseList la lista di entità di release da mappare
     * @return la lista di oggetti DTO di release corrispondente
     */
    @Override
    public List<ReleaseDTO> mapTODTOList(List<Release> releaseList) {
        return releaseList.stream()
                .map((Release release) -> this.mapTODTO(release))
                .collect(Collectors.toList());
    }

    /**
     * Mappa un DTO di release in un'entità di release.
     *
     * @param releaseDTO il DTO di release da mappare
     * @return l'entità di release corrispondente
     */
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

    /**
     * Mappa una lista di DTO di release in una lista di entità di release.
     *
     * @param releaseDTOList la lista di DTO di release da mappare
     * @return la lista di entità di release corrispondente
     */
    @Override
    public List<Release> mapTOEntityList(List<ReleaseDTO> releaseDTOList) {
        return releaseDTOList.stream()
                .map((ReleaseDTO releaseDTO) -> this.mapToEntity(releaseDTO))
                .collect(Collectors.toList());
    }

    /**
     * Ottiene tutte le release nel sistema.
     *
     * @return la lista di tutte le release nel sistema
     */
    public List<Release> getAll() {
        return releaseRepository.findAll();
    }

    /**
     * Ottiene le release associate a un determinato artista.
     *
     * @param artistName il nome dell'artista
     * @return la lista delle release associate all'artista specificato
     */
    public List<Release> getReleasesByArtistName(String artistName) {
        return releaseRepository.findReleasesByArtistsName(artistName);
    }

    /**
     * Ottiene una release in base al titolo e al nome dell'artista associato.
     *
     * @param title il titolo della release
     * @param artistName il nome dell'artista associato alla release
     * @return la release corrispondente al titolo e all'artista specificati
     */
    public Release getReleaseByTitleAndArtistName(String title, String artistName) {
        return releaseRepository.findReleaseByTitleAndArtistsName(title, artistName);
    }


    /**
     * Ottiene una lista di DTO di release per tutte le release nel sistema.
     *
     * @return la lista di DTO di release per tutte le release nel sistema
     */
    @Override
    public List<ReleaseDTO> getDTOs() {
        List<Release> releaseList = new ArrayList<>(releaseRepository.findAll());
        return mapTODTOList(releaseList);
    }

    /**
     * Ottiene il DTO di release per l'ID specificato.
     *
     * @param id l'ID della release
     * @return il DTO di release corrispondente all'ID specificato, o null se non trovato
     */
    @Override
    public ReleaseDTO getDTO(Integer id) {
        Optional<Release> release = releaseRepository.findById(id);
        if (release.isPresent()) {
            return mapTODTO(release.get());
        } else {
            return null;
        }
    }



    /**
     * Ottiene una release in base all'ID MusicBrainz del release-group.
     *
     * @param idMusicBrainz l'ID MusicBrainz del release-group
     * @return la release corrispondente all'ID MusicBrainz specificato, o null se non trovata
     */
    @Override
    public Release getReleaseGroupByIdMusicBrainz(String idMusicBrainz) {
        Optional <Release> release = releaseRepository.findByIdReleaseGroupMusicBrainz(idMusicBrainz);
        if (release.isPresent()) {
            return release.get();
        } else {
            return null;
        }
    }


    /**
     * Aggiunge una nuova release al sistema utilizzando i dati del DTO di release specificato.
     *
     * @param releaseDTO il DTO di release che contiene i dati della release da aggiungere
     * @return la release aggiunta
     */
    @Override
    public Release add(ReleaseDTO releaseDTO) {
        Release release = mapToEntity(releaseDTO);
        return releaseRepository.save(release);
    }

    /**
     * Aggiorna una release esistente nel sistema utilizzando i dati del DTO di release specificato e l'ID della release.
     *
     * @param releaseDTO il DTO di release che contiene i dati aggiornati della release
     * @param id l'ID della release da aggiornare
     * @return la release aggiornata, o null se la release cercata non è presente
     */
    @Override
    public Release update(ReleaseDTO releaseDTO, Integer id) {

        Release releaseSaved = null;

        try {
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

            releaseSaved = releaseRepository.save(searchedRelease);

            for (Artist artist : releaseSaved.getArtists()) {
                for (Release artistRelease : artist.getReleases()) {
                    if (artistRelease.getIdRelease().equals(searchedRelease.getIdRelease())) {
                        artist.getReleases().remove(artistRelease);
                        artist.getReleases().add(releaseSaved);
                    }
                }
            }
        }catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        }
        return releaseSaved;
    }

    /**
     * Aggiorna una release esistente nel sistema utilizzando i dati dell'entità di release specificata e l'ID della release.
     *
     * @param release l'entità di release che contiene i dati aggiornati della release
     * @param id l'ID della release da aggiornare
     */
    public void updateByEntity(Release release, Integer id) {
        try {

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

        } catch (RuntimeException runtimeException) {
            runtimeException.printStackTrace();
        }
    }


    /**
     * Elimina una release dal sistema utilizzando l'ID specificato.
     * Se la release non esiste, viene gestita un'eccezione EntityNotFoundException.
     *
     * @param id l'ID della release da eliminare
     */
    @Transactional
    @Override
    public void delete(Integer id) {

        try {
            Release release = releaseRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Release not found"));

            // Memorizza gli artisti da rimuovere
            List<Artist> artistToRemove = new ArrayList<>();
            for (Artist artist : release.getArtists()) {
                artistToRemove.add(artist);
            }

            // Rimuovi la Release dagli artisti
            for (Artist artista : artistToRemove) {
                artista.getReleases().remove(release);
                artistRepository.save(artista);
            }

            // Ora puoi eliminare la Release
            releaseRepository.delete(release);

        }catch (EntityNotFoundException entityNotFoundException) {
            entityNotFoundException.printStackTrace();
        }
    }

    /**
     * Crea una specifica per la ricerca delle release in base al tipo (kind) e al nome dell'artista.
     *
     * @param kind       il tipo di release da cercare
     * @param artistName il nome dell'artista associato alle release
     * @return una specifica per la ricerca delle release in base al tipo e al nome dell'artista
     */
    private Specification<Release> createkindAndArtistSpecification(String kind, String artistName) {
        return Specification.where(ReleaseSpecifications.findByKindAndArtistsName(kind, artistName));
    }


    /**
     * Ottiene una lista di release in base al tipo (kind) e al nome dell'artista associato.
     *
     * @param kind       il tipo di release da cercare
     * @param artistName il nome dell'artista associato alle release
     * @return una lista di release corrispondenti ai criteri di ricerca specificati
     */
    public List<Release> getReleasesByKindAndArtistsName(String kind, String artistName) {
        Specification<Release> specifications = createkindAndArtistSpecification(kind, artistName);
        List<Release> releaseList = releaseRepository.findAll(specifications);

        return releaseList.stream()
                .collect(Collectors.toList());
    }

    /**
     * Crea una lista di specifiche per la ricerca delle release basate sui criteri specificati.
     *
     * @param idRelease               l'ID della release
     * @param idReleaseGroupMusicBrainz l'ID del gruppo di release su MusicBrainz
     * @param idReleaseMusicBrainz   l'ID della release su MusicBrainz
     * @param title                   il titolo della release
     * @param kind                    il tipo di release
     * @param coverArt                l'URL dell'immagine di copertina della release
     * @param dateOfRelease           la data di rilascio della release
     * @param tracks                  la lista dei brani della release
     * @param genres                  la lista dei generi della release
     * @param name                    il nome dell'artista associato alla release
     * @return una lista di specifiche per la ricerca delle release
     */
    private List<Specification<Release>> createSpecifications(Integer idRelease, String idReleaseGroupMusicBrainz , String idReleaseMusicBrainz, String title, String kind, String coverArt, String dateOfRelease, List<String> tracks, List<String> genres, String name) {
        List<Specification<Release>> specifications = new ArrayList<>();
        if (idRelease != null) {
            specifications.add(ReleaseSpecifications.likeIdRelease(idRelease));
        }
        if (idReleaseGroupMusicBrainz != null) {
            specifications.add(ReleaseSpecifications.likeIdReleaseGroupMusicBrainz(idReleaseGroupMusicBrainz));
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

    /**
     * Combina una lista di specifiche per formare una singola specifica che rappresenta la congiunzione logica di tutte le specifiche fornite.
     *
     * @param specifications la lista di specifiche da combinare
     * @return una specifica combinata che rappresenta la congiunzione logica di tutte le specifiche fornite,
     *         oppure null se la lista di specifiche è vuota
     */
    private Specification<Release> combineSpecifications(List<Specification<Release>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }


    /**
     * Ottiene una lista di DTO delle release in base ai criteri specificati.
     *
     * @param idRelease               l'ID della release
     * @param idReleaseGroupMusicBrainz l'ID del gruppo di release su MusicBrainz
     * @param idReleaseMusicBrainz   l'ID della release su MusicBrainz
     * @param title                   il titolo della release
     * @param kind                    il tipo di release
     * @param coverArt                l'URL dell'immagine di copertina della release
     * @param dateOfRelease           la data di rilascio della release
     * @param tracks                  la lista dei brani della release
     * @param genres                  la lista dei generi della release
     * @param name                    il nome dell'artista associato alla release
     * @return una lista di DTO delle release che corrispondono ai criteri di ricerca specificati
     */
    @Override
    public List<ReleaseDTO> getReleaseBy(Integer idRelease,String idReleaseGroupMusicBrainz, String idReleaseMusicBrainz, String title, String kind, String coverArt, String dateOfRelease, List<String> tracks, List<String> genres, String name) {
        List<Specification<Release>> specifications = createSpecifications(idRelease, idReleaseGroupMusicBrainz, idReleaseMusicBrainz, title, kind, coverArt, dateOfRelease, tracks, genres, name);
        Specification<Release> combinedSpecification = combineSpecifications(specifications);

        List<Release> releaseList = releaseRepository.findAll((Sort) combinedSpecification);

        return releaseList.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }
}

