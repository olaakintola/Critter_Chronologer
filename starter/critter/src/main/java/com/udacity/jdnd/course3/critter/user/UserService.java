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

    public Long saveCustomer(Customer newCustomer){
        Customer customer = customerRepository.save(newCustomer);
        return customer.getId();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
