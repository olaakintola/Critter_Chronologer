package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    @Column
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> activities = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Employee.class )
    @JoinTable(
            name = "employee_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pet_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> pets = new ArrayList<>();

    public void addEmployee(Employee employee){
        this.employees.add(employee);
        employee.getSchedule().add(this);
    }

    public void removeEmployee(long employeeId){
        Employee employee = this.employees.stream().filter(emp -> emp.getId() == employeeId)
                .findFirst().orElse(null);
        if(employee != null){
            this.employees.remove(employee);
            employee.getSchedule().remove(this);
        }
    }

    public void addPet(Pet pet){
        this.pets.add(pet);
        pet.getSchedules().add(this);
    }

    public void removePet(long petId){
        Pet pet = this.pets.stream().filter(p -> p.getId() == petId)
                .findFirst().orElse(null);
        if(pet != null){
            this.pets.remove(pet);
            pet.getSchedules().remove(this);
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
