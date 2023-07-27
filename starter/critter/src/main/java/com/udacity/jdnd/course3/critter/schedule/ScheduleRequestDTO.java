package com.udacity.jdnd.course3.critter.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

// delete class
public class ScheduleRequestDTO {

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
