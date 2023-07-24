package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Long saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule).getId();
    }


    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForEmployee(long employeeId) {
        return scheduleRepository.findByEmployees_Id(employeeId);
    }

    public List<Schedule> getSchedulesForPet(long petId) {
        return scheduleRepository.findByPets_Id(petId);
    }

    public List<Schedule> getSchedulesForCustomer(long customerId) {
        // change this line
        return scheduleRepository.findByPets_OwnerId(customerId);

    }

    public void deleteAllSchedules() {
        scheduleRepository.deleteAll();
    }
}
