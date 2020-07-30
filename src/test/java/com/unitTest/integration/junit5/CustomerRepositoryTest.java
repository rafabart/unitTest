package com.unitTest.integration.junit5;


import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.unitTest.entity.Customer;
import com.unitTest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static com.unitTest.templateLoader.CustomerTemplateLoader.VALID_CUSTOMER_NO_SAVED;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@DisplayName("Teste de integração com banco de dados - CustomerRepository")
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;


    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("com.unitTest.templateLoader");
    }


    @Test
    @DisplayName("Deve salvar o novo cliente com sucesso no banco de dados")
    void shoud_save_new_customer_with_success() {

        final Customer customer = Fixture.from(Customer.class).gimme(VALID_CUSTOMER_NO_SAVED);
        Customer customerFound = customerRepository.save(customer);

        assertThat(customerFound.getId()).isEqualTo(1L);
        assertThat(customerFound.getName()).isEqualTo(customer.getName());
    }


    @Test
    @DisplayName("Deve encontrar o usuário por id com sucesso")
    void shoud_find_customer_by_id_with_success() {

        final Customer customer = Fixture.from(Customer.class).gimme(VALID_CUSTOMER_NO_SAVED);

        customerRepository.save(customer);
        Customer customerFound = customerRepository.getOne(1L);

        assertThat(customerFound.getId()).isEqualTo(1L);
        assertThat(customerFound.getName()).isEqualTo(customer.getName());
    }
}
