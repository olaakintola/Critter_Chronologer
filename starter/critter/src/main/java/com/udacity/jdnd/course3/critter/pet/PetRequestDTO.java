package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

public class PetRequestDTO {

    private PetType type;

    private EmployeeSkill activity;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public EmployeeSkill getActivity() {
        return activity;
    }

    public void setActivity(EmployeeSkill activity) {
        this.activity = activity;
    }
}
