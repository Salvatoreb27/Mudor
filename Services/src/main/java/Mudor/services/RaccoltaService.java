package Mudor.services;

import Mudor.DTO.RaccoltaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RaccoltaService {
    List<RaccoltaDTO> getRaccolte();

    RaccoltaDTO getRaccolta(Integer id);

    void addRaccolta(RaccoltaDTO artistaMusicaleDTO);

    void updateRaccolta(RaccoltaDTO artistaMusicaleDTO, Integer id);

    void deleteRaccolta(Integer id);
}
