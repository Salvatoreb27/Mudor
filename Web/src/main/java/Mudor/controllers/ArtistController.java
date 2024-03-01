package Mudor.controllers;

import Mudor.DTO.ArtistDTO;
import Mudor.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController extends AbstractController<ArtistDTO, Integer> {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ArtistDTO>> getAll() {
        List<ArtistDTO> artistDTOList = artistService.getDTOs();
        return new ResponseEntity<>(artistDTOList, HttpStatus.OK);
    }

    @GetMapping("/getBy")
    public ResponseEntity<List<ArtistDTO>> getArtistBy (
            @RequestParam(name = "idArtist", required = false) Integer idArtist,
            @RequestParam(name = "idArtistaMusicBrainz", required = false) String idArtistaMusicBrainz,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "genres", required = false) List<String> genres,
            @RequestParam(name = "title", required = false) String title) {
        List<ArtistDTO> artistDTOList = artistService
                .getArtistBy(
                        idArtist,
                        idArtistaMusicBrainz,
                        name,
                        description,
                        country,
                        genres,
                        title);
        return new ResponseEntity<>(artistDTOList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(@RequestParam ArtistDTO artistDTO) {
        artistService.add(artistDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(@RequestParam ArtistDTO artistDTO, @RequestParam Integer id) {
        artistService.update(artistDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        artistService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
