package Mudor.services;

import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.DTO.CrossArtistaSingoloDTO;
import Mudor.entity.ArtistaMusicale;
import Mudor.entity.CrossArtistaSingolo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrossArtistaSingoloService {

    CrossArtistaSingoloDTO mapTOCrossArtistaSingoloDTO(CrossArtistaSingolo crossArtistaSingolo);
    List<CrossArtistaSingoloDTO> mapTOCrossArtistaSingoloDTOList(List<CrossArtistaSingolo> crossArtistaSingoloList);
    CrossArtistaSingolo mapToCrossArtistaSingolo(CrossArtistaSingoloDTO crossArtistaSingoloDTO);
    List<CrossArtistaSingolo> mapTOCrossArtistaSingoloList(List<CrossArtistaSingoloDTO> crossArtistaSingoloDTOList);
    List<CrossArtistaSingoloDTO> getAllRelations();
    List<CrossArtistaSingolo> getAssociationListByListOfSingoli(List <String> singoli);
    List<CrossArtistaSingolo> getAssociationListByListOfArtisti(List <String> artisti);
    CrossArtistaSingoloDTO getRelation(Integer id);
    List<String> getSingoliByArtistaMusicale(Integer id);
    List<String> getArtistiBySingolo(Integer id);
    void addRelation(CrossArtistaSingoloDTO crossArtistaSingoloDTO);
    void updateRelation(CrossArtistaSingoloDTO crossArtistaSingoloDTO, Integer id);
    void deleteRelation(Integer id);
}
