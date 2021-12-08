package com.tatp.restapi;

public class NoCompanyFoundException extends RuntimeException {
    public NoCompanyFoundException() {
        super("No Company Found");
    }
}
