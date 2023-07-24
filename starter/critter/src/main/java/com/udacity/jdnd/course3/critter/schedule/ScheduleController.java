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
            List<Long> petIds = getPetIdS(schedule);
            scheduleDTO.setPetIds(petIds);
            List<Long> employeeIds = getEmployeeIds(schedule);
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }


    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesForPet(petId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for(Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            List<Long> petIds = getPetIdS(schedule);
            scheduleDTO.setPetIds(petIds);
            List<Long> employeeIds = getEmployeeIds(schedule);
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for(Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            List<Long> petIds = getPetIdS(schedule);
            scheduleDTO.setPetIds(petIds);
            List<Long> employeeIds = getEmployeeIds(schedule);
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for(Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);
            List<Long> petIds = getPetIdS(schedule);
            scheduleDTO.setPetIds(petIds);
            List<Long> employeeIds = getEmployeeIds(schedule);
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTOList.add(scheduleDTO);
        }

        return scheduleDTOList;
    }

    @PostMapping("/{scheduleId}/pets")
    public PetDTO addPet(@PathVariable long scheduleId, @RequestBody PetDTO petDTO){
        Pet newPet = new Pet();
        BeanUtils.copyProperties(petDTO, newPet, "id");
        Pet newlyAddedPet = scheduleService.addPetToSchedule(scheduleId, newPet);
        BeanUtils.copyProperties(newlyAddedPet, petDTO);
        return petDTO;
    }

    @DeleteMapping
    public void deleteAllSchedules(){
        scheduleService.deleteAllSchedules();
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable long scheduleId){
        scheduleService.deleteSingleSchedule(scheduleId);
    }


    private static List<Long> getPetIdS(Schedule schedule) {
        List<Pet> pets =  schedule.getPets();
        List<Long> idList = new ArrayList<>();
        for(Pet pet:pets){
            idList.add(pet.getId());
        }
        return idList;
    }


    private List<Long> getEmployeeIds(Schedule schedule) {
        List<Employee> employees =  schedule.getEmployees();
        List<Long> idList = new ArrayList<>();
        for(Employee emp:employees){
            idList.add(emp.getId());
        }
        return idList;
    }
}
