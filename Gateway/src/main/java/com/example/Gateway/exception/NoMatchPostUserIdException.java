package com.example.Gateway.exception;

public class NoMatchPostUserIdException extends RuntimeException{
    public NoMatchPostUserIdException(){}
    public NoMatchPostUserIdException(String message){
        super(message);
    }
}
