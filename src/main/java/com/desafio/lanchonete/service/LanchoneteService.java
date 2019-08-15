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

    final double preco_alface = 0.4;
    final double preco_bacon = 2.0;
    final double preco_hamburguer = 3.0;
    final double preco_ovo = 0.8;
    final double preco_queijo = 1.5;

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

        lanche.setPreco(calculo(lanche));
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
}
