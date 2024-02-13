package Mudor.services;

import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.ArtistaMusicale;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ArtistaMusicaleService {
    public ArtistaMusicaleDTO mapTOArtistaMusicaleDTO (ArtistaMusicale artistaMusicale);
    List<ArtistaMusicaleDTO> mapTOArtistaMusicaleDTOList(List<ArtistaMusicale> artistaMusicaleList);
    ArtistaMusicale mapToArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO);
    List<ArtistaMusicale> mapTOArtistaMusicaleList(List<ArtistaMusicaleDTO> artistaMusicaleDTOList);
    List<ArtistaMusicaleDTO> getArtistiMusicali();
    ArtistaMusicale getArtistaMusicaleById(Integer id);
    ArtistaMusicale getArtistaMusicaleByNome(String nome);
    ArtistaMusicaleDTO getArtistaMusicaleDTO(Integer id);
    void addArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO);

    void updateArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id);

    void deleteArtistaMusicale(Integer id);
}
