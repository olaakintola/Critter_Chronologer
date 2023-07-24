package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet save(Pet pet){

        Customer customer = customerRepository.findById(pet.getOwnerId()).orElseThrow(() -> new CustomerNotFoundException());

        pet.setCustomer(customer);
        customer.getPets().add(pet);
        customerRepository.save(customer);
        List<Pet> savedPets =  customer.getPets();
        Pet nPet =  savedPets.get(savedPets.size() - 1 );
        return nPet;
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


    public void updateSinglePet(Pet pet, long petId) {
        Pet retrievedPet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException());
        retrievedPet.setNotes(pet.getNotes());
        petRepository.save(retrievedPet);
    }

    public void deleteSinglePet(long petId) {
        petRepository.deleteById(petId);
    }
}
