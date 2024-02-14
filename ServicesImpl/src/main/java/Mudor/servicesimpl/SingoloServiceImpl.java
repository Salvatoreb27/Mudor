package Mudor.servicesimpl;


import Mudor.DTO.RaccoltaDTO;
import Mudor.DTO.SingoloDTO;
import Mudor.entity.*;
import Mudor.entity.spec.RaccoltaSpecifications;
import Mudor.entity.spec.SingoloSpecifications;
import Mudor.repository.SingoloRepository;
import Mudor.services.CrossArtistaSingoloService;
import Mudor.services.SingoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

    private List<Specification<Singolo>> createSpecifications(Integer idSingolo, String titoloSingolo, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<Singolo>> specifications = new ArrayList<>();
        if (idSingolo != null) {
            specifications.add(SingoloSpecifications.likeIdSingolo(idSingolo));
        }
        if (titoloSingolo != null) {
            specifications.add(SingoloSpecifications.likeTitoloSingolo(titoloSingolo));
        }
        if (dataRilascio != null) {
            specifications.add(SingoloSpecifications.likeDataRilascio(dataRilascio));
        }
        if (brani != null) {
            specifications.add(SingoloSpecifications.likeBrani(brani));
        }
        if (generi != null) {
            specifications.add(SingoloSpecifications.likeGeneri(generi));
        }
        if (nome != null) {
            specifications.add(SingoloSpecifications.likeArtistaSingolo(nome));
        }
        return specifications;
    }
    private Specification<Singolo> combineSpecifications(List<Specification<Singolo>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    @Override
    public List<SingoloDTO> getSingoloBy(Integer idSingolo, String titoloSingolo, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<Singolo>> specifications = createSpecifications(idSingolo, titoloSingolo, dataRilascio, brani, generi, nome);
        Specification<Singolo> combinedSpecification = combineSpecifications(specifications);

        List<Singolo> listaSingoli= singoloRepository.findAll((Sort) combinedSpecification);

        return listaSingoli.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }

}
