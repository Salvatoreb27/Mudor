package Mudor.servicesimpl;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.DTO.SingoloDTO;
import Mudor.entity.*;
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
    public SingoloDTO mapTODTO(Singolo singolo) {
        List<String> artisti = crossArtistaSingoloService.getArtistiBySingolo(singolo.getIdSingolo());
        SingoloDTO singoloDTO = SingoloDTO.builder()
                .titoloSingolo(singolo.getTitoloSingolo())
                .dataRilascio(singolo.getDataRilascio())
                .generi(singolo.getGeneri())
                .artisti(artisti)
                .build();
        return singoloDTO;
    }

    @Override
    public List<SingoloDTO> mapTODTOList(List<Singolo> singoloList) {
        return singoloList.stream()
                .map((Singolo singolo) -> this.mapTODTO(singolo))
                .collect(Collectors.toList());
    }

    @Override
    public Singolo mapToEntity(SingoloDTO singoloDTO) {
        List<CrossArtistaSingolo> crossArtistaSingoloList = crossArtistaSingoloService.getAssociationListByListOfArtisti(singoloDTO.getArtisti());
        Singolo singolo = Singolo.builder()
                .titoloSingolo(singoloDTO.getTitoloSingolo())
                .dataRilascio(singoloDTO.getDataRilascio())
                .generi(singoloDTO.getGeneri())
                .crossArtistaSingolos(crossArtistaSingoloList)
                .build();
        return null;
    }

    @Override
    public List<Singolo> mapTOEntityList(List<SingoloDTO> singoloDTOList) {
        return singoloDTOList.stream()
                .map((SingoloDTO singoloDTO) -> this.mapToEntity(singoloDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<SingoloDTO> getDTOs() {
        List<Singolo> singoli = new ArrayList<>(singoloRepository.findAll());
        return mapTODTOList(singoli);
    }

    @Override
    public SingoloDTO getDTO(Integer id) {
        Optional<Singolo> singolo = singoloRepository.findById(id);
        if (singolo.isPresent()) {
            return mapTODTO(singolo.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(SingoloDTO singoloDTO) {
        Singolo singolo = mapToEntity(singoloDTO);
        singoloRepository.save(singolo);
    }

    @Override
    public void update(SingoloDTO singoloDTO, Integer id) {
        List<CrossArtistaSingolo> associazioniArtistaSingolo = crossArtistaSingoloService.getAssociationListByListOfArtisti(singoloDTO.getArtisti());
        Singolo singoloRicercato = singoloRepository.findById(id).orElseThrow(() -> new RuntimeException("Il singolo ricercato non è presente"));
        singoloRicercato.setTitoloSingolo(singoloDTO.getTitoloSingolo() == null ? singoloRicercato.getTitoloSingolo() : singoloDTO.getTitoloSingolo());
        singoloRicercato.setDataRilascio(singoloDTO.getDataRilascio() == null ? singoloRicercato.getDataRilascio() : singoloDTO.getDataRilascio());
        singoloRicercato.setGeneri(singoloDTO.getGeneri() == null ? singoloRicercato.getGeneri() : singoloDTO.getGeneri());
        singoloRicercato.setCrossArtistaSingolos(associazioniArtistaSingolo == null ? singoloRicercato.getCrossArtistaSingolos() : associazioniArtistaSingolo);
        singoloRepository.save(singoloRicercato);
    }

    @Override
    public void delete(Integer id) {
    if(singoloRepository.existsById(id)) {
        singoloRepository.deleteById(id);
    }
    }

    @Override
    public Singolo getSingoloByTitolo(String titolo) {
        Optional<Singolo> singolo = singoloRepository.findByTitoloSingolo(titolo);
        if (singolo.isPresent()) {
            return singolo.get();
        } else {
            return null;
        }
    }

}
