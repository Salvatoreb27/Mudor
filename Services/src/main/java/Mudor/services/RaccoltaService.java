package Mudor.services;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.entity.Raccolta;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface RaccoltaService extends MapAndCRUDService<RaccoltaDTO, Raccolta, Integer> {
    List<RaccoltaDTO> getRaccoltaBy(Integer idRaccolta, String titoloRaccolta, Date dataRilascio, List<String> brani, List<String> generi, String nome);
}
