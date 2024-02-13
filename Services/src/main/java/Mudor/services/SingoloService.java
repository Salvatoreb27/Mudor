package Mudor.services;

import Mudor.DTO.SingoloDTO;
import Mudor.entity.Singolo;
import org.springframework.stereotype.Service;

@Service
public interface SingoloService extends MapAndCRUDService<SingoloDTO, Singolo, Integer> {

//    SingoloDTO mapTOSingoloDTO(Singolo singolo);
//    List<SingoloDTO> mapTOSingoloDTOList(List<Singolo> singoloList);
//    Singolo mapToSingolo(SingoloDTO singoloDTO);
//    List<Singolo> mapTOSingoloList(List<SingoloDTO> singoloDTOList);
//    List<SingoloDTO> getSingoli();
//    SingoloDTO getSingolo(Integer id);
    Singolo getSingoloByTitolo (String titolo);
//    void addSingolo(SingoloDTO artistaMusicaleDTO);
//    void updateSingolo(SingoloDTO singoloDTO, Integer id);
//    void deleteSingolo(Integer id);
}
