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

    final double preco_alface = 0.4;
    final double preco_bacon = 2.0;
    final double preco_hamburguer = 3.0;
    final double preco_ovo = 0.8;
    final double preco_queijo = 1.5;

    @PostMapping
    public ResponseEntity<Lanchonete> insertLanche(@RequestBody Lanchonete lanche) throws Exception{
        lanche.setPreco(calculo(lanche));
        return ResponseEntity.ok(service.insertLanche(lanche));
    }

    private double calculo(Lanchonete lanche){
        double preco = 0.0;

        if(lanche.isAlface())
            preco += preco_alface;
        if(lanche.isBacon())
            preco += preco_bacon;
        if(lanche.isOvo())
            preco += preco_ovo;

        if(lanche.isHamburguer()) {
            //verificar promocao muita carne
            int qta_carne = (lanche.getMuita_carne() / 3) * 2;
            if(qta_carne != 0)
                preco += preco_hamburguer * qta_carne;
            else
                preco += preco_hamburguer;
        }
        if(lanche.isQueijo()) {
            int qto_queijo = (lanche.getMuito_queijo() / 3) * 2;
            if(qto_queijo != 0)
                preco += preco_queijo * qto_queijo;
            else
                preco += preco_queijo;
        }

        //verificar promoção light
        if(lanche.isAlface() && !(lanche.isBacon()))
            preco = preco - (preco * 0.1);

        return preco;
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
