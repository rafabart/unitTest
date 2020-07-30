package com.unitTest.integration.junit5.webMvcTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitTest.entity.Customer;
import com.unitTest.resource.CustomerResource;
import com.unitTest.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerResource.class)
@DisplayName("Teste de integração domínio /customers")
public class CustomerResourceTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Teste de integração do POST de criação do cliente com sucesso")
    void shoud_create_new_customer_with_sucess() throws Exception {

        Customer customer = new Customer();
        customer.setName("Marge");

        when(customerService.create(any(Customer.class))).thenReturn(1L);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(content().string("1"));
    }
}
