package com.tatp.restapi.exception;

public class NoEmployeeFoundException extends RuntimeException {
    public NoEmployeeFoundException() {
        super("No Employee Found");
    }
}
