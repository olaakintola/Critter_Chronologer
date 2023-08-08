package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    @NotEmpty(message = "EmployeeIds cannot be empty")
    private List<Long> employeeIds;

    @NotEmpty(message = "PetIds cannot be empty")
    private List<Long> petIds;
    @NotNull(message = "Day is mandatory")
    private DayOfWeek workDay;
    private Set<EmployeeSkill> activities;

    private LocalDate date;

    Map<String, List<Employee>> timeSlotMap = new HashMap<>();

    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public DayOfWeek getWorkDay() {
        return workDay;
    }

    public void setWorkDay(DayOfWeek workDay) {
        this.workDay = workDay;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, List<Employee>> getTimeSlotMap() {
        return timeSlotMap;
    }

    public void setTimeSlotMap(Map<String, List<Employee>> timeSlotMap) {
        this.timeSlotMap = timeSlotMap;
    }
}
