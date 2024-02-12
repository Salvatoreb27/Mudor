package Mudor.services;

import Mudor.entity.CrossArtistaSingolo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrossArtistaSingoloService {

    List<CrossArtistaSingolo> getAllRelations();

    CrossArtistaSingolo getRelation(Integer id);

    void addRelation(CrossArtistaSingolo crossArtistaSingolo);

    void updateRelation(CrossArtistaSingolo crossArtistaSingolo, Integer id);

    void deleteRelation(Integer id);
}
