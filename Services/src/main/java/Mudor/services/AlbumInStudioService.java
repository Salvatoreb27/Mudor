package Mudor.services;

import Mudor.DTO.AlbumInStudioDTO;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AlbumInStudioService {

    List<AlbumInStudioDTO> getAlbumsInStudio();

    AlbumInStudioDTO getAlbumInStudio(Integer id);

    void addAlbumInStudio(AlbumInStudioDTO albumInStudioDTO);

    void updateAlbumInStudio(AlbumInStudioDTO albumInStudioDTO, Integer id);

    void deleteAlbumInStudio(Integer id);
}
