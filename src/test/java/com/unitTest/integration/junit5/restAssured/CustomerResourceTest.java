package com.unitTest.integration.junit5.restAssured;


import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.unitTest.dto.CustomerDTO;
import com.unitTest.entity.Customer;
import com.unitTest.service.CustomerService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import static com.unitTest.templateLoader.CustomerDTOTemplateLoader.VALID_CUSTOMERDTO;
import static com.unitTest.templateLoader.CustomerTemplateLoader.VALID_CUSTOMER_NO_SAVED;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Teste de integração domínio /customers")
public class CustomerResourceTest {

    @MockBean
    private CustomerService customerService;

    @LocalServerPort
    private int port;


    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("com.unitTest.templateLoader");
    }


    @Test
    @DisplayName("Teste de integração do POST de criação do cliente com sucesso")
    void shoud_create_new_customer_with_sucess() {

        final Customer customer = Fixture.from(Customer.class).gimme(VALID_CUSTOMER_NO_SAVED);

        when(customerService.create(any(Customer.class))).thenReturn(1L);

        given().port(port)
                .log().all()
                .contentType(ContentType.JSON)
                .body(customer)
                .when()
                .post("/customers")
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .body(containsString("1"));
    }


    @Test
    @DisplayName("Teste de integração do GET de busca do cliente por id com sucesso")
    void shoud_find_customer_by_id_with_sucess() {

        final CustomerDTO customerDTO = Fixture.from(CustomerDTO.class).gimme(VALID_CUSTOMERDTO);

        when(customerService.findById(anyLong())).thenReturn(customerDTO);

        given().port(port)
                .log().all()
                .when()
                .get("/customers/1")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .assertThat()
                .body("id", is(1))
                .body("name", is("Bart Simpsons"));
    }
}
