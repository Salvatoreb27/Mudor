package Mudor.controllers;

import Mudor.DTO.ReleaseDTO;
import Mudor.services.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/release")
public class ReleaseController extends AbstractController <ReleaseDTO, Integer>{

    @Autowired
    private ReleaseService releaseService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ReleaseDTO>> getAll() {
        List<ReleaseDTO> releaseDTOList = releaseService.getDTOs();
        return new ResponseEntity<>(releaseDTOList, HttpStatus.OK);
    }

    @GetMapping("/getBy")
    public ResponseEntity<List<ReleaseDTO>> getReleaseBy (
            @RequestParam(name = "idRelease", required = false) Integer idRelease,
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


    @PostMapping("/add")
    @Override
    public ResponseEntity<Void> add(@RequestParam ReleaseDTO releaseDTO) {
        releaseService.add(releaseDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Void> update(@RequestParam ReleaseDTO releaseDTO, @RequestParam Integer id) {
        releaseService.update(releaseDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        releaseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


