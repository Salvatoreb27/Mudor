package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.AlbumLiveDTO;
import Mudor.entity.AlbumInStudio;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.repository.AlbumLiveRepository;
import Mudor.services.AlbumLiveService;
import Mudor.services.ArtistaMusicaleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlbumLiveServiceImpl implements AlbumLiveService {

    @Autowired
    private AlbumLiveRepository albumLiveRepository;

    @Autowired
    private ArtistaMusicaleService artistaMusicaleService;

    @Override
    public AlbumLiveDTO mapTODTO(AlbumLive albumLive) {
        AlbumLiveDTO albumLiveDTO = AlbumLiveDTO.builder()
                .titoloAlbumLive(albumLive.getTitoloAlbumLive())
                .dataRilascio(albumLive.getDataRilascio())
                .brani(albumLive.getBrani())
                .generi(albumLive.getGeneri())
                .idArtistaMusicale(albumLive.getArtistaMusicale().getIdArtista())
                .build();
        return albumLiveDTO;
    }

    @Override
    public List<AlbumLiveDTO> mapTODTOList(List<AlbumLive> albumLiveList) {
        return albumLiveList.stream()
                .map((AlbumLive albumLive) -> this.mapTODTO(albumLive))
                .collect(Collectors.toList());
    }

    @Override
    public AlbumLive mapToEntity(AlbumLiveDTO albumLiveDTO) {
        AlbumLive albumLive = AlbumLive.builder()
                .titoloAlbumLive(albumLiveDTO.getTitoloAlbumLive())
                .dataRilascio(albumLiveDTO.getDataRilascio())
                .brani(albumLiveDTO.getBrani())
                .generi(albumLiveDTO.getGeneri())
                .artistaMusicale(artistaMusicaleService.getArtistaMusicaleById(albumLiveDTO.getIdArtistaMusicale()))
                .build();
        return null;
    }

    @Override
    public List<AlbumLive> mapTOEntityList(List<AlbumLiveDTO> albumLiveDTOList) {
        return albumLiveDTOList.stream()
                .map((AlbumLiveDTO albumLiveDTO) -> this.mapToEntity(albumLiveDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumLiveDTO> getDTOs() {
        List<AlbumLive> albumLiveList = new ArrayList<>(albumLiveRepository.findAll());
        return mapTODTOList(albumLiveList);
    }

    @Override
    public AlbumLiveDTO getDTO(Integer id) {
        Optional<AlbumLive> albumLive = albumLiveRepository.findById(id);
        if (albumLive.isPresent()) {
            return mapTODTO(albumLive.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(AlbumLiveDTO albumLiveDTO) {
        AlbumLive albumLive = mapToEntity(albumLiveDTO);
        albumLiveRepository.save(albumLive);
    }

    @Override
    public void update(AlbumLiveDTO albumLiveDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleService.getArtistaMusicaleById(albumLiveDTO.getIdArtistaMusicale());
        AlbumLive albumLiveRicercato = albumLiveRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album live ricercato non è presente"));
        albumLiveRicercato.setTitoloAlbumLive(albumLiveDTO.getTitoloAlbumLive());
        albumLiveRicercato.setDataRilascio(albumLiveDTO.getDataRilascio());
        albumLiveRicercato.setGeneri(albumLiveDTO.getGeneri());
        albumLiveRicercato.setBrani(albumLiveDTO.getBrani());
        albumLiveRicercato.setArtistaMusicale(artistaMusicale);
        albumLiveRepository.save(albumLiveRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (albumLiveRepository.existsById(id)) {
            albumLiveRepository.deleteById(id);
        }
    }

}