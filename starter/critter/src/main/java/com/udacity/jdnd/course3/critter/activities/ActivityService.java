package com.udacity.jdnd.course3.critter.activities;

import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    private final PetRepository petRepository;
    private final ActivityRepository activityRepository;

    public ActivityService(PetRepository petRepository, ActivityRepository activityRepository) {
        this.petRepository = petRepository;
        this.activityRepository = activityRepository;
    }

    public Activity save(long petId, Activity activity) {
        Activity retrievedActivity = petRepository.findById(petId).map(pet -> {
            long activityId = activity.getId();

            // activity existed
            if(activityId != 0L){
                Activity _activity = activityRepository.findById(activityId).orElseThrow(() -> new ActivityNotFoundException());
                pet.addPetActivity(_activity);
                petRepository.save(pet);
                return _activity;
            }

            // add and create new activity
            pet.addPetActivity(activity);
            return activityRepository.save(activity);
        }).orElseThrow(() -> new PetNotFoundException());

        return retrievedActivity;
    }
}
