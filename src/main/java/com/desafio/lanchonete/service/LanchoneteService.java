package com.desafio.lanchonete.service;

import com.desafio.lanchonete.exception.DuplicateKeyException;
import com.desafio.lanchonete.exception.EntityNotFoundException;
import com.desafio.lanchonete.exception.InvalidFieldException;
import com.desafio.lanchonete.exception.ServiceException;
import com.desafio.lanchonete.model.Lanchonete;
import com.desafio.lanchonete.repository.LanchoneteRepository;
import com.sun.deploy.util.ArrayUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LanchoneteService {
    @Autowired
    private LanchoneteRepository lanchoneteRepository;

    private final double preco_alface = 0.4;
    private final double preco_bacon = 2.0;
    private final double preco_hamburguer = 3.0;
    private final double preco_ovo = 0.8;
    private final double preco_queijo = 1.5;

    public Lanchonete insertLanche(Lanchonete lanche) throws ServiceException {
        validateInsert(lanche);
        return lanchoneteRepository.insert(lanche);
    }

    public Page<Lanchonete> findAll(Pageable pageable){
        return lanchoneteRepository.findAll(pageable);
    }

    public Lanchonete findByName(String name) throws ServiceException{
        String message = String.format("sandwich %s not found", name);
        return lanchoneteRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(message));
    }

    public void delete(String id)throws ServiceException{
        validateId(id);
        lanchoneteRepository.deleteById(id);
    }

    public void deleteAll() throws ServiceException{
        lanchoneteRepository.deleteAll();
    }

    private void validateId(String id) throws EntityNotFoundException {
        if(!lanchoneteRepository.findById(id).isPresent())
            throw new EntityNotFoundException(String.format("Sandwich %s not found", id));
    }

    public String findIngredientesByName(String name){
        String listaIngredientes = "";
        switch (name){
            case "X-Bacon":
                listaIngredientes = "bacon, hambúrguer de carne e queijo";
                break;
            case "X-Burger":
                listaIngredientes = "hambúrguer de carne e queijo";
                break;
            case "X-Egg":
                listaIngredientes = "ovo, hambúrguer de carne e queijo";
            case "X-Egg Bacon":
                listaIngredientes = "bacon, ovo, hambúrguer de carne e queijo";
        }
        return listaIngredientes;
    }

    private void validateInsert(Lanchonete lanche) throws ServiceException{
        if(StringUtils.hasLength(lanche.getId()))
            throw new InvalidFieldException("Id is an invalid parameter of the insert action");
        //if(lanchoneteRepository.findByName(lanche.getName()).isPresent())
            //throw new DuplicateKeyException(String.format("Name %s already exist", lanche.getName()));

        if(lanche.isMenu())
            precoLancheMenu(lanche);
        else
            precoLanchePersonalizado(lanche);
    }

    public void precoLancheMenu(Lanchonete lanche){
        //se foi selecionada uma opcao do cardápio, seta os ingredientes padrão
        //observação: a promoção light não se aplica a esses lanches, apenas aos personalizáveis
        switch (lanche.getName()) {
            case "X-Bacon":
                lanche.setBacon(true);
                lanche.setHamburguer(true);
                lanche.setQueijo(true);

                lanche.setPreco(preco_bacon + calcularPromocaoCarne(lanche) + calcularPromocaoQueijo(lanche));
                break;
            case "X-Burger":
                lanche.setHamburguer(true);
                lanche.setQueijo(true);

                lanche.setPreco(calcularPromocaoCarne(lanche) + calcularPromocaoQueijo(lanche));
                break;
            case "X-Egg":
                lanche.setHamburguer(true);
                lanche.setOvo(true);
                lanche.setQueijo(true);

                lanche.setPreco(calcularPromocaoCarne(lanche) + preco_ovo + calcularPromocaoQueijo(lanche));
                break;
            case "X-Egg Bacon":
                lanche.setBacon(true);
                lanche.setHamburguer(true);
                lanche.setOvo(true);
                lanche.setQueijo(true);

                lanche.setPreco(preco_bacon + calcularPromocaoCarne(lanche) + preco_ovo + calcularPromocaoQueijo(lanche));
                break;
        }
    }

    public void precoLanchePersonalizado(Lanchonete lanche){
        double preco = 0.0;

        if(lanche.isAlface())
            preco += preco_alface;
        if(lanche.isBacon())
            preco += preco_bacon;
        if(lanche.isOvo())
            preco += preco_ovo;

        if(lanche.isHamburguer()) {
            preco += calcularPromocaoCarne(lanche);
        }
        if(lanche.isQueijo()) {
            preco += calcularPromocaoQueijo(lanche);
        }

        //aplicar desconto promoção light
        if(lanche.isAlface() && !(lanche.isBacon()))
            preco = preco - (preco * 0.1);

        //formatar preço com duas casas decimais
        double d = preco;
        BigDecimal preco_formatado = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);

        lanche.setPreco(preco_formatado.doubleValue());
    }

    public double calcularPromocaoCarne(Lanchonete lanche){
        double preco = 0.0;
        int qta_carne = (lanche.getMuita_carne() / 3) * 2;
        if(qta_carne != 0)
            preco += preco_hamburguer * qta_carne;
        else
            preco += preco_hamburguer;

        //formatar preço com duas casas decimais
        double d = preco;
        BigDecimal preco_formatado = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);

        return preco_formatado.doubleValue();
    }

    public double calcularPromocaoQueijo(Lanchonete lanche){
        double preco = 0.0;
        int qto_queijo = (lanche.getMuito_queijo() / 3) * 2;
        if(qto_queijo != 0)
            preco += preco_queijo * qto_queijo;
        else
            preco += preco_queijo;

        //formatar preço com duas casas decimais
        double d = preco;
        BigDecimal preco_formatado = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);

        return preco_formatado.doubleValue();
    }

}
