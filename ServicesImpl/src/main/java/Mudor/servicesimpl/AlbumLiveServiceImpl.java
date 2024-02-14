package Mudor.servicesimpl;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.repository.AlbumLiveRepository;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.services.AlbumLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumLiveServiceImpl implements AlbumLiveService {

    @Autowired
    private AlbumLiveRepository albumLiveRepository;

    @Autowired
    private ArtistaMusicaleRepository artistaMusicaleRepository;

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
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(albumLiveDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        AlbumLive albumLive = AlbumLive.builder()
                .titoloAlbumLive(albumLiveDTO.getTitoloAlbumLive())
                .dataRilascio(albumLiveDTO.getDataRilascio())
                .brani(albumLiveDTO.getBrani())
                .generi(albumLiveDTO.getGeneri())
                .artistaMusicale(artistaMusicale)
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
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(albumLiveDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        AlbumLive albumLiveRicercato = albumLiveRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album live ricercato non è presente"));
        albumLiveRicercato.setTitoloAlbumLive(albumLiveDTO.getTitoloAlbumLive() == null ? albumLiveRicercato.getTitoloAlbumLive() : albumLiveDTO.getTitoloAlbumLive());
        albumLiveRicercato.setDataRilascio(albumLiveDTO.getDataRilascio() == null ? albumLiveRicercato.getDataRilascio() : albumLiveDTO.getDataRilascio());
        albumLiveRicercato.setGeneri(albumLiveDTO.getGeneri() == null ? albumLiveRicercato.getGeneri() : albumLiveDTO.getGeneri());
        albumLiveRicercato.setBrani(albumLiveDTO.getBrani() == null ? albumLiveRicercato.getBrani() : albumLiveDTO.getBrani());
        albumLiveRicercato.setArtistaMusicale(artistaMusicale == null ? albumLiveRicercato.getArtistaMusicale() : artistaMusicale);
        albumLiveRepository.save(albumLiveRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (albumLiveRepository.existsById(id)) {
            albumLiveRepository.deleteById(id);
        }
    }

}
