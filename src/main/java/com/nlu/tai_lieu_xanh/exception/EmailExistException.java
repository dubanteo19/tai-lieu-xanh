package com.nlu.tai_lieu_xanh.exception;

public class EmailExistException extends RuntimeException {
    public EmailExistException() {
        super("Email already exist");
    }
}
