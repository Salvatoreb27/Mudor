package Mudor.servicesimpl;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.Raccolta;
import Mudor.repository.RaccoltaRepository;
import Mudor.services.ArtistaMusicaleService;
import Mudor.services.RaccoltaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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
        return null;
    }

    @Override
    public RaccoltaDTO getRaccolta(Integer id) {
        return null;
    }

    @Override
    public void addRaccolta(RaccoltaDTO artistaMusicaleDTO) {

    }
    @Override
    public void updateRaccolta(RaccoltaDTO artistaMusicaleDTO, Integer id) {

    }

    @Override
    public void deleteRaccolta(Integer id) {

    }
}
