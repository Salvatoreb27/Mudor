package Mudor.services;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.ArtistaMusicale;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ArtistaMusicaleService extends MapAndCRUDService<ArtistaMusicaleDTO, ArtistaMusicale, Integer> {

    ArtistaMusicale getArtistaMusicaleById(Integer id);
    ArtistaMusicale getArtistaMusicaleByNome(String nome);
    List<ArtistaMusicaleDTO> getArtistaMusicaleBy(Integer idAristaMusicale, String nome, String descrizione, String paeseDOrigine,List<String> generi, String titoloAlbumInStudio, String titoloAlbumLive, String titoloSingolo, String titoloRaccolta);
}
