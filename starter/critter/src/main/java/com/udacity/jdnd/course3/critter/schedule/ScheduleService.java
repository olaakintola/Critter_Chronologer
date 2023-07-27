package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.HashSet;
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

    public void deleteEmployeeFromSchedule(long scheduleId, long employeeId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException());

        schedule.removeEmployee(employeeId);
        scheduleRepository.save(schedule);
    }

    public void fillScheduleOpenSlot(Schedule schedule) {
        HashSet<DayOfWeek> daysWithOpenSlots = new HashSet<>();
        String availableDay = schedule.getDate().getDayOfWeek().name();
        switch (availableDay){
            case "SUNDAY":
                daysWithOpenSlots.add(DayOfWeek.SUNDAY);
                break;
            case "MONDAY":
                daysWithOpenSlots.add(DayOfWeek.MONDAY);
                break;
            case "TUESDAY":
                daysWithOpenSlots.add(DayOfWeek.TUESDAY);
                break;
            case "WEDNESDAY":
                daysWithOpenSlots.add(DayOfWeek.WEDNESDAY);
                break;
            case "THURSDAY":
                daysWithOpenSlots.add(DayOfWeek.THURSDAY);
                break;
            case "FRIDAY":
                daysWithOpenSlots.add(DayOfWeek.FRIDAY);
                break;
            default:
                daysWithOpenSlots.add(DayOfWeek.SATURDAY);
        }

        List<Schedule> scheduleList = scheduleRepository.
                findByEmployees_DaysAvailableEqualAndEmployees_Allocated( daysWithOpenSlots, false);

        Employee employee = scheduleList.get(0).getEmployees().get(0);
        employee.setStartTime(schedule.getStartTime());
        employee.setEndTime(schedule.getEndTime());
        employee.setAllocated(true);
        schedule.addEmployee(employee);
        scheduleRepository.save(schedule);

    }
}
