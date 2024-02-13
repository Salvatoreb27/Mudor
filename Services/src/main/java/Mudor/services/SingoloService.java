package Mudor.services;

import Mudor.DTO.RaccoltaDTO;
import Mudor.DTO.SingoloDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SingoloService {
    List<SingoloDTO> getSingoli();
    SingoloDTO getSingolo(Integer id);
    void addSingolo(SingoloDTO artistaMusicaleDTO);
    void updateSingolo(SingoloDTO singoloDTO, Integer id);
    void deleteSingolo(Integer id);
}
