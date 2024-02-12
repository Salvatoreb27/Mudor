package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.entity.AlbumInStudio;
import Mudor.repository.AlbumInStudioRepository;
import Mudor.services.AlbumInStudioService;
import Mudor.services.ArtistaMusicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public AlbumInStudioDTO getAlbumInStudio(Integer id) {
        return null;
    }

    @Override
    public void addAlbumInStudio(AlbumInStudioDTO albumInStudioDTO) {

    }

    @Override
    public void updateAlbumInStudio(AlbumInStudioDTO albumInStudioDTO, Integer id) {

    }

    @Override
    public void deleteAlbumInStudio(Integer id) {

    }
}
