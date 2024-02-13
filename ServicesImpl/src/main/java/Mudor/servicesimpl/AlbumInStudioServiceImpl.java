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
    public AlbumInStudioDTO mapTOAlbumInStudioDTO(AlbumInStudio albumInStudio) {
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
    public List<AlbumInStudioDTO> mapTOAlbumInStudioDTOList(List<AlbumInStudio> albumsInStudioList) {
        return albumsInStudioList.stream()
                .map(this::mapTOAlbumInStudioDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumInStudio mapToAlbumInStudio(AlbumInStudioDTO albumInStudioDTO) {
        AlbumInStudio albumInStudio = AlbumInStudio.builder()
                .titoloAlbumInStudio(albumInStudioDTO.getTitoloAlbumInStudio())
                .dataRilascio(albumInStudioDTO.getDataRilascio())
                .brani(albumInStudioDTO.getBrani())
                .generi(albumInStudioDTO.getGeneri())
                .artistaMusicale(artistaMusicaleService.getArtistaMusicale(albumInStudioDTO.getIdArtistaMusicale()))
                .build();
        return null;
    }

    @Override
    public List<AlbumInStudio> mapTOAlbumInStudioList(List<AlbumInStudioDTO> albumsInStudioDTOList) {
        return albumsInStudioDTOList.stream()
                .map(this::mapToAlbumInStudio)
                .collect(Collectors.toList());
    }


    @Override
    public List<AlbumInStudioDTO> getAlbumsInStudio() {
        List<AlbumInStudio> albumInStudioList = new ArrayList<>();
        return mapTOAlbumInStudioDTOList(albumInStudioList);
    }

    @Override
    public AlbumInStudioDTO getAlbumInStudio(Integer id) {
        Optional<AlbumInStudio> albumInStudio = albumInStudioRepository.findById(id);
        if (albumInStudio.isPresent()) {
            return mapTOAlbumInStudioDTO(albumInStudio.get());
        } else {
            return null;
        }
    }

    @Override
    public void addAlbumInStudio(AlbumInStudioDTO albumInStudioDTO) {
        AlbumInStudio albumInStudio = mapToAlbumInStudio(albumInStudioDTO);
        albumInStudioRepository.save(albumInStudio);
    }

    @Override
    public void updateAlbumInStudio(AlbumInStudioDTO albumInStudioDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleService.getArtistaMusicale(albumInStudioDTO.getIdArtistaMusicale());
        AlbumInStudio albumInStudioRicercato = albumInStudioRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album in studio ricercato non è presente"));
        albumInStudioRicercato.setTitoloAlbumInStudio(albumInStudioDTO.getTitoloAlbumInStudio());
        albumInStudioRicercato.setDataRilascio(albumInStudioDTO.getDataRilascio());
        albumInStudioRicercato.setGeneri(albumInStudioDTO.getGeneri());
        albumInStudioRicercato.setBrani(albumInStudioDTO.getBrani());
        albumInStudioRicercato.setArtistaMusicale(artistaMusicale);
    }

    @Override
    public void deleteAlbumInStudio(Integer id) {
        if (albumInStudioRepository.existsById(id)) {
            albumInStudioRepository.deleteById(id);
        }
    }

}
