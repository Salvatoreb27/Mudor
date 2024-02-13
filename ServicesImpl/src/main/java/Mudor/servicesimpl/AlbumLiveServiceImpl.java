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
    public AlbumLiveDTO mapTOAlbumLiveDTO(AlbumLive albumLive) {
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
    public List<AlbumLiveDTO> mapTOAlbumLiveDTOList(List<AlbumLive> albumsLiveList) {
        return albumsLiveList.stream()
                .map(this::mapTOAlbumLiveDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumLive mapToAlbumLive(AlbumLiveDTO albumLiveDTO) {
        AlbumLive albumLive = AlbumLive.builder()
                .titoloAlbumLive(albumLiveDTO.getTitoloAlbumLive())
                .dataRilascio(albumLiveDTO.getDataRilascio())
                .brani(albumLiveDTO.getBrani())
                .generi(albumLiveDTO.getGeneri())
                .artistaMusicale(artistaMusicaleService.getArtistaMusicale(albumLiveDTO.getIdArtistaMusicale()))
                .build();
        return null;
    }

    @Override
    public List<AlbumLive> mapTOAlbumLiveList(List<AlbumLiveDTO> albumsLiveDTOList) {
        return albumsLiveDTOList.stream()
                .map(this::mapToAlbumLive)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumLiveDTO> getAlbumsLive() {
        List<AlbumLive> albumLiveList = new ArrayList<>();
        return mapTOAlbumLiveDTOList(albumLiveList);
    }

    @Override
    public AlbumLiveDTO getAlbumLive(Integer id) {
        Optional<AlbumLive> albumLive = albumLiveRepository.findById(id);
        if (albumLive.isPresent()) {
            return mapTOAlbumLiveDTO(albumLive.get());
        } else {
            return null;
        }
    }

    @Override
    public void addAlbumLive(AlbumLiveDTO albumLiveDTO) {
        AlbumLive albumLive = mapToAlbumLive(albumLiveDTO);
        albumLiveRepository.save(albumLive);
    }


    @Override
    public void updateAlbumLive(AlbumLiveDTO albumLiveDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleService.getArtistaMusicale(albumLiveDTO.getIdArtistaMusicale());
        AlbumLive albumLiveRicercato = albumLiveRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album live ricercato non è presente"));
        albumLiveRicercato.setTitoloAlbumLive(albumLiveDTO.getTitoloAlbumLive());
        albumLiveRicercato.setDataRilascio(albumLiveDTO.getDataRilascio());
        albumLiveRicercato.setGeneri(albumLiveDTO.getGeneri());
        albumLiveRicercato.setBrani(albumLiveDTO.getBrani());
        albumLiveRicercato.setArtistaMusicale(artistaMusicale);
    }

    @Override
    public void deleteAlbumLive(Integer id) {
        if (albumLiveRepository.existsById(id)) {
            albumLiveRepository.deleteById(id);
        }
    }

}
