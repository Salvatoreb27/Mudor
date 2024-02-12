package Mudor.services;


import Mudor.DTO.AlbumLiveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumLiveService {
    List<AlbumLiveDTO> getAlbumsLive();

    AlbumLiveDTO getAlbumLive(Integer id);

    void addAlbumLive(AlbumLiveDTO albumLiveDTO);

    void updateAlbumLive(AlbumLiveDTO albumLiveDTO, Integer id);

    void deleteAlbumLive(Integer id);
}
