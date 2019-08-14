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

    @NonNull
    private String preco;

    private String extra; //pode ser nulo
}
