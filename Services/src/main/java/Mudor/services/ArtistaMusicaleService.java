package Mudor.services;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistaMusicaleService {

    List<ArtistaMusicaleDTO> getArtistiMusicali();

    ArtistaMusicaleDTO getArtistaMusicale(Integer id);

    void addArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO);

    void updateArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id);

    void deleteArtistaMusicale(Integer id);
}
