package com.udacity.jdnd.course3.critter.schedule;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    EmployeeRepository employeeRepository;

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
        Set<DayOfWeek> daysAvailable = new HashSet<>();
        DayOfWeek employeesWorkingDays = schedule.getWorkDay();
        daysAvailable.add(employeesWorkingDays);

        List<Schedule> scheduleList = scheduleRepository.findSchedulesByEmployeesDaysAvailableIn(daysAvailable);

        Schedule retrievedSchedule = scheduleList.get(0);
        List<Employee> scheduledEmployees = retrievedSchedule.getEmployees();
        Map<String, List<Employee> > timeSlotMap = retrievedSchedule.getTimeSlotMap();
        String startTime = schedule.getStartTime().getHour()+":"+schedule.getStartTime().getMinute();
        String endTime = schedule.getEndTime().getHour()+":"+schedule.getEndTime().getMinute();
        int sTime = schedule.getStartTime().getHour();
        int eTime = schedule.getEndTime().getHour();

        Employee openEmployee = null;

        for(Employee emp: scheduledEmployees){
            if(!timeSlotMap.containsKey(startTime) ){
                timeSlotMap.put(startTime, new ArrayList<>() );
                timeSlotMap.get(startTime).add(emp);
                openEmployee = emp;

                // you might need to find somewhere to add endtime
                break;
            }else{
                if( timeSlotMap.get(startTime).contains(emp) ){
                    continue;
                }
                timeSlotMap.get(startTime).add(emp);
                openEmployee = emp;
            }
        }

        if(eTime - sTime > 1){
            fillMoreTimeSlotsWithEmployee(timeSlotMap, eTime, sTime, schedule, openEmployee);
        }


    }

    private void fillMoreTimeSlotsWithEmployee(Map<String, List<Employee>> timeSlotMap, int endTime,
                                               int startTime, Schedule schedule, Employee employee) {
        int workingHours = endTime - startTime;
        int incrementTimeBy = 1;

        while(workingHours > 1){
            LocalDateTime t = schedule.getStartTime().plusHours(incrementTimeBy);
            String slotToFill = t.getHour() + ":" + t.getMinute();
            if(!timeSlotMap.containsKey(slotToFill) ){
                timeSlotMap.put(slotToFill, new ArrayList<>() );
                timeSlotMap.get(slotToFill).add(employee);
            }

            workingHours--;
            incrementTimeBy++;
        }
    }
}
