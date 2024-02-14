package Mudor.services;

import Mudor.DTO.AlbumInStudioDTO;

import Mudor.entity.AlbumInStudio;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public interface AlbumInStudioService extends MapAndCRUDService<AlbumInStudioDTO, AlbumInStudio, Integer> {
    List<AlbumInStudioDTO> getAlbumInStudioBy(Integer idAlbumInStudio, String titoloAlbumInStudio, Date dataRilascio, List<String> brani, List<String> generi,String nome);
}
