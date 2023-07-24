package com.udacity.jdnd.course3.critter.schedule;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException() {
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
