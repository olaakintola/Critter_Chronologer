package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
