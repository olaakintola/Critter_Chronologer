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
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.getDaysAvailable().add(employeeDTO.getDate().getDayOfWeek());
        List<Employee> employeeList = userService.findEmployeesForService(employee);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Employee emp: employeeList){
            EmployeeDTO empDTO = new EmployeeDTO();
            BeanUtils.copyProperties(emp, empDTO);
            employeeDTOList.add(empDTO);
        }

        return employeeDTOList;
    }

    @DeleteMapping("/customer")
    public void deleteAllCustomers(){
        userService.deleteAllCustomers();
    }

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable long customerId){
        userService.deleteSingleCustomer(customerId);
    }

    @DeleteMapping("/employee")
    public void deleteAllEmployees(){
        userService.deleteAllEmployees();
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable long employeeId){
        userService.deleteSingleEmployee(employeeId);
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
