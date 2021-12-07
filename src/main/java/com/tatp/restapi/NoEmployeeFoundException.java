package com.tatp.restapi;

public class NoEmployeeFoundException extends Exception{
    public NoEmployeeFoundException() {
        super("No Employee Found");
    }
}
