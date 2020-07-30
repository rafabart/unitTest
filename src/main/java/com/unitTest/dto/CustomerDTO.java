package com.unitTest.dto;

import com.unitTest.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    private String name;


    public CustomerDTO(final Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
    }
}
