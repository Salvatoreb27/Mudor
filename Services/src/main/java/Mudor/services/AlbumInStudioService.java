package Mudor.services;

import Mudor.DTO.AlbumInStudioDTO;

import Mudor.entity.AlbumInStudio;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AlbumInStudioService {

    AlbumInStudioDTO mapTOAlbumInStudioDTO(AlbumInStudio albumInStudio);
    List<AlbumInStudioDTO> mapTOAlbumInStudioDTOList(List<AlbumInStudio> albumsInStudioList);
    AlbumInStudio mapToAlbumInStudio(AlbumInStudioDTO albumInStudioDTO);
    List<AlbumInStudio> mapTOAlbumInStudioList(List<AlbumInStudioDTO> albumsInStudioDTOList);
    List<AlbumInStudioDTO> getAlbumsInStudio();
    AlbumInStudioDTO getAlbumInStudio(Integer id);
    void addAlbumInStudio(AlbumInStudioDTO albumInStudioDTO);
    void updateAlbumInStudio(AlbumInStudioDTO albumInStudioDTO, Integer id);
    void deleteAlbumInStudio(Integer id);
}
