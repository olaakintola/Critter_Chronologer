package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Long saveCustomer(Customer customer){
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer.getId();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Long saveEmployee(Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        return newEmployee.getId();
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException());
    }

    public Customer getCustomer(long petId) {
        return customerRepository.findByPetsId(petId).get(0);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException());
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Employee employee) {
        // need to create a method in the repository to get the needed employee list in one go
        List<Employee> employees = employeeRepository.findBySkillsInAndDaysAvailableIn(employee.getSkills() , employee.getDaysAvailable());
        Set<Employee> employeeSet = new HashSet<>();
        for(Employee e: employees){
            if(e.getSkills().containsAll( employee.getSkills() ) && e.getDaysAvailable().containsAll(employee.getDaysAvailable() ) ){
                employeeSet.add(e);
            }
        }

        List<Employee> employeeList = new ArrayList<>(employeeSet);
        return employeeList;

    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public void deleteSingleCustomer(long customerId) {
        customerRepository.deleteById(customerId);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    public void deleteSingleEmployee(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void updateSingleCustomer(Customer customer, long customerId) {
        Customer retrievedCustomer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException());
        retrievedCustomer.setPhoneNumber(customer.getPhoneNumber() );
        retrievedCustomer.setNotes(customer.getNotes() );
        customerRepository.save(retrievedCustomer);
    }
}
