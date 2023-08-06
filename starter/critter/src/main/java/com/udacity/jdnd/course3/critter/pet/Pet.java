package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.activities.Activity;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    private long ownerId;

    private LocalDate birthDate;

    private String notes;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> schedules;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pet_activities",
        joinColumns = { @JoinColumn(name = "pet_id") },
        inverseJoinColumns = { @JoinColumn(name = "activity_id") })
    private Set<Activity> petActivities = new HashSet<>();

    @ElementCollection(targetClass=String.class)
    Map<String, String> petActivityMap = new HashMap<>();

    private EmployeeSkill activity;

    public EmployeeSkill getActivity() {
        return activity;
    }

    public void setActivity(EmployeeSkill activity) {
        this.activity = activity;
    }

    public void addPetAndActivity(String pet, String activity){
        if(petActivityMap.containsKey(pet) ){
            petActivityMap.replace(pet, activity);
        }

        petActivityMap.put(pet, activity);
        //this could lead to a bug
        type = PetType.valueOf(pet);
    }

    public String displayPetActivity(){
        String petActivity = "";
        String petName = type.name();
        return petActivityMap.get(petName);
    }

    public void addPetActivity(Activity activity){
        this.petActivities.add(activity);
        activity.getPets().add(this);
    }

    public void removePetActivity(long petActivityId){
        Activity petActivity = this.petActivities.stream().filter( activity -> activity.getId() == petActivityId)
                .findFirst().orElse(null);
        if(petActivity != null){
            this.petActivities.remove(petActivity);
            petActivity.getPets().remove(this);
        }

    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
