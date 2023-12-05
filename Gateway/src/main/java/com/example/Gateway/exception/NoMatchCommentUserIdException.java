package com.example.Gateway.exception;

public class NoMatchCommentUserIdException extends RuntimeException {
    public NoMatchCommentUserIdException()
    {
    }
    public NoMatchCommentUserIdException (String message){
        super(message);
    }
}
