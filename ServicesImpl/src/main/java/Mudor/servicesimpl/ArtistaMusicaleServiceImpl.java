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
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findByNomeArtistaMusicale(nome);
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
        artistaMusicaleRicercato.setNome(artistaMusicaleDTO.getNome());
        artistaMusicaleRicercato.setDescrizione(artistaMusicaleDTO.getDescrizione());
        artistaMusicaleRicercato.setGeneri(artistaMusicaleDTO.getGeneri());
        artistaMusicaleRicercato.setPaeseDOrigine(artistaMusicaleDTO.getPaeseDOrigine());
        artistaMusicaleRicercato.setAlbumsInStudio(albumInStudioList);
        artistaMusicaleRicercato.setAlbumsLive(albumLiveList);
        artistaMusicaleRicercato.setCrossArtistaSingolos(crossArtistaSingoloList);
        artistaMusicaleRicercato.setRaccolte(raccoltaList);
        artistaMusicaleRepository.save(artistaMusicaleRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (artistaMusicaleRepository.existsById(id)) {
            artistaMusicaleRepository.deleteById(id);
        }
    }

}
