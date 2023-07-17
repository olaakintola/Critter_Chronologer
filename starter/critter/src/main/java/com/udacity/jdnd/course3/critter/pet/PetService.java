package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.EmployeeNotFoundException;
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

    public Pet getPet(long petId) {
        return petRepository.findById(petId).orElseThrow(()->new PetNotFoundException());
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
