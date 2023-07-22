package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = scheduleDTO.getPetIds();
        for(Long petId: petIds){
            pets.add( petService.getPet(petId) );
        }
        schedule.setPets(pets);

        List<Employee> employees = new ArrayList<>();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        for(Long empId: employeeIds){
            employees.add( userService.getEmployee(empId ) );
        }
        schedule.setEmployees(employees);

        scheduleService.saveSchedule(schedule);


        BeanUtils.copyProperties(schedule, scheduleDTO );

        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList =  scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for(Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }
}
