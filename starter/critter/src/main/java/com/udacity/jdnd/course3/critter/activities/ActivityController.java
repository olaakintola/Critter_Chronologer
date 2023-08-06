package com.udacity.jdnd.course3.critter.activities;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/pets/{petId}/behaviours")
    public ActivityDTO addActivity(@PathVariable long petId, @RequestBody ActivityDTO activityDTO){
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO, activity);
        Activity savedActivity = activityService.save(petId, activity);
        BeanUtils.copyProperties(savedActivity, activityDTO);
        return activityDTO;
    }



}
