package Mudor.services;


import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.AlbumLiveDTO;
import Mudor.entity.AlbumInStudio;
import Mudor.entity.AlbumLive;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumLiveService {

    AlbumLiveDTO mapTOAlbumLiveDTO(AlbumLive albumLive);
    List<AlbumLiveDTO> mapTOAlbumLiveDTOList(List<AlbumLive> albumLiveList);
    AlbumLive mapToAlbumLive(AlbumLiveDTO albumLiveDTO);
    List<AlbumLive> mapTOAlbumLiveList(List<AlbumLiveDTO> albumLiveDTOList);
    List<AlbumLiveDTO> getAlbumsLive();
    AlbumLiveDTO getAlbumLive(Integer id);
    void addAlbumLive(AlbumLiveDTO albumLiveDTO);
    void updateAlbumLive(AlbumLiveDTO albumLiveDTO, Integer id);
    void deleteAlbumLive(Integer id);
}
