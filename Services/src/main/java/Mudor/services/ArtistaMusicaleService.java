package Mudor.services;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistaMusicaleService {
    public ArtistaMusicaleDTO mapTOArtistaMusicaleDTO (ArtistaMusicale artistaMusicale);
    List<ArtistaMusicaleDTO> mapTOArtistaMusicaleDTOList(List<ArtistaMusicale> artistaMusicaleList);
    ArtistaMusicale mapToArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO);
    List<ArtistaMusicale> mapTOArtistaMusicaleList(List<ArtistaMusicaleDTO> artistaMusicaleDTOList);
    List<ArtistaMusicaleDTO> getArtistiMusicali();
    ArtistaMusicale getArtistaMusicale(Integer id);
    ArtistaMusicaleDTO getArtistaMusicaleDTO(Integer id);
    void addArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO);

    void updateArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id);

    void deleteArtistaMusicale(Integer id);
}
