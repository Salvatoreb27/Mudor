package Mudor.servicesimpl;

import Mudor.DTO.*;
import Mudor.entity.*;
import Mudor.entity.spec.ArtistaMusicaleSpecifications;
import Mudor.entity.spec.SingoloSpecifications;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class ArtistaMusicaleServiceImpl implements ArtistaMusicaleService {
    @Autowired
    private ArtistaMusicaleRepository artistaMusicaleRepository;
    @Autowired
    private AlbumInStudioService albumInStudioService;
    @Autowired
    private AlbumLiveService albumLiveService;
    @Autowired
    private CrossArtistaSingoloService crossArtistaSingoloService;
    @Autowired
    private RaccoltaService raccoltaService;

    @Override
    public ArtistaMusicale getArtistaMusicaleById(Integer id) {
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findById(id);
        if (artistaMusicale.isPresent()) {
            return artistaMusicale.get();
        } else {
            return null;
        }
    }

    @Override
    public ArtistaMusicale getArtistaMusicaleByNome(String nome) {
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findByNome(nome);
        if (artistaMusicale.isPresent()) {
            return artistaMusicale.get();
        } else {
            return null;
        }
    }

    @Override
    public ArtistaMusicaleDTO mapTODTO(ArtistaMusicale artistaMusicale) {
        List<AlbumInStudioDTO> albumInStudioDTOList = albumInStudioService.mapTODTOList(artistaMusicale.getAlbumsInStudio());
        List<AlbumLiveDTO> albumLiveDTOList = albumLiveService.mapTODTOList(artistaMusicale.getAlbumsLive());
        List<String> singoliArtista = crossArtistaSingoloService.getSingoliByArtistaMusicale(artistaMusicale.getIdArtista());
        List<RaccoltaDTO> raccoltaDTOList = raccoltaService.mapTODTOList(artistaMusicale.getRaccolte());
        ArtistaMusicaleDTO artistaMusicaleDTO = ArtistaMusicaleDTO.builder()
                .nome(artistaMusicale.getNome())
                .descrizione(artistaMusicale.getDescrizione())
                .paeseDOrigine(artistaMusicale.getPaeseDOrigine())
                .generi(artistaMusicale.getGeneri())
                .albumsInStudioDTOS(albumInStudioDTOList)
                .albumsLiveDTOS(albumLiveDTOList)
                .singoli(singoliArtista)
                .raccoltaDTOS(raccoltaDTOList)
                .build();
        return artistaMusicaleDTO;
    }

    @Override
    public List<ArtistaMusicaleDTO> mapTODTOList(List<ArtistaMusicale> artistaMusicaleList) {
        return artistaMusicaleList.stream()
                .map((ArtistaMusicale artistaMusicale) -> this.mapTODTO(artistaMusicale))
                .collect(Collectors.toList());
    }

    @Override
    public ArtistaMusicale mapToEntity(ArtistaMusicaleDTO artistaMusicaleDTO) {
        List<AlbumInStudio> albumInStudioList = albumInStudioService.mapTOEntityList(artistaMusicaleDTO.getAlbumsInStudioDTOS());
        List<AlbumLive> albumLiveList = albumLiveService.mapTOEntityList(artistaMusicaleDTO.getAlbumsLiveDTOS());
        List<CrossArtistaSingolo> crossArtistaSingoloList = crossArtistaSingoloService.getAssociationListByListOfSingoli(artistaMusicaleDTO.getSingoli());
        List<Raccolta> raccoltaList = raccoltaService.mapTOEntityList(artistaMusicaleDTO.getRaccoltaDTOS());
        ArtistaMusicale artistaMusicale = ArtistaMusicale.builder()
                .nome(artistaMusicaleDTO.getNome())
                .descrizione(artistaMusicaleDTO.getDescrizione())
                .generi(artistaMusicaleDTO.getGeneri())
                .paeseDOrigine(artistaMusicaleDTO.getPaeseDOrigine())
                .albumsInStudio(albumInStudioList)
                .albumsLive(albumLiveList)
                .crossArtistaSingolos(crossArtistaSingoloList)
                .raccolte(raccoltaList)
                .build();
        return artistaMusicale;
    }

    @Override
    public List<ArtistaMusicale> mapTOEntityList(List<ArtistaMusicaleDTO> artistaMusicaleDTOList) {
        return artistaMusicaleDTOList.stream()
                .map((ArtistaMusicaleDTO artistaMusicaleDTO) -> this.mapToEntity(artistaMusicaleDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArtistaMusicaleDTO> getDTOs() {
        List<ArtistaMusicale> artisti = new ArrayList<>(artistaMusicaleRepository.findAll());
        return mapTODTOList(artisti);
    }

    @Override
    public ArtistaMusicaleDTO getDTO(Integer id) {
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findById(id);
        if (artistaMusicale.isPresent()) {
            return mapTODTO(artistaMusicale.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(ArtistaMusicaleDTO artistaMusicaleDTO) {
        ArtistaMusicale artistaMusicale = mapToEntity(artistaMusicaleDTO);
        artistaMusicaleRepository.save(artistaMusicale);
    }

    @Override
    public void update(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id) {
        List<AlbumInStudio> albumInStudioList = albumInStudioService.mapTOEntityList(artistaMusicaleDTO.getAlbumsInStudioDTOS());
        List<AlbumLive> albumLiveList = albumLiveService.mapTOEntityList(artistaMusicaleDTO.getAlbumsLiveDTOS());
        List<CrossArtistaSingolo> crossArtistaSingoloList = crossArtistaSingoloService.getAssociationListByListOfSingoli(artistaMusicaleDTO.getSingoli());
        List<Raccolta> raccoltaList = raccoltaService.mapTOEntityList(artistaMusicaleDTO.getRaccoltaDTOS());
        ArtistaMusicale artistaMusicaleRicercato = artistaMusicaleRepository.findById(id).orElseThrow(() -> new RuntimeException("L'Artista ricercato non è presente"));
        artistaMusicaleRicercato.setNome(artistaMusicaleDTO.getNome() == null ? artistaMusicaleRicercato.getNome() : artistaMusicaleDTO.getNome());
        artistaMusicaleRicercato.setDescrizione(artistaMusicaleDTO.getDescrizione() == null ? artistaMusicaleRicercato.getDescrizione() : artistaMusicaleDTO.getDescrizione());
        artistaMusicaleRicercato.setGeneri(artistaMusicaleDTO.getGeneri() == null ? artistaMusicaleRicercato.getGeneri() : artistaMusicaleDTO.getGeneri());
        artistaMusicaleRicercato.setPaeseDOrigine(artistaMusicaleDTO.getPaeseDOrigine() == null ? artistaMusicaleRicercato.getPaeseDOrigine() : artistaMusicaleDTO.getPaeseDOrigine());
        artistaMusicaleRicercato.setAlbumsInStudio(albumInStudioList == null ? artistaMusicaleRicercato.getAlbumsInStudio() : albumInStudioList);
        artistaMusicaleRicercato.setAlbumsLive(albumLiveList == null ? artistaMusicaleRicercato.getAlbumsLive() : albumLiveList);
        artistaMusicaleRicercato.setCrossArtistaSingolos(crossArtistaSingoloList == null ? artistaMusicaleRicercato.getCrossArtistaSingolos() : crossArtistaSingoloList);
        artistaMusicaleRicercato.setRaccolte(raccoltaList == null ? artistaMusicaleRicercato.getRaccolte() : raccoltaList);
        artistaMusicaleRepository.save(artistaMusicaleRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (artistaMusicaleRepository.existsById(id)) {
            artistaMusicaleRepository.deleteById(id);
        }
    }

    private List<Specification<ArtistaMusicale>> createSpecifications(Integer idArtistaMusicale, String nome, String descrizione, String paeseDOrigine,List<String> generi, String titoloAlbumInStudio, String titoloAlbumLive, String titoloSingolo, String titoloRaccolta) {
        List<Specification<ArtistaMusicale>> specifications = new ArrayList<>();
        if (idArtistaMusicale != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeIdArtistaMusicale(idArtistaMusicale));
        }
        if (nome != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeNome(nome));
        }
        if (descrizione != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeDescrizione(descrizione));
        }
        if (paeseDOrigine != null) {
            specifications.add(ArtistaMusicaleSpecifications.likePaeseDOrigine(paeseDOrigine));
        }
        if (generi != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeGeneri(generi));
        }
        if (titoloAlbumInStudio != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeTitoloAlbumInStudio(titoloAlbumInStudio));
        }
        if (titoloAlbumLive != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeTitoloAlbumLive(titoloAlbumLive));
        }
        if (titoloSingolo != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeTitoloSingolo(titoloSingolo));
        }
        if (titoloRaccolta != null) {
            specifications.add(ArtistaMusicaleSpecifications.likeTitoloRaccolta(titoloRaccolta));
        }
        return specifications;
    }
    private Specification<ArtistaMusicale> combineSpecifications(List<Specification<ArtistaMusicale>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    @Override
    public List<ArtistaMusicaleDTO> getArtistaMusicaleBy(Integer idArtistaMusicale, String nome, String descrizione, String paeseDOrigine,List<String> generi, String titoloAlbumInStudio, String titoloAlbumLive, String titoloSingolo, String titoloRaccolta) {
        List<Specification<ArtistaMusicale>> specifications = createSpecifications(idArtistaMusicale, nome, descrizione, paeseDOrigine, generi, titoloAlbumInStudio, titoloAlbumLive, titoloSingolo, titoloRaccolta);
        Specification<ArtistaMusicale> combinedSpecification = combineSpecifications(specifications);

        List<ArtistaMusicale> listaArtistiMusicali= artistaMusicaleRepository.findAll((Sort) combinedSpecification);

        return listaArtistiMusicali.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }

}
