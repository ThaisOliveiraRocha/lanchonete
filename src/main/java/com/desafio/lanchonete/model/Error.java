package com.desafio.lanchonete.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private int status;
    private String message;
}
