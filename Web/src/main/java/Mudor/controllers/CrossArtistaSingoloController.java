package Mudor.controllers;

import Mudor.DTO.CrossArtistaSingoloDTO;
import Mudor.services.CrossArtistaSingoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/CrossArtistaSingolo")
public class CrossArtistaSingoloController extends AbstractController <CrossArtistaSingoloDTO, Integer> {

    @Autowired
    private CrossArtistaSingoloService crossArtistaSingoloService;
    @GetMapping("/all")
    @Override
    public ResponseEntity<List<CrossArtistaSingoloDTO>> getAll() {
        List<CrossArtistaSingoloDTO> crossArtistaSingoloDTOList = crossArtistaSingoloService.getDTOs();
        return new ResponseEntity<>(crossArtistaSingoloDTOList, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(CrossArtistaSingoloDTO crossArtistaSingoloDTO) {
        crossArtistaSingoloService.add(crossArtistaSingoloDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(CrossArtistaSingoloDTO crossArtistaSingoloDTO, Integer id) {
        crossArtistaSingoloService.update(crossArtistaSingoloDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        crossArtistaSingoloService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
