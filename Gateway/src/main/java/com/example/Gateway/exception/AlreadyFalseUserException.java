package com.example.Gateway.exception;

public class AlreadyFalseUserException extends RuntimeException{
    public AlreadyFalseUserException(){

    }
    public AlreadyFalseUserException(String message){
        super(message);
    }
}
