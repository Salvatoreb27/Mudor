package Mudor.controllers;

import Mudor.DTO.RaccoltaDTO;
import Mudor.DTO.SingoloDTO;
import Mudor.services.SingoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/singolo")
public class SingoloController extends AbstractController <SingoloDTO, Integer>{

    @Autowired
    private SingoloService singoloService;
    @GetMapping("/all")
    @Override
    public ResponseEntity<List<SingoloDTO>> getAll() {
        List<SingoloDTO> singoloDTOList = singoloService.getDTOs();
        return new ResponseEntity<>(singoloDTOList, HttpStatus.OK);
    }

    @GetMapping("/getBy")
    public ResponseEntity<List<SingoloDTO>> getSingoloBy (
            @RequestParam(name = "idSingolo", required = false) Integer idSingolo,
            @RequestParam(name = "titoloSingolo", required = false) String titoloSingolo,
            @RequestParam(name = "dataRilascio", required = false) Date dataRilascio,
            @RequestParam(name = "brani", required = false) List<String> brani,
            @RequestParam(name = "generi", required = false) List<String> generi,
            @RequestParam(name = "nome", required = false) String nomeArtista) {
        List<SingoloDTO> singoloDTOList = singoloService
                .getSingoloBy(idSingolo,
                        titoloSingolo,
                        dataRilascio,
                        brani,
                        generi,
                        nomeArtista);
        return new ResponseEntity<>(singoloDTOList, HttpStatus.OK);
    }
    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(SingoloDTO singoloDTO) {
        singoloService.add(singoloDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(SingoloDTO singoloDTO, Integer id) {
        singoloService.update(singoloDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        singoloService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
