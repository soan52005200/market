package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.Constants.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.utils.ConfigurationUtil;
import ru.sfedu.market.api.DataProviderCSV.*;
import ru.sfedu.market.utils.Result;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.SUCCESS;

class TestPrintConstantTest extends BeanTest{


    private final IDataProvider csv = new DataProviderCSV();

    @Test
    void createCsvCustomer() throws IOException{

        assertEquals(csv.createCustomer(readyCustomer()).getStatus(),SUCCESS);

    }



    @Test
    void readCsvCustomer() throws IOException {

        assertFalse(csv.getCustomerById(readyCustomer().getId()).isPresent());


    }

}