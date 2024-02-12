package Mudor.services;

import Mudor.DTO.CrossArtistaSingoloDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrossArtistaSingoloService {

    List<CrossArtistaSingoloDTO> getAllRelations();

    CrossArtistaSingoloDTO getRelation(Integer id);

    void addRelation(CrossArtistaSingoloDTO crossArtistaSingoloDTO);

    void updateRelation(CrossArtistaSingoloDTO crossArtistaSingoloDTO, Integer id);

    void deleteRelation(Integer id);
}
