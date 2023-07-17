package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
