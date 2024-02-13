package Mudor.servicesimpl;

import Mudor.entity.CrossArtistaSingolo;
import Mudor.entity.Singolo;
import Mudor.repository.CrossArtistaSingoloRepository;
import Mudor.services.ArtistaMusicaleService;
import Mudor.services.CrossArtistaSingoloService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CrossArtistaSingoloServiceImpl implements CrossArtistaSingoloService {

    @Autowired
    private CrossArtistaSingoloRepository crossArtistaSingoloRepository;

    @Autowired
    private ArtistaMusicaleService artistaMusicaleService;


    @Override
    public List<CrossArtistaSingolo> getAllRelations() {
        return null;
    }

    @Override
    public List<CrossArtistaSingolo> getAssociationListByListOfSingoli(List <String> singoli){
        List<CrossArtistaSingolo> associazioniArtistaSingolo = crossArtistaSingoloRepository.findAll();
        for (CrossArtistaSingolo crossArtistaSingolo : associazioniArtistaSingolo) {
            for(String singolo : singoli)
            if(crossArtistaSingolo.getSingolo().getTitoloSingolo().equalsIgnoreCase(singolo)) {
                associazioniArtistaSingolo.add(crossArtistaSingolo);
            }
        }
        return associazioniArtistaSingolo;
    }

    @Override
    public CrossArtistaSingolo getRelation(Integer id) {
        return null;
    }

    @Override
    public List<String> getSingoliByArtistaMusicale (Integer id){
        List<String> singoli = new ArrayList<>();
        List<CrossArtistaSingolo> singoliDellArtista = crossArtistaSingoloRepository.findAll();
        for (CrossArtistaSingolo crossArtistaSingolo : singoliDellArtista) {
            if(crossArtistaSingolo.getArtistaMusicale().getIdArtista() == id) {
                singoli.add(crossArtistaSingolo.getSingolo().getTitoloSingolo());
            }
        }
        return singoli;
    }
    @Override
    public void addRelation(CrossArtistaSingolo crossArtistaSingolo) {
    }

    @Override
    public void updateRelation(CrossArtistaSingolo crossArtistaSingolo, Integer id) {

    }

    @Override
    public void deleteRelation(Integer id) {

    }
}
