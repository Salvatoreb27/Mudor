package Mudor.services;

import Mudor.DTO.ArtistDTO;
import Mudor.entity.Artist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArtistService extends MapAndCRUDService<ArtistDTO, Artist, Integer> {

    Artist getArtistById(Integer id);
    Artist getArtistByName(String name);
    Artist getArtistByIdMusicBrainz (String idMusicBrainz);
    List<ArtistDTO> getArtistBy(Integer idArtist, String idArtistMusicBrainz, String name, String description, String country, List<String> genres, String title);
}
