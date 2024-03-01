package Mudor.controllers;

import Mudor.DTO.ReleaseDTO;
import Mudor.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Questa classe gestisce le richieste relative alle release attraverso API REST.
 * Fornisce endpoint per ottenere, aggiungere, aggiornare ed eliminare release.
 */
@RestController
@RequestMapping("/api/v1/release")
public class ReleaseController extends AbstractController <ReleaseDTO, Integer>{

    @Autowired
    private ReleaseService releaseService;

    /**
     * Ottiene tutte le release.
     *
     * @return una ResponseEntity contenente la lista di tutte le release
     */
    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ReleaseDTO>> getAll() {
        List<ReleaseDTO> releaseDTOList = releaseService.getDTOs();
        return new ResponseEntity<>(releaseDTOList, HttpStatus.OK);
    }

    /**
     * Ottiene le release in base ai criteri specificati.
     *
     * @param idRelease              l'ID della release
     * @param idReleaseGroupMusicBrainz  l'ID del gruppo di release su MusicBrainz
     * @param idReleaseMusicBrainz   l'ID della release su MusicBrainz
     * @param title                  il titolo della release
     * @param kind                   il tipo di release
     * @param covertArt              l'URL della copertina della release
     * @param dateOfRelease          la data di uscita della release
     * @param tracks                 la lista delle tracce della release
     * @param genres                 la lista dei generi della release
     * @param name                   il nome dell'artista associato alla release
     * @return una ResponseEntity contenente la lista delle release che soddisfano i criteri specificati
     */
    @GetMapping("/getBy")
    public ResponseEntity<List<ReleaseDTO>> getReleaseBy (
            @RequestParam(name = "idRelease", required = false) Integer idRelease,
            @RequestParam(name = "idReleaseGroupMusicBrainz", required = false) String idReleaseGroupMusicBrainz,
            @RequestParam(name = "idReleaseMusicBrainz", required = false) String idReleaseMusicBrainz,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "kind", required = false) String kind,
            @RequestParam(name = "covertArt", required = false) String covertArt,
            @RequestParam(name = "dateOfRelease", required = false) String dateOfRelease,
            @RequestParam(name = "tracks", required = false) List<String> tracks,
            @RequestParam(name = "genres", required = false) List<String> genres,
            @RequestParam(name = "name", required = false) String name) {
        List<ReleaseDTO> releaseDTOList = releaseService
                .getReleaseBy(
                        idRelease,
                        idReleaseGroupMusicBrainz,
                        idReleaseMusicBrainz,
                        title,
                        kind,
                        covertArt,
                        dateOfRelease,
                        tracks,
                        genres,
                        name);
        return new ResponseEntity<>(releaseDTOList, HttpStatus.OK);
    }


    /**
     * Aggiunge una nuova release.
     *
     * @param releaseDTO la DTO della release da aggiungere
     * @return una ResponseEntity indicante lo stato dell'operazione
     */
    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(@RequestParam ReleaseDTO releaseDTO) {
        releaseService.add(releaseDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Aggiorna una release esistente.
     *
     * @param releaseDTO la DTO della release aggiornata
     * @param id         l'ID della release da aggiornare
     * @return una ResponseEntity indicante lo stato dell'operazione
     */
    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(@RequestParam ReleaseDTO releaseDTO, @RequestParam Integer id) {
        releaseService.update(releaseDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Elimina una release esistente.
     *
     * @param id l'ID della release da eliminare
     * @return una ResponseEntity indicante lo stato dell'operazione
     */

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        releaseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


