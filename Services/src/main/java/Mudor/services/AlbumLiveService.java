package Mudor.services;


import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.AlbumLiveDTO;
import Mudor.entity.AlbumLive;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AlbumLiveService extends MapAndCRUDService<AlbumLiveDTO, AlbumLive, Integer> {
    List<AlbumLiveDTO> getAlbumLiveBy(Integer idAlbumLive, String titoloAlbumLive, Date dataRilascio, List<String> brani, List<String> generi, String nome);
}
