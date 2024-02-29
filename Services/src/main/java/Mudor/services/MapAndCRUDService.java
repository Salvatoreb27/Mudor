package Mudor.services;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MapAndCRUDService<DTOType, EntityType, IdType> {
    DTOType mapTODTO(EntityType entityType);

    List<DTOType> mapTODTOList(List<EntityType> entityList);

    EntityType mapToEntity(DTOType dtoType);

    List<EntityType> mapTOEntityList(List<DTOType> dtoList);

    List<DTOType> getDTOs();

    DTOType getDTO(IdType id);

    EntityType add(DTOType DTO);

    EntityType update(DTOType DTO, IdType id);

    void delete(IdType id);
}

