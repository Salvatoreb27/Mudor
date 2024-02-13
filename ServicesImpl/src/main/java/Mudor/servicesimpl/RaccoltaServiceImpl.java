package Mudor.servicesimpl;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.entity.Raccolta;
import Mudor.repository.RaccoltaRepository;
import Mudor.services.ArtistaMusicaleService;
import Mudor.services.RaccoltaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RaccoltaServiceImpl implements RaccoltaService {

    @Autowired
    private RaccoltaRepository raccoltaRepository;

    @Autowired
    private ArtistaMusicaleService artistaMusicaleService;

    @Override
    public RaccoltaDTO mapTODTO(Raccolta raccolta) {
        RaccoltaDTO raccoltaDTO = RaccoltaDTO.builder()
                .titoloRaccolta(raccolta.getTitoloRaccolta())
                .dataRilascio(raccolta.getDataRilascio())
                .brani(raccolta.getBrani())
                .generi(raccolta.getGeneri())
                .idArtistaMusicale(raccolta.getArtistaMusicale().getIdArtista())
                .build();
        return raccoltaDTO;
    }

    @Override
    public List<RaccoltaDTO> mapTODTOList(List<Raccolta> raccoltaList) {
        return raccoltaList.stream()
                .map((Raccolta raccolta) -> this.mapTODTO(raccolta))
                .collect(Collectors.toList());
    }

    @Override
    public Raccolta mapToEntity(RaccoltaDTO raccoltaDTO) {
        Raccolta raccolta = Raccolta.builder()
                .titoloRaccolta(raccoltaDTO.getTitoloRaccolta())
                .dataRilascio(raccoltaDTO.getDataRilascio())
                .brani(raccoltaDTO.getBrani())
                .generi(raccoltaDTO.getGeneri())
                .artistaMusicale(artistaMusicaleService.getArtistaMusicaleById(raccoltaDTO.getIdArtistaMusicale()))
                .build();
        return null;
    }

    @Override
    public List<Raccolta> mapTOEntityList(List<RaccoltaDTO> raccoltaDTOList) {
        return raccoltaDTOList.stream()
                .map((RaccoltaDTO raccoltaDTO) -> this.mapToEntity(raccoltaDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<RaccoltaDTO> getDTOs() {
        List<Raccolta> raccoltaList = new ArrayList<>(raccoltaRepository.findAll());
        return mapTODTOList(raccoltaList);
    }

    @Override
    public RaccoltaDTO getDTO(Integer id) {
        Optional<Raccolta> raccolta = raccoltaRepository.findById(id);
        if (raccolta.isPresent()) {
            return mapTODTO(raccolta.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(RaccoltaDTO raccoltaDTO) {
        Raccolta raccolta = mapToEntity(raccoltaDTO);
        raccoltaRepository.save(raccolta);
    }

    @Override
    public void update(RaccoltaDTO raccoltaDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleService.getArtistaMusicaleById(raccoltaDTO.getIdArtistaMusicale());
        Raccolta raccoltaRicercata = raccoltaRepository.findById(id).orElseThrow(() -> new RuntimeException("La raccolta ricercata non è presente"));
        raccoltaRicercata.setTitoloRaccolta(raccoltaDTO.getTitoloRaccolta());
        raccoltaRicercata.setDataRilascio(raccoltaDTO.getDataRilascio());
        raccoltaRicercata.setGeneri(raccoltaDTO.getGeneri());
        raccoltaRicercata.setBrani(raccoltaDTO.getBrani());
        raccoltaRicercata.setArtistaMusicale(artistaMusicale);
        raccoltaRepository.save(raccoltaRicercata);
    }

    @Override
    public void delete(Integer id) {
        if (raccoltaRepository.existsById(id)) {
            raccoltaRepository.deleteById(id);
        }
    }
}