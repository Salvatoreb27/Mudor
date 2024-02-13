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
    public RaccoltaDTO mapTORaccoltaDTO(Raccolta raccolta) {
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
    public List<RaccoltaDTO> mapTORaccoltaDTOList(List<Raccolta> raccoltaList) {
        return raccoltaList.stream()
                .map(this::mapTORaccoltaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Raccolta mapToRaccolta(RaccoltaDTO raccoltaDTO) {
        Raccolta raccolta = Raccolta.builder()
                .titoloRaccolta(raccoltaDTO.getTitoloRaccolta())
                .dataRilascio(raccoltaDTO.getDataRilascio())
                .brani(raccoltaDTO.getBrani())
                .generi(raccoltaDTO.getGeneri())
                .artistaMusicale(artistaMusicaleService.getArtistaMusicale(raccoltaDTO.getIdArtistaMusicale()))
                .build();
        return null;
    }

    @Override
    public List<Raccolta> mapTORaccoltaList(List<RaccoltaDTO> raccoltaDTOList) {
        return raccoltaDTOList.stream()
                .map(this::mapToRaccolta)
                .collect(Collectors.toList());
    }

    @Override
    public List<RaccoltaDTO> getRaccolte() {
        List<Raccolta> raccoltaList = new ArrayList<>();
        return mapTORaccoltaDTOList(raccoltaList);
    }

    @Override
    public RaccoltaDTO getRaccolta(Integer id) {
        Optional<Raccolta> raccolta = raccoltaRepository.findById(id);
        if (raccolta.isPresent()) {
            return mapTORaccoltaDTO(raccolta.get());
        } else {
            return null;
        }
    }

    @Override
    public void addRaccolta(RaccoltaDTO raccoltaDTO) {
        Raccolta raccolta = mapToRaccolta(raccoltaDTO);
        raccoltaRepository.save(raccolta);
    }


    @Override
    public void updateRaccolta(RaccoltaDTO raccoltaDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleService.getArtistaMusicale(raccoltaDTO.getIdArtistaMusicale());
        Raccolta raccoltaRicercata = raccoltaRepository.findById(id).orElseThrow(() -> new RuntimeException("La raccolta ricercata non è presente"));
        raccoltaRicercata.setTitoloRaccolta(raccoltaDTO.getTitoloRaccolta());
        raccoltaRicercata.setDataRilascio(raccoltaDTO.getDataRilascio());
        raccoltaRicercata.setGeneri(raccoltaDTO.getGeneri());
        raccoltaRicercata.setBrani(raccoltaDTO.getBrani());
        raccoltaRicercata.setArtistaMusicale(artistaMusicale);
    }

    @Override
    public void deleteRaccolta(Integer id) {
        if (raccoltaRepository.existsById(id)) {
            raccoltaRepository.deleteById(id);
        }
    }


}
