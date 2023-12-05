package com.example.Gateway.exception;

public class ExistUserException extends RuntimeException{

    public ExistUserException(){

    }
    public ExistUserException(String message){
        super(message);
    }
}
