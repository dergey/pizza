package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Address;
import com.sergey.zhuravlev.pizza.entity.Customer;
import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Page<Customer> list(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Customer getCustomer(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Customer createCustomer(String firstName, String lastName, Address address) {
        Customer customer = new Customer(null, firstName, lastName, address);
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName, Address address) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

}