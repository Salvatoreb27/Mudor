package Mudor.services;
import java.util.List;

public interface MapAndCRUDService<DTOType, EntityType, IdType> {
    DTOType mapTODTO(EntityType entityType);

    List<DTOType> mapTODTOList(List<EntityType> entityList);

    EntityType mapToEntity(DTOType dtoType);

    List<EntityType> mapTOEntityList(List<DTOType> dtoList);

    List<DTOType> getDTOs();

    DTOType getDTO(IdType id);

    void add(DTOType DTO);

    void update(DTOType DTO, IdType id);

    void delete(IdType id);
}

