package Mudor.servicesimpl;

import Mudor.DTO.CrossArtistaSingoloDTO;
import Mudor.entity.*;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.repository.CrossArtistaSingoloRepository;
import Mudor.repository.SingoloRepository;
import Mudor.services.CrossArtistaSingoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CrossArtistaSingoloServiceImpl implements CrossArtistaSingoloService {

    @Autowired
    private CrossArtistaSingoloRepository crossArtistaSingoloRepository;
    @Autowired
    private ArtistaMusicaleRepository artistaMusicaleRepository;
    @Autowired
    private SingoloRepository singoloRepository;

    @Override
    public List<CrossArtistaSingolo> getAssociationListByListOfSingoli(List<String> singoli) {
        List<CrossArtistaSingolo> associazioniArtistaSingolo = crossArtistaSingoloRepository.findAll();
        for (CrossArtistaSingolo crossArtistaSingolo : associazioniArtistaSingolo) {
            for (String singolo : singoli)
                if (crossArtistaSingolo.getSingolo().getTitoloSingolo().equalsIgnoreCase(singolo)) {
                    associazioniArtistaSingolo.add(crossArtistaSingolo);
                }
        }
        return associazioniArtistaSingolo;
    }

    @Override
    public List<CrossArtistaSingolo> getAssociationListByListOfArtisti(List<String> artisti) {
        List<CrossArtistaSingolo> associazioniArtistaSingolo = crossArtistaSingoloRepository.findAll();
        for (CrossArtistaSingolo crossArtistaSingolo : associazioniArtistaSingolo) {
            for (String artista : artisti)
                if (crossArtistaSingolo.getArtistaMusicale().getNome().equalsIgnoreCase(artista)) {
                    associazioniArtistaSingolo.add(crossArtistaSingolo);
                }
        }
        return associazioniArtistaSingolo;
    }

    @Override
    public List<String> getSingoliByArtistaMusicale(Integer id) {
        List<String> singoli = new ArrayList<>();
        List<CrossArtistaSingolo> associazioniArtistaSingolo = crossArtistaSingoloRepository.findAll();
        for (CrossArtistaSingolo crossArtistaSingolo : associazioniArtistaSingolo) {
            if (crossArtistaSingolo.getArtistaMusicale().getIdArtista() == id) {
                singoli.add(crossArtistaSingolo.getSingolo().getTitoloSingolo());
            }
        }
        return singoli;
    }

    @Override
    public List<String> getArtistiBySingolo(Integer id) {
        List<String> artisti = new ArrayList<>();
        List<CrossArtistaSingolo> associazioniArtistaSingolo = crossArtistaSingoloRepository.findAll();
        for (CrossArtistaSingolo crossArtistaSingolo : associazioniArtistaSingolo) {
            if (crossArtistaSingolo.getSingolo().getIdSingolo() == id) {
                artisti.add(crossArtistaSingolo.getArtistaMusicale().getNome());
            }
        }
        return artisti;
    }

    @Override
    public CrossArtistaSingoloDTO mapTODTO(CrossArtistaSingolo crossArtistaSingolo) {
        CrossArtistaSingoloDTO crossArtistaSingoloDTO = CrossArtistaSingoloDTO.builder()
                .nomeArtista(crossArtistaSingolo.getArtistaMusicale().getNome())
                .titoloSingolo(crossArtistaSingolo.getSingolo().getTitoloSingolo())
                .build();
        return crossArtistaSingoloDTO;
    }

    @Override
    public List<CrossArtistaSingoloDTO> mapTODTOList(List<CrossArtistaSingolo> crossArtistaSingoloList) {
        return crossArtistaSingoloList.stream()
                .map((CrossArtistaSingolo crossArtistaSingolo) -> this.mapTODTO(crossArtistaSingolo))
                .collect(Collectors.toList());
    }

    @Override
    public CrossArtistaSingolo mapToEntity(CrossArtistaSingoloDTO crossArtistaSingoloDTO) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findByNome(crossArtistaSingoloDTO.getNomeArtista()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        Singolo singolo = singoloRepository.findByTitoloSingolo(crossArtistaSingoloDTO.getTitoloSingolo()).orElseThrow(() -> new RuntimeException("Singolo non trovato"));
        CrossArtistaSingolo crossArtistaSingolo = CrossArtistaSingolo.builder()
                .artistaMusicale(artistaMusicale)
                .singolo(singolo)
                .build();
        return null;
    }

    @Override
    public List<CrossArtistaSingolo> mapTOEntityList(List<CrossArtistaSingoloDTO> crossArtistaSingoloDTOList) {
        return crossArtistaSingoloDTOList.stream()
                .map((CrossArtistaSingoloDTO crossArtistaSingoloDTO) -> this.mapToEntity(crossArtistaSingoloDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<CrossArtistaSingoloDTO> getDTOs() {
        List<CrossArtistaSingolo> crossArtistaSingoloList = new ArrayList<>(crossArtistaSingoloRepository.findAll());
        return mapTODTOList(crossArtistaSingoloList);
    }

    @Override
    public CrossArtistaSingoloDTO getDTO(Integer id) {
        Optional<CrossArtistaSingolo> crossArtistaSingolo = crossArtistaSingoloRepository.findById(id);
        if (crossArtistaSingolo.isPresent()) {
            return mapTODTO(crossArtistaSingolo.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(CrossArtistaSingoloDTO crossArtistaSingoloDTO) {
        CrossArtistaSingolo crossArtistaSingolo = mapToEntity(crossArtistaSingoloDTO);
        crossArtistaSingoloRepository.save(crossArtistaSingolo);
    }

    @Override
    public void update(CrossArtistaSingoloDTO crossArtistaSingoloDTO, Integer id) {
        Singolo singolo = singoloRepository.findByTitoloSingolo(crossArtistaSingoloDTO.getTitoloSingolo()).orElseThrow(() -> new RuntimeException("Singolo non trovato"));
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findByNome(crossArtistaSingoloDTO.getNomeArtista()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        CrossArtistaSingolo associazioneCrossArtistaSingoloRicercata = crossArtistaSingoloRepository.findById(id).orElseThrow(() -> new RuntimeException("L'associazione ricercata non è presente"));
        associazioneCrossArtistaSingoloRicercata.setSingolo(singolo == null ? associazioneCrossArtistaSingoloRicercata.getSingolo() : singolo);
        associazioneCrossArtistaSingoloRicercata.setArtistaMusicale(artistaMusicale == null ? associazioneCrossArtistaSingoloRicercata.getArtistaMusicale() : artistaMusicale);
        crossArtistaSingoloRepository.save(associazioneCrossArtistaSingoloRicercata);
    }

    @Override
    public void delete(Integer id) {
        if (crossArtistaSingoloRepository.existsById(id)) {
            crossArtistaSingoloRepository.deleteById(id);
        }
    }
}
