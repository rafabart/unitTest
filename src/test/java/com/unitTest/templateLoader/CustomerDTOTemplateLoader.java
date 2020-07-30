package com.unitTest.templateLoader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.unitTest.dto.CustomerDTO;

public class CustomerDTOTemplateLoader implements TemplateLoader {

    public static final String VALID_CUSTOMERDTO = "validCustomerDto";

    @Override
    public void load() {

        Fixture.of(CustomerDTO.class).addTemplate(VALID_CUSTOMERDTO, new Rule() {{
            add("id", 1L);
            add("name", "Bart Simpsons");
        }});
    }
}
