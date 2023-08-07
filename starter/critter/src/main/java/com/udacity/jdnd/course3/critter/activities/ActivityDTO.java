package com.udacity.jdnd.course3.critter.activities;

import com.udacity.jdnd.course3.critter.pet.Pet;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class ActivityDTO {

    private long id;

    private List<Pet> pets = new ArrayList<>();

    private String behaviour;

    public ActivityDTO() {
    }

    public ActivityDTO(String behaviour) {
        this.behaviour = behaviour;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}
