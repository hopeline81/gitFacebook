package com.example.facebookdemo.exception;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(String message){
        super(message);
    }
}
