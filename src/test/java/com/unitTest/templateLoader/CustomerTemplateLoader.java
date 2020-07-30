package com.unitTest.templateLoader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.unitTest.entity.Customer;

public class CustomerTemplateLoader implements TemplateLoader {

    public static final String VALID_CUSTOMER = "validCustomer";
    public static final String VALID_CUSTOMER_NO_SAVED = "validCustomerNoSaved";

    @Override
    public void load() {

        Fixture.of(Customer.class).addTemplate(VALID_CUSTOMER, new Rule() {{
            add("id", 1L);
            add("name", "Bart Simpsons");
        }});


        Fixture.of(Customer.class).addTemplate(VALID_CUSTOMER_NO_SAVED, new Rule() {{
            add("name", "Bart Simpsons");
        }});
    }
}
