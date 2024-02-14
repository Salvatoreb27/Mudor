package Mudor.services;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.SingoloDTO;
import Mudor.entity.Singolo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SingoloService extends MapAndCRUDService<SingoloDTO, Singolo, Integer> {
    Singolo getSingoloByTitolo (String titolo);
    List<SingoloDTO> getSingoloBy(Integer idSingolo, String titoloSingolo, Date dataRilascio, List<String> brani, List<String> generi, String nome);
}
