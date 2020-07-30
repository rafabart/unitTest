package com.unitTest.integration.junit5.autoConfigureMockMvc;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitTest.dto.CustomerDTO;
import com.unitTest.entity.Customer;
import com.unitTest.service.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.unitTest.templateLoader.CustomerDTOTemplateLoader.VALID_CUSTOMERDTO;
import static com.unitTest.templateLoader.CustomerTemplateLoader.VALID_CUSTOMER_NO_SAVED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste de integração domínio /customers")
public class CustomerResourceTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates("com.unitTest.templateLoader");
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Teste de integração do método create da classe controller usando MockMvc
    @Test
    @DisplayName("Teste de integração do POST de criação do cliente com sucesso")
    void shoud_create_new_customer_with_sucess() throws Exception {

        final Customer customer = Fixture.from(Customer.class).gimme(VALID_CUSTOMER_NO_SAVED);

        when(customerService.create(any(Customer.class))).thenReturn(1L);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(content().string("1"));

    }


    @Test
    @DisplayName("Teste de integração do GET de busca do cliente por id com sucesso")
    void shoud_find_customer_by_id_with_sucess() throws Exception {

        final CustomerDTO customerDTO = Fixture.from(CustomerDTO.class).gimme(VALID_CUSTOMERDTO);

        when(customerService.findById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Bart Simpsons"))
                .andExpect(jsonPath("$.id").value(1));

    }
}