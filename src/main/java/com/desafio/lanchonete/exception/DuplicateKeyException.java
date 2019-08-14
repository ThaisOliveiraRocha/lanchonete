package com.desafio.lanchonete.exception;

public class DuplicateKeyException extends ServiceException {
    public DuplicateKeyException (String message){
        super(message);
    }
}
