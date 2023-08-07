package com.udacity.jdnd.course3.critter.activities;

public class ActivityNotFoundException extends RuntimeException {

    public ActivityNotFoundException() {
    }

    public ActivityNotFoundException(String message) {
        super(message);
    }
}
