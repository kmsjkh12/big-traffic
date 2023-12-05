package com.example.Gateway.exception;


public class NotEqualsAuthUserException extends RuntimeException{
    public NotEqualsAuthUserException(){}

    public NotEqualsAuthUserException(String message){
        super(message);
    }
}
