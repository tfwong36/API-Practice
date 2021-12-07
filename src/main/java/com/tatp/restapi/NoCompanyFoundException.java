package com.tatp.restapi;

public class NoCompanyFoundException extends Exception{
    public NoCompanyFoundException() {
        super("No Company Found");
    }
}
