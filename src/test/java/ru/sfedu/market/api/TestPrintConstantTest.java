package ru.sfedu.market.api;

import ru.sfedu.market.Constants.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.utils.ConfigurationUtil;
import ru.sfedu.market.api.DataProviderCSV.*;
import java.io.IOException;

import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;

class TestPrintConstantTest {
    @Test
    void get() throws IOException {
        DataProviderCSV dataProviderCSV = new DataProviderCSV();
        System.out.println(dataProviderCSV.getAll(Customer.class,getConfigurationEntry("csvCustomer")));



    }
}