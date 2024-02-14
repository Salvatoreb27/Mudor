package Mudor.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public abstract class AbstractController<DTOType, IdType> {

    public abstract ResponseEntity<List<DTOType>> getAll();

    public abstract ResponseEntity<Void> add(@RequestBody DTOType dtoType);

    public abstract ResponseEntity<Void> update(@RequestBody DTOType dtoType, @RequestParam IdType idType);

    public abstract ResponseEntity<Void> delete(@RequestParam Integer IdType);

}
