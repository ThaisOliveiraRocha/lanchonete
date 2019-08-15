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

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/lanche", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LanchoneteController {
    @Autowired
    LanchoneteService service;

    @PostMapping
    public ResponseEntity<Lanchonete> insertLanche(@RequestBody Lanchonete lanche) throws Exception{
        return ResponseEntity.ok(service.insertLanche(lanche));
    }

    @GetMapping("/{id}")
    public ResponseEntity <Lanchonete> getById(@PathVariable String id) throws Exception{
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Stream<Lanchonete>> getAll(@SortDefault.SortDefaults(
            {@SortDefault(sort = "name", direction = Sort.Direction.ASC)})Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable).get());
    }
}
