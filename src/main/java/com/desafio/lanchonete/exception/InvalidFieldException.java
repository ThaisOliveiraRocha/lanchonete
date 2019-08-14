package com.desafio.lanchonete.exception;


public class InvalidFieldException extends ServiceException {
    public InvalidFieldException (String message){
        super(message);
    }
}
