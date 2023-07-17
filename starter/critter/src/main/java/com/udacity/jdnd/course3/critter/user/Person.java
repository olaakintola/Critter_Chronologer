package com.udacity.jdnd.course3.critter.user;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

// I'm using the mappedsuperclass because the customer and employee have common attributes which is in the person class but
// there is no need to have a person table in the database because we dont need the person entity in our program
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue
    protected long id;
    @Nationalized
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
