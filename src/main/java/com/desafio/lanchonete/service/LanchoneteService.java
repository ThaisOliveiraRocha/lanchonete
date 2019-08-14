package com.desafio.lanchonete.service;

import com.desafio.lanchonete.exception.DuplicateKeyException;
import com.desafio.lanchonete.exception.EntityNotFoundException;
import com.desafio.lanchonete.exception.InvalidFieldException;
import com.desafio.lanchonete.exception.ServiceException;
import com.desafio.lanchonete.model.Lanchonete;
import com.desafio.lanchonete.repository.LanchoneteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LanchoneteService {
    @Autowired
    private LanchoneteRepository lanchoneteRepository;

    public Lanchonete insertLanche(Lanchonete lanche) throws ServiceException {
        validateInsert(lanche);
        return lanchoneteRepository.insert(lanche);
    }

    public Page<Lanchonete> findAll(Pageable pageable){
        return lanchoneteRepository.findAll(pageable);
    }

    public Lanchonete findById(String id) throws ServiceException{
        String message = String.format("sandwich %s not found", id);
        return lanchoneteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    private void validateInsert(Lanchonete lanche) throws ServiceException{
        if(StringUtils.hasLength(lanche.getId()))
            throw new InvalidFieldException("Id is an invalid parameter of the insert action");
        if(lanchoneteRepository.findByName(lanche.getName()).isPresent())
            throw new DuplicateKeyException(String.format("Name %s already exist", lanche.getName()));

    }
}
