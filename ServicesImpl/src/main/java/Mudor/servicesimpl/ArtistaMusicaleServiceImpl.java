package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.entity.*;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

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

    public ArtistaMusicaleDTO mapTOArtistaMusicaleDTO(ArtistaMusicale artistaMusicale) {
        List<AlbumInStudioDTO> albumInStudioDTOList = albumInStudioService.mapTOAlbumInStudioDTOList(artistaMusicale.getAlbumsInStudio());
        List<AlbumLiveDTO> albumLiveDTOList = albumLiveService.mapTOAlbumLiveDTOList(artistaMusicale.getAlbumsLive());
        List<String> singoliArtista = crossArtistaSingoloService.getSingoliByArtistaMusicale(artistaMusicale.getIdArtista());
        List<RaccoltaDTO> raccoltaDTOList = raccoltaService.mapTORaccoltaDTOList(artistaMusicale.getRaccolte());
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
    public List<ArtistaMusicaleDTO> mapTOArtistaMusicaleDTOList(List<ArtistaMusicale> artistaMusicaleList) {
        return artistaMusicaleList.stream()
                .map(this::mapTOArtistaMusicaleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistaMusicale mapToArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO) {
        List<AlbumInStudio> albumInStudioList = albumInStudioService.mapTOAlbumInStudioList(artistaMusicaleDTO.getAlbumsInStudioDTOS());
        List<AlbumLive> albumLiveList = albumLiveService.mapTOAlbumLiveList(artistaMusicaleDTO.getAlbumsLiveDTOS());
        List<CrossArtistaSingolo> crossArtistaSingoloList = crossArtistaSingoloService.getAssociationListByListOfSingoli(artistaMusicaleDTO.getSingoli());
        List<Raccolta> raccoltaList = raccoltaService.mapTORaccoltaList(artistaMusicaleDTO.getRaccoltaDTOS());
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
    public List<ArtistaMusicale> mapTOArtistaMusicaleList(List<ArtistaMusicaleDTO> artistaMusicaleDTOList) {
        return artistaMusicaleDTOList.stream()
                .map(this::mapToArtistaMusicale)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArtistaMusicaleDTO> getArtistiMusicali() {
        List<ArtistaMusicale> artisti = new ArrayList<>();
        return mapTOArtistaMusicaleDTOList(artisti);
    }

    @Override
    public ArtistaMusicaleDTO getArtistaMusicaleDTO(Integer id) {
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findById(id);
        if (artistaMusicale.isPresent()){
            return mapTOArtistaMusicaleDTO(artistaMusicale.get());
        } else {
            return null;
        }

    }

    @Override
    public ArtistaMusicale getArtistaMusicale(Integer id) {
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findById(id);
        if (artistaMusicale.isPresent()) {
            return artistaMusicale.get();
        } else {
            return null;
        }
    }

    @Override
    public void addArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO) {
        ArtistaMusicale artistaMusicale = mapToArtistaMusicale(artistaMusicaleDTO);
        artistaMusicaleRepository.save(artistaMusicale);
    }

    @Override
    public void updateArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id) {
        List<AlbumInStudio> albumInStudioList = albumInStudioService.mapTOAlbumInStudioList(artistaMusicaleDTO.getAlbumsInStudioDTOS());
        List<AlbumLive> albumLiveList = albumLiveService.mapTOAlbumLiveList(artistaMusicaleDTO.getAlbumsLiveDTOS());
        List<CrossArtistaSingolo> crossArtistaSingoloList = crossArtistaSingoloService.getAssociationListByListOfSingoli(artistaMusicaleDTO.getSingoli());
        List<Raccolta> raccoltaList = raccoltaService.mapTORaccoltaList(artistaMusicaleDTO.getRaccoltaDTOS());
        ArtistaMusicale artistaMusicaleRicercato = artistaMusicaleRepository.findById(id).orElseThrow(() -> new RuntimeException("L'Artista ricercato non è presente"));
        artistaMusicaleRicercato.setNome(artistaMusicaleDTO.getNome());
        artistaMusicaleRicercato.setDescrizione(artistaMusicaleDTO.getDescrizione());
        artistaMusicaleRicercato.setGeneri(artistaMusicaleDTO.getGeneri());
        artistaMusicaleRicercato.setPaeseDOrigine(artistaMusicaleDTO.getPaeseDOrigine());
        artistaMusicaleRicercato.setAlbumsInStudio(albumInStudioList);
        artistaMusicaleRicercato.setAlbumsLive(albumLiveList);
        artistaMusicaleRicercato.setCrossArtistaSingolos(crossArtistaSingoloList);
        artistaMusicaleRicercato.setRaccolte(raccoltaList);
    }


    @Override
    public void deleteArtistaMusicale(Integer id) {
        if (artistaMusicaleRepository.existsById(id)) {
            artistaMusicaleRepository.deleteById(id);
        }
    }

}
