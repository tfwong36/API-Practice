package com.tatp.restapi.exception;

public class NoCompanyFoundException extends RuntimeException {
    public NoCompanyFoundException() {
        super("No Company Found");
    }
}
