package Mudor.services;

import Mudor.DTO.CrossArtistaSingoloDTO;
import Mudor.entity.CrossArtistaSingolo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrossArtistaSingoloService extends MapAndCRUDService<CrossArtistaSingoloDTO, CrossArtistaSingolo, Integer> {


    List<CrossArtistaSingolo> getAssociationListByListOfSingoli(List <String> singoli);
    List<CrossArtistaSingolo> getAssociationListByListOfArtisti(List <String> artisti);
//    CrossArtistaSingoloDTO getRelation(Integer id);
    List<String> getSingoliByArtistaMusicale(Integer id);
    List<String> getArtistiBySingolo(Integer id);

}
