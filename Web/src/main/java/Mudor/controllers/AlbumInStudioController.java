package Mudor.controllers;


import Mudor.DTO.AlbumInStudioDTO;
import Mudor.services.AlbumInStudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/albumInStudio")
public class AlbumInStudioController extends AbstractController<AlbumInStudioDTO, Integer>{

    @Autowired
    private AlbumInStudioService albumInStudioService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<AlbumInStudioDTO>> getAll() {
        List<AlbumInStudioDTO> albumInStudioDTOList = albumInStudioService.getDTOs();
        return new ResponseEntity<>(albumInStudioDTOList, HttpStatus.OK);
    }

    @GetMapping("/getBy")
    public ResponseEntity<List<AlbumInStudioDTO>> getAlbumInStudioBy (
            @RequestParam(name = "idAlbumInStudio", required = false) Integer idAlbumInStudio,
            @RequestParam(name = "titoloAlbumInStudio", required = false) String titoloAlbumInStudio,
            @RequestParam(name = "dataRilascio", required = false) Date dataRilascio,
            @RequestParam(name = "brani", required = false) List<String> brani,
            @RequestParam(name = "generi", required = false) List<String> generi,
            @RequestParam(name = "nome", required = false) String nomeArtista) {
        List<AlbumInStudioDTO> albumInStudioDTOList = albumInStudioService
                .getAlbumInStudioBy(idAlbumInStudio,
                        titoloAlbumInStudio,
                        dataRilascio,
                        brani,
                        generi,
                        nomeArtista);
        return new ResponseEntity<>(albumInStudioDTOList, HttpStatus.OK);
    }


    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(AlbumInStudioDTO albumInStudioDTO) {
        albumInStudioService.add(albumInStudioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(AlbumInStudioDTO albumInStudioDTO, Integer id) {
        albumInStudioService.update(albumInStudioDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        albumInStudioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
