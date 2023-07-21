package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer );
        userService.saveCustomer(customer);
        BeanUtils.copyProperties(customer, customerDTO );

        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = userService.getAllCustomers();
        System.out.println(customerList.get(0).getPets().size() + " controller");
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for(Customer customer: customerList){
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customer, customerDTO);
            List<Long> idList = getPetIds(customer);
            customerDTO.setPetIds(idList);
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO customerDTO = new CustomerDTO();
        Customer retrievedCustomer = userService.getCustomer(petId);
        BeanUtils.copyProperties(retrievedCustomer, customerDTO);
        List<Long> idList = getPetIds(retrievedCustomer);
        customerDTO.setPetIds(idList);
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        userService.saveEmployee(employee);
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee retrievedEmployee = userService.getEmployee(employeeId);
        BeanUtils.copyProperties(retrievedEmployee, employeeDTO);
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private static List<Long> getPetIds(Customer customer) {
        System.out.println(customer.getName());
        System.out.println(customer.getPets().size()+" 2");
        List<Pet> pets =  customer.getPets();
        List<Long> idList = new ArrayList<>();
        for(Pet pet:pets){
            idList.add(pet.getId());
        }
        return idList;
    }

}
