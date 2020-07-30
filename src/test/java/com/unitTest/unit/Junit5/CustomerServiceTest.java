package com.unitTest.unit.Junit5;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.unitTest.dto.CustomerDTO;
import com.unitTest.entity.Customer;
import com.unitTest.exception.CustomerNotFoundException;
import com.unitTest.repository.CustomerRepository;
import com.unitTest.service.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.unitTest.templateLoader.CustomerTemplateLoader.VALID_CUSTOMER;
import static com.unitTest.templateLoader.CustomerTemplateLoader.VALID_CUSTOMER_NO_SAVED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Teste unitário da classe CustomerService")
public class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;


    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("com.unitTest.templateLoader");
    }


    @Test
    @DisplayName("Teste unitário me criação do cliente com sucesso")
    void should_create_new_customer_with_success() {

        final Customer customer = Fixture.from(Customer.class).gimme(VALID_CUSTOMER_NO_SAVED);
        final Customer customerResponse = Fixture.from(Customer.class).gimme(VALID_CUSTOMER);

        when(customerRepository.save(any(Customer.class))).thenReturn(customerResponse);

        final Long result = customerService.create(customer);

        assertEquals(1L, result);
    }


    @Test
    @DisplayName("Teste unitário de busca do cliente por id com sucesso")
    void shoud_find_customer_by_id_with_sucess() {

        final Customer customerResponse = Fixture.from(Customer.class).gimme(VALID_CUSTOMER);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerResponse));

        final CustomerDTO result = customerService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Bart Simpsons", result.getName());
    }


    @Test
    @DisplayName("Teste unitário de busca do cliente por id inexistente")
    void shoud_not_find_customer_by_id() {

        Exception exception = assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1L));

        assertEquals(exception.getMessage(), "Cliente não encontrado!");
    }
}