package Mudor.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Questa classe astratta fornisce un'implementazione base per i controller che gestiscono le operazioni CRUD (Create, Read, Update, Delete) per entità di tipo DTO.
 *
 * @param <DTOType> il tipo di oggetto DTO gestito dal controller
 * @param <IdType>  il tipo di ID dell'oggetto gestito dal controller
 */
public abstract class AbstractController<DTOType, IdType> {

    /**
     * Recupera tutti gli oggetti DTO gestiti dal controller.
     *
     * @return una risposta HTTP contenente una lista di oggetti DTO
     */
    public abstract ResponseEntity<List<DTOType>> getAll();

    /**
     * Aggiunge un nuovo oggetto DTO.
     *
     * @param dtoType l'oggetto DTO da aggiungere
     * @return una risposta HTTP vuota
     */
    public abstract ResponseEntity<Void> add(@RequestBody DTOType dtoType);

    /**
     * Aggiorna un oggetto DTO esistente identificato dall'ID specificato.
     *
     * @param dtoType l'oggetto DTO aggiornato
     * @param idType  l'ID dell'oggetto DTO da aggiornare
     * @return una risposta HTTP vuota
     */
    public abstract ResponseEntity<Void> update(@RequestBody DTOType dtoType, @RequestParam IdType idType);

    /**
     * Elimina un oggetto identificato dall'ID specificato.
     *
     * @param IdType l'ID dell'oggetto da eliminare
     * @return una risposta HTTP vuota
     */
    public abstract ResponseEntity<Void> delete(@RequestParam Integer IdType);
}
