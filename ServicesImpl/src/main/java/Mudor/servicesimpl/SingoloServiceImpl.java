package Mudor.servicesimpl;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.DTO.SingoloDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.entity.CrossArtistaSingolo;
import Mudor.entity.Singolo;
import Mudor.repository.SingoloRepository;
import Mudor.services.CrossArtistaSingoloService;
import Mudor.services.SingoloService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingoloServiceImpl implements SingoloService {

    @Autowired
    private SingoloRepository singoloRepository;

    @Autowired
    private CrossArtistaSingoloService crossArtistaSingoloService;

    @Override
    public SingoloDTO mapTOSingoloDTO(Singolo singolo) {
        List <String> artisti = crossArtistaSingoloService.getArtistiBySingolo(singolo.getIdSingolo());
        SingoloDTO singoloDTO = SingoloDTO.builder()
                .titoloSingolo(singolo.getTitoloSingolo())
                .dataRilascio(singolo.getDataRilascio())
                .generi(singolo.getGeneri())
                .artisti(artisti)
                .build();
        return singoloDTO;
    }

    @Override
    public List<SingoloDTO> mapTOSingoloDTOList(List<Singolo> singoloList) {
        return singoloList.stream()
                .map(this::mapTOSingoloDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Singolo mapToSingolo(SingoloDTO singoloDTO) {
        List <CrossArtistaSingolo> crossArtistaSingoloList = crossArtistaSingoloService.getAssociationListByListOfArtisti(singoloDTO.getArtisti());
        Singolo singolo = Singolo.builder()
                .titoloSingolo(singoloDTO.getTitoloSingolo())
                .dataRilascio(singoloDTO.getDataRilascio())
                .generi(singoloDTO.getGeneri())
                .crossArtistaSingolos(crossArtistaSingoloList)
                .build();
        return null;
    }

    @Override
    public List<Singolo> mapTOSingoloList(List<SingoloDTO> singoloDTOList) {
        return singoloDTOList.stream()
                .map(this::mapToSingolo)
                .collect(Collectors.toList());
    }
    @Override
    public List<SingoloDTO> getSingoli() {
        List<Singolo> singoli = new ArrayList<>(singoloRepository.findAll());
        return mapTOSingoloDTOList(singoli);
    }


    @Override
    public SingoloDTO getSingolo(Integer id) {
        Optional<Singolo> singolo = singoloRepository.findById(id);
        if(singolo.isPresent()){
            return mapTOSingoloDTO(singolo.get());
        } else {
            return null;
        }
    }


    @Override
    public Singolo getSingoloByTitolo(String titolo) {
        Optional<Singolo> singolo = singoloRepository.findByTitoloSingolo(titolo);
        if(singolo.isPresent()) {
            return singolo.get();
        } else {
            return null;
        }
    }

    @Override
    public void addSingolo(SingoloDTO artistaMusicaleDTO) {

    }
    @Override
    public void updateSingolo(SingoloDTO singoloDTO, Integer id) {

    }

    @Override
    public void deleteSingolo(Integer id) {

    }
}
