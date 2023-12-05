package com.example.Gateway.exception;

public class ExistCommentException extends RuntimeException{
    public ExistCommentException(){

    }
    public ExistCommentException(String message){
        super(message);
    }
}
