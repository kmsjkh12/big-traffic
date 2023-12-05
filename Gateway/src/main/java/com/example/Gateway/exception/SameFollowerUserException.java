package com.example.Gateway.exception;

public class SameFollowerUserException extends RuntimeException{
    public SameFollowerUserException(){}
    public SameFollowerUserException(String s) {
        super(s);
    }
}
