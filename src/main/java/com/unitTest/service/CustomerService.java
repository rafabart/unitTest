package com.unitTest.service;

import com.unitTest.dto.CustomerDTO;
import com.unitTest.entity.Customer;
import com.unitTest.exception.CustomerNotFoundException;
import com.unitTest.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public Long create(final Customer customer) {
        return customerRepository.save(customer).getId();
    }


    public CustomerDTO findById(final Long id) {

        final Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException());

        return new CustomerDTO(customer);
    }
}