package com.example.Gateway.exception;

public class NoDataDtoException extends RuntimeException{

    public NoDataDtoException(){

    }
    public NoDataDtoException(String message){
        super(message);
    }
}
