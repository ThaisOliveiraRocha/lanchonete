package com.desafio.lanchonete.repository;

import com.desafio.lanchonete.model.Lanchonete;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LanchoneteRepository extends MongoRepository<Lanchonete, String> {
    Optional<Lanchonete> findByName(String name);

    Optional<Lanchonete> findByNameIgnoreCase(String name);
}
