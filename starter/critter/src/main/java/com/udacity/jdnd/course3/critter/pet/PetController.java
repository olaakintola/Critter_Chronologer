package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@Valid @RequestBody PetDTO petDTO) {

        Pet newPet = new Pet();
        BeanUtils.copyProperties(petDTO, newPet, "id");
        Pet savedPet = petService.save(newPet);
        BeanUtils.copyProperties(savedPet, petDTO);
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        PetDTO petDTO = new PetDTO();
        Pet retrievedPet = petService.getPet(petId);
        BeanUtils.copyProperties(retrievedPet, petDTO);
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<Pet> petList = petService.getAllPets();
        List<PetDTO> petDTOList = new ArrayList<>();
        for(Pet pet: petList){
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<Pet> petsByOwner = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOList = new ArrayList<>();
        for(Pet pet:petsByOwner){
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }

    @PutMapping("/{petId}")
    public void updatePet(@PathVariable long petId, @RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet );
        petService.updateSinglePet(petId, pet);
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable long petId){
        petService.deleteSinglePet(petId);
    }

    @GetMapping("/schedule/{scheduleId}")
    public List<PetDTO> getAllPetsByScheduleId(@PathVariable long scheduleId) {
        List<Pet> petList = petService.getAllPetsByScheduleId(scheduleId);
        List<PetDTO> petDTOList = new ArrayList<>();
        for(Pet pet:petList){
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTOList.add(petDTO);
        }
        return petDTOList;

    }


}
