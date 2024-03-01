package Mudor.controllers;

import Mudor.DTO.ArtistDTO;
import Mudor.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Questa classe gestisce le richieste relative agli artisti attraverso API REST.
 * Estende l'astrazione {@link AbstractController} per i DTO degli artisti.
 */
@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController extends AbstractController<ArtistDTO, Integer> {

    @Autowired
    private ArtistService artistService;

    /**
     * Recupera tutti gli artisti.
     *
     * @return una risposta HTTP contenente una lista di DTO degli artisti
     */
    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ArtistDTO>> getAll() {
        List<ArtistDTO> artistDTOList = artistService.getDTOs();
        return new ResponseEntity<>(artistDTOList, HttpStatus.OK);
    }

    /**
     * Recupera gli artisti in base ai parametri specificati.
     *
     * @param idArtist              l'ID dell'artista
     * @param idArtistaMusicBrainz  l'ID dell'artista su MusicBrainz
     * @param name                  il nome dell'artista
     * @param description           la descrizione dell'artista
     * @param country               il paese dell'artista
     * @param genres                i generi musicali dell'artista
     * @param title                 il titolo dell'artista
     * @return una risposta HTTP contenente una lista di DTO degli artisti che corrispondono ai criteri specificati
     */
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

    /**
     * Aggiunge un nuovo artista.
     *
     * @param artistDTO il DTO dell'artista da aggiungere
     * @return una risposta HTTP vuota
     */
    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(@RequestParam ArtistDTO artistDTO) {
        artistService.add(artistDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Aggiorna le informazioni di un artista esistente.
     *
     * @param artistDTO il DTO dell'artista aggiornato
     * @param id        l'ID dell'artista da aggiornare
     * @return una risposta HTTP vuota
     */
    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(@RequestParam ArtistDTO artistDTO, @RequestParam Integer id) {
        artistService.update(artistDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Elimina un artista.
     *
     * @param id l'ID dell'artista da eliminare
     * @return una risposta HTTP vuota
     */
    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        artistService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
