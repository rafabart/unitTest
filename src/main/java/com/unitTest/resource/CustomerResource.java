package com.unitTest.resource;

import com.unitTest.entity.Customer;
import com.unitTest.dto.CustomerDTO;
import com.unitTest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
public class CustomerResource {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO findById(@PathVariable("id") final Long id) {
        return customerService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody final Customer customer) {
        return customerService.create(customer);
    }
}
