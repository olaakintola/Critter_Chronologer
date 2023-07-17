package com.udacity.jdnd.course3.critter.pet;


public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException() {
    }

    public PetNotFoundException(String message) {
        super(message);
    }
}
