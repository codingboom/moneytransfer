package com.moneytransfer.exception;

public class ExistingAccountFoundException extends RuntimeException {
    public ExistingAccountFoundException (String message){
        super(message);
    }
}
