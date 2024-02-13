package Mudor.services;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.Raccolta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RaccoltaService {
    RaccoltaDTO mapTORaccoltaDTO(Raccolta raccolta);
    List<RaccoltaDTO> mapTORaccoltaDTOList(List<Raccolta> raccoltaList);
    Raccolta mapToRaccolta(RaccoltaDTO raccoltaDTO);
    List<Raccolta> mapTORaccoltaList(List<RaccoltaDTO> raccoltaDTOList);
    List<RaccoltaDTO> getRaccolte();
    RaccoltaDTO getRaccolta(Integer id);
    void addRaccolta(RaccoltaDTO artistaMusicaleDTO);
    void updateRaccolta(RaccoltaDTO artistaMusicaleDTO, Integer id);
    void deleteRaccolta(Integer id);
}
