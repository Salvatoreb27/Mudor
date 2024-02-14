package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.*;
import Mudor.repository.AlbumInStudioRepository;
import Mudor.services.AlbumInStudioService;
import Mudor.services.ArtistaMusicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AlbumInStudioServiceImpl implements AlbumInStudioService {
    @Autowired
    private AlbumInStudioRepository albumInStudioRepository;
    @Autowired
    private ArtistaMusicaleService artistaMusicaleService;


    @Override
    public AlbumInStudioDTO mapTODTO(AlbumInStudio albumInStudio) {
        AlbumInStudioDTO albumInStudioDTO = AlbumInStudioDTO.builder()
                .titoloAlbumInStudio(albumInStudio.getTitoloAlbumInStudio())
                .dataRilascio(albumInStudio.getDataRilascio())
                .brani(albumInStudio.getBrani())
                .generi(albumInStudio.getGeneri())
                .idArtistaMusicale(albumInStudio.getArtistaMusicale().getIdArtista())
                .build();
        return albumInStudioDTO;
    }

    @Override
    public List<AlbumInStudioDTO> mapTODTOList(List<AlbumInStudio> albumInStudioList) {
        return albumInStudioList.stream()
                .map((AlbumInStudio albumInStudio) -> this.mapTODTO(albumInStudio))
                .collect(Collectors.toList());
    }

    @Override
    public AlbumInStudio mapToEntity(AlbumInStudioDTO albumInStudioDTO) {
        AlbumInStudio albumInStudio = AlbumInStudio.builder()
                .titoloAlbumInStudio(albumInStudioDTO.getTitoloAlbumInStudio())
                .dataRilascio(albumInStudioDTO.getDataRilascio())
                .brani(albumInStudioDTO.getBrani())
                .generi(albumInStudioDTO.getGeneri())
                .artistaMusicale(artistaMusicaleService.getArtistaMusicaleById(albumInStudioDTO.getIdArtistaMusicale()))
                .build();
        return null;
    }

    @Override
    public List<AlbumInStudio> mapTOEntityList(List<AlbumInStudioDTO> albumInStudioDTOList) {
        return albumInStudioDTOList.stream()
                .map((AlbumInStudioDTO albumInStudioDTO) -> this.mapToEntity(albumInStudioDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumInStudioDTO> getDTOs() {
        List<AlbumInStudio> albumInStudioList = new ArrayList<>(albumInStudioRepository.findAll());
        return mapTODTOList(albumInStudioList);
    }

    @Override
    public AlbumInStudioDTO getDTO(Integer id) {
        Optional<AlbumInStudio> albumInStudio = albumInStudioRepository.findById(id);
        if (albumInStudio.isPresent()) {
            return mapTODTO(albumInStudio.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(AlbumInStudioDTO albumInStudioDTO) {
        AlbumInStudio albumInStudio = mapToEntity(albumInStudioDTO);
        albumInStudioRepository.save(albumInStudio);
    }

    @Override
    public void update(AlbumInStudioDTO albumInStudioDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleService.getArtistaMusicaleById(albumInStudioDTO.getIdArtistaMusicale());
        AlbumInStudio albumInStudioRicercato = albumInStudioRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album in studio ricercato non è presente"));
        albumInStudioRicercato.setTitoloAlbumInStudio(albumInStudioDTO.getTitoloAlbumInStudio() == null ? albumInStudioRicercato.getTitoloAlbumInStudio() : albumInStudioDTO.getTitoloAlbumInStudio());
        albumInStudioRicercato.setDataRilascio(albumInStudioDTO.getDataRilascio() == null ? albumInStudioRicercato.getDataRilascio() : albumInStudioDTO.getDataRilascio());
        albumInStudioRicercato.setGeneri(albumInStudioDTO.getGeneri() == null ? albumInStudioRicercato.getGeneri() : albumInStudioDTO.getGeneri());
        albumInStudioRicercato.setBrani(albumInStudioDTO.getBrani() == null ? albumInStudioRicercato.getBrani() : albumInStudioDTO.getBrani());
        albumInStudioRicercato.setArtistaMusicale(artistaMusicale == null ? albumInStudioRicercato.getArtistaMusicale() : artistaMusicale);
        albumInStudioRepository.save(albumInStudioRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (albumInStudioRepository.existsById(id)) {
            albumInStudioRepository.deleteById(id);
        }
    }
}
