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

    public Long save(Pet pet){
//        return customerRepository.findById(pet.getOwnerId()).map(customer -> {
//            pet.setCustomer(customer);
//            return petRepository.save(pet);
//        }).orElseThrow(() -> new CustomerNotFoundException()).getId();

        Customer customer = customerRepository.findById(pet.getOwnerId()).orElseThrow(() -> new CustomerNotFoundException());

        customer.getPets().add(pet);
        pet.setCustomer(customer);
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


//    //        return customerRepository.findById(pet.getOwnerId()).map(customer -> {
////            pet.setCustomer(customer);
////            return petRepository.save(pet);
////        }).orElseThrow(() -> new CustomerNotFoundException()).getId();
//
//    Customer customer = customerRepository.findById(pet.getOwnerId()).orElseThrow(() -> new CustomerNotFoundException());
//        customer.getPets().add(pet);
//        customerRepository.save(customer);
//    //        pet.setCustomer(customer);
//    Pet newPet = petRepository.save(pet);
//    List<Pet> pets = customerRepository.findById(pet.getOwnerId()).get().getPets();
//    Pet lastSavedPet = pets.get(pets.size() -1 );
//        return lastSavedPet.getId();
////        return newPet.getId();


}
