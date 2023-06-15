package com.wilson.storybackend.exceptions;

public class TargetNotFoundException extends Exception {
    public TargetNotFoundException() {
        super();
    }

    public TargetNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
