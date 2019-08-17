package com.desafio.lanchonete.mock;

import com.desafio.lanchonete.model.Lanchonete;

import java.util.Arrays;
import java.util.List;

public class LanchoneteData {
    public static final String NOME = "teste";
    public static final boolean ALFACE = false;
    public static final boolean BACON = false;
    public static final boolean HAMBURGUER = false;
    public static final boolean OVO = false;
    public static final boolean QUEIJO = false;
    public static final int MUITA_CARNE = 0;
    public static final int MUITO_QUEIJO = 0;
    public static final double PRECO = 0.0;
    public static final boolean MENU = false;

    public static Lanchonete getLanchoneteMock(){
        return Lanchonete.builder()
                .name(NOME)
                .alface(ALFACE)
                .bacon(BACON)
                .hamburguer(HAMBURGUER)
                .ovo(OVO)
                .queijo(QUEIJO)
                .muita_carne(MUITA_CARNE)
                .muito_queijo(MUITO_QUEIJO)
                .preco(PRECO)
                .menu(MENU)
                .build();
    }

    public static List<Lanchonete> getLanchoneteListMock(){
        return Arrays.asList(getLanchoneteMock(), getLanchoneteMock(), getLanchoneteMock());
    }

    public static List<Lanchonete> getLanchoneteListMock(String id){
        int i = 1;
        return Arrays.asList(getLanchoneteMock().withId(id + i++), getLanchoneteMock().withId(id + i++), getLanchoneteMock().withId(id + i++));
    }
}
