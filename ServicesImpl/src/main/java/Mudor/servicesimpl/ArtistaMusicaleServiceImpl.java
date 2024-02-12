package Mudor.servicesimpl;

import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.ArtistaMusicale;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.services.AlbumInStudioService;
import Mudor.services.AlbumLiveService;
import Mudor.services.ArtistaMusicaleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ArtistaMusicaleServiceImpl implements ArtistaMusicaleService {
@Autowired
private ArtistaMusicaleRepository artistaMusicaleRepository;
@Autowired
private AlbumInStudioService albumInStudioService;

@Autowired
private AlbumLiveService albumLiveService;

    public ArtistaMusicaleDTO mapTOArtistaMusicaleDTO (ArtistaMusicale artistaMusicale) {
        ArtistaMusicaleDTO artistaMusicaleDTO = ArtistaMusicaleDTO.builder()
                .nome(artistaMusicale.getNome())
                .descrizione(artistaMusicale.getDescrizione())
                .paeseDOrigine(artistaMusicale.getPaeseDOrigine())
                .generi(artistaMusicale.getGeneri())
                .albumsInStudioDTOS(albumInStudioService.mapTOAlbumInStudioDTOList(artistaMusicale.getAlbumsInStudio()))
                .albumsLiveDTOS(albumLiveService.mapTOAlbumLiveDTOList(artistaMusicale.getAlbumsLive()))
                .singoli(artistaMusicale.getCrossArtistaSingolos().stream().forEach())
                .raccoltaDTOS(artistaMusicale.getRaccolte())
                .build();
    }
    @Override
    public List<ArtistaMusicaleDTO> getArtistiMusicali() {
        return null;
    }

    @Override
    public ArtistaMusicaleDTO getArtistaMusicaleDTO(Integer id) {
        return null;
    }
    @Override
    public ArtistaMusicale getArtistaMusicale(Integer id) {
        Optional<ArtistaMusicale> artistaMusicale = artistaMusicaleRepository.findById(id);
        if(artistaMusicale.isPresent()) {
            return artistaMusicale.get();
        }else {
            return null;
        }
    }

    @Override
    public void addArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO) {

    }

    @Override
    public void updateArtistaMusicale(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id) {

    }

    @Override
    public void deleteArtistaMusicale(Integer id) {

    }
}
