package com.desafio.lanchonete.exception;

public class EntityNotFoundException extends ServiceException {
    public EntityNotFoundException(String message){
        super(message);
    }
}
