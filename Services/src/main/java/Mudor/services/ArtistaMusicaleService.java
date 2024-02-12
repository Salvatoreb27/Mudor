package Mudor.services;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.ArtistaMusicale;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistaMusicaleService {

    List<ArtistaMusicaleDTO> getArtistiMusicali();

    ArtistaMusicale getArtistaMusicale(Integer id);
    ArtistaMusicaleDTO getArtistaMusicaleDTO(Integer id);

    void addArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO);

    void updateArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id);

    void deleteArtistaMusicale(Integer id);
}
