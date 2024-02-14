package Mudor.controllers;


import Mudor.DTO.RaccoltaDTO;
import Mudor.services.RaccoltaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/raccolta")
public class RaccoltaController extends AbstractController<RaccoltaDTO, Integer> {

    @Autowired
    private RaccoltaService raccoltaService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<RaccoltaDTO>> getAll() {
        List<RaccoltaDTO> raccoltaDTOList = raccoltaService.getDTOs();
        return new ResponseEntity<>(raccoltaDTOList, HttpStatus.OK);
    }
    @GetMapping("/getBy")
    public ResponseEntity<List<RaccoltaDTO>> getRaccoltaBy (
            @RequestParam(name = "idRaccolta", required = false) Integer idRaccolta,
            @RequestParam(name = "titoloRaccolta", required = false) String titoloRaccolta,
            @RequestParam(name = "dataRilascio", required = false) Date dataRilascio,
            @RequestParam(name = "brani", required = false) List<String> brani,
            @RequestParam(name = "generi", required = false) List<String> generi,
            @RequestParam(name = "nome", required = false) String nomeArtista) {
        List<RaccoltaDTO> raccoltaDTOList = raccoltaService
                .getRaccoltaBy(idRaccolta,
                        titoloRaccolta,
                        dataRilascio,
                        brani,
                        generi,
                        nomeArtista);
        return new ResponseEntity<>(raccoltaDTOList, HttpStatus.OK);
    }
    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(RaccoltaDTO raccoltaDTO) {
        raccoltaService.add(raccoltaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(RaccoltaDTO raccoltaDTO, Integer id) {
        raccoltaService.update(raccoltaDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        raccoltaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
