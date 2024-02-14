package Mudor.controllers;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.services.ArtistaMusicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/artistaMusicale")
public class ArtistaMusicaleController extends AbstractController<ArtistaMusicaleDTO, Integer> {

    @Autowired
    private ArtistaMusicaleService artistaMusicaleService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ArtistaMusicaleDTO>> getAll() {
        List<ArtistaMusicaleDTO> artistaMusicaleDTOList = artistaMusicaleService.getDTOs();
        return new ResponseEntity<>(artistaMusicaleDTOList, HttpStatus.OK);
    }

    @GetMapping("/getBy")
    public ResponseEntity<List<ArtistaMusicaleDTO>> getArtistaMusicaleBy (
            @RequestParam(name = "idArtistaMusicale", required = false) Integer idArtistaMusicale,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descrizione", required = false) String descrizione,
            @RequestParam(name = "paeseDOrigine", required = false) String paeseDOrigine,
            @RequestParam(name = "generi", required = false) List<String> generi,
            @RequestParam(name = "titoloAlbumInStudio", required = false) String titoloAlbumInStudio,
            @RequestParam(name = "titoloAlbumLive", required = false) String titoloAlbumLive,
            @RequestParam(name = "titoloSingolo", required = false) String titoloSingolo,
            @RequestParam(name = "titoloRaccolta", required = false) String titoloRaccolta) {
        List<ArtistaMusicaleDTO> artistaMusicaleDTOList = artistaMusicaleService
                .getArtistaMusicaleBy(idArtistaMusicale,
                        nome,
                        descrizione,
                        paeseDOrigine,
                        generi,
                        titoloAlbumInStudio,
                        titoloAlbumLive,
                        titoloSingolo,
                        titoloRaccolta);
        return new ResponseEntity<>(artistaMusicaleDTOList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(ArtistaMusicaleDTO artistaMusicaleDTO) {
        artistaMusicaleService.add(artistaMusicaleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(ArtistaMusicaleDTO artistaMusicaleDTO, Integer id) {
        artistaMusicaleService.update(artistaMusicaleDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        artistaMusicaleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
