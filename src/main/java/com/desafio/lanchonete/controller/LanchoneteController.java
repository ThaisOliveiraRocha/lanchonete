package com.desafio.lanchonete.controller;

import com.desafio.lanchonete.model.Lanchonete;
import com.desafio.lanchonete.service.LanchoneteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class LanchoneteController {
    @Autowired
    LanchoneteService service;

    /*
    * Sempre que um pedido é realizado, é incluido no banco
    * */
    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping
    public ResponseEntity<Lanchonete> insertLanche(@RequestBody Lanchonete lanche) throws Exception{
        return ResponseEntity.ok(service.insertLanche(lanche));
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @DeleteMapping("/{id}")
    public ResponseEntity <String> delete (@PathVariable String id) throws Exception{
        service.delete(id);
        return ResponseEntity.ok(String.format("Excluido com Sucesso!!!"));//return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity <String> deleteAll () throws Exception{
        service.deleteAll();
        return ResponseEntity.ok(String.format("Todos os itens foram excluidos!"));//return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping
    public ResponseEntity<Stream<Lanchonete>> getAll(@SortDefault.SortDefaults(
            {@SortDefault(sort = "name", direction = Sort.Direction.ASC)})Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable).get());
    }
}
