package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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

    public void deleteSingleSchedule(long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public Pet addPetToSchedule(long scheduleId, Pet newPet) {
        Pet pet = scheduleRepository.findById(scheduleId).map( schedule -> {
            long petId = newPet.getId();

            //pet exists
            if(petId != 0L){
                Pet _pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException());
                schedule.addPet(_pet);
                scheduleRepository.save(schedule);
                return _pet;
            }

            // create and add new pet
            schedule.addPet(newPet);
            return petRepository.save(newPet);
        }).orElseThrow(() -> new ScheduleNotFoundException());

        return pet;
    }


    public void deletePetFromSchedule(long scheduleId, long petId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException());

        schedule.removePet(petId);
        scheduleRepository.save(schedule);
    }

    public Employee addEmployeeToSchedule(long scheduleId, Employee employee) {
        Employee emp = scheduleRepository.findById(scheduleId).map(schedule -> {
            long employeeId = employee.getId();

            // employee exists
            if(employeeId != 0L){
                Employee _employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException() );
                schedule.addEmployee(_employee);
                scheduleRepository.save(schedule);
                return _employee;
            }

            //create and add new employee
            schedule.addEmployee(employee);
            return employeeRepository.save(employee);

        }).orElseThrow(() -> new ScheduleNotFoundException());
        return employee;
    }
}
