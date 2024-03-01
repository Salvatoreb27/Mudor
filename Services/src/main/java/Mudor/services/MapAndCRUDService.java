package Mudor.services;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * L'interfaccia MapAndCRUDService definisce operazioni comuni per il mapping tra DTO e entità
 * e per le operazioni CRUD di base.
 *
 * @param <DTOType> il tipo del DTO
 * @param <EntityType> il tipo dell'entità
 * @param <IdType> il tipo dell'ID
 */
public interface MapAndCRUDService<DTOType, EntityType, IdType> {

    /**
     * Mappa un'entità in un DTO.
     *
     * @param entityType l'entità da mappare
     * @return il DTO corrispondente
     */
    DTOType mapTODTO(EntityType entityType);

    /**
     * Mappa una lista di entità in una lista di DTO.
     *
     * @param entityList la lista di entità da mappare
     * @return la lista di DTO corrispondente
     */
    List<DTOType> mapTODTOList(List<EntityType> entityList);

    /**
     * Mappa un DTO in un'entità.
     *
     * @param dtoType il DTO da mappare
     * @return l'entità corrispondente
     */
    EntityType mapToEntity(DTOType dtoType);

    /**
     * Mappa una lista di DTO in una lista di entità.
     *
     * @param dtoList la lista di DTO da mappare
     * @return la lista di entità corrispondente
     */
    List<EntityType> mapTOEntityList(List<DTOType> dtoList);

    /**
     * Ottiene tutti i DTO.
     *
     * @return la lista di tutti i DTO
     */
    List<DTOType> getDTOs();

    /**
     * Ottiene il DTO corrispondente all'ID specificato.
     *
     * @param id l'ID del DTO da ottenere
     * @return il DTO corrispondente all'ID specificato
     */
    DTOType getDTO(IdType id);

    /**
     * Aggiunge un'entità.
     *
     * @param DTO il DTO dell'entità da aggiungere
     * @return l'entità aggiunta
     */
    EntityType add(DTOType DTO);

    /**
     * Aggiorna un'entità con l'ID specificato.
     *
     * @param DTO il DTO dell'entità aggiornata
     * @param id l'ID dell'entità da aggiornare
     * @return l'entità aggiornata
     */
    EntityType update(DTOType DTO, IdType id);

    /**
     * Cancella un'entità con l'ID specificato.
     *
     * @param id l'ID dell'entità da cancellare
     */
    void delete(IdType id);
}

