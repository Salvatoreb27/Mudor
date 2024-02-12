package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.services.AlbumInStudioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumInStudioServiceImpl implements AlbumInStudioService {
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
