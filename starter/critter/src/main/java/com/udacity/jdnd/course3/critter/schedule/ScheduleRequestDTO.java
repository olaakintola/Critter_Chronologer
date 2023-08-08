package com.udacity.jdnd.course3.critter.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;

// delete class
public class ScheduleRequestDTO {

    private DayOfWeek workDay;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public DayOfWeek getWorkDay() {
        return workDay;
    }

    public void setWorkDay(DayOfWeek workDay ) {
        this.workDay = workDay;
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
