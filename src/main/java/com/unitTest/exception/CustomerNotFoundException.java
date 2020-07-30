package com.unitTest.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Cliente não encontrado!");
    }
}
