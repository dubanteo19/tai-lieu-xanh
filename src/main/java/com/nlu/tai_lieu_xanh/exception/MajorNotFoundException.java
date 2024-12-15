package com.nlu.tai_lieu_xanh.exception;

public class MajorNotFoundException extends RuntimeException {
    public MajorNotFoundException() {
        super("Major not found");
    }
}
