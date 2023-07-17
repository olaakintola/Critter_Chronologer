package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Long save(Pet pet){
        Pet newPet = petRepository.save(pet);
        return newPet.getId();
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

}
