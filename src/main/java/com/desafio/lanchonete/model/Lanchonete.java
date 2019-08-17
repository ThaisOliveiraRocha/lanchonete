package com.desafio.lanchonete.model;

import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Wither;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@Wither
public class Lanchonete {
    @Id
    private String id;

    @NonNull
    private String name;

    private boolean menu;

    private boolean alface;

    private boolean bacon;

    private boolean hamburguer;

    private boolean ovo;

    private boolean queijo;

    private int muita_carne;

    private int muito_queijo;

    private double preco;
}
