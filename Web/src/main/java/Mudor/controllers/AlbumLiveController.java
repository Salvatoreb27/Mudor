package Mudor.controllers;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.AlbumLiveDTO;
import Mudor.services.AlbumLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/albumLive")
public class AlbumLiveController extends AbstractController<AlbumLiveDTO, Integer> {

    @Autowired
    private AlbumLiveService albumLiveService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<AlbumLiveDTO>> getAll() {
        List<AlbumLiveDTO> albumLiveDTOList = albumLiveService.getDTOs();
        return new ResponseEntity<>(albumLiveDTOList, HttpStatus.OK);
    }

    @GetMapping("/getBy")
    public ResponseEntity<List<AlbumLiveDTO>> getAlbumLiveBy (
            @RequestParam(name = "idAlbumLive", required = false) Integer idAlbumLive,
            @RequestParam(name = "titoloAlbumLive", required = false) String titoloAlbumLive,
            @RequestParam(name = "dataRilascio", required = false) Date dataRilascio,
            @RequestParam(name = "brani", required = false) List<String> brani,
            @RequestParam(name = "generi", required = false) List<String> generi,
            @RequestParam(name = "nome", required = false) String nomeArtista) {
        List<AlbumLiveDTO> albumLiveDTOList = albumLiveService
                .getAlbumLiveBy(idAlbumLive,
                        titoloAlbumLive,
                        dataRilascio,
                        brani,
                        generi,
                        nomeArtista);
        return new ResponseEntity<>(albumLiveDTOList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(AlbumLiveDTO albumLiveDTO) {
        albumLiveService.add(albumLiveDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(AlbumLiveDTO albumLiveDTO, Integer id) {
        albumLiveService.update(albumLiveDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        albumLiveService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
