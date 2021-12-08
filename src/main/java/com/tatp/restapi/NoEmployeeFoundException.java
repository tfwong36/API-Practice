package com.tatp.restapi;

public class NoEmployeeFoundException extends RuntimeException {
    public NoEmployeeFoundException() {
        super("No Employee Found");
    }
}
