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

import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;

class TestPrintConstantTest extends BeanTest{


    private final IDataProvider csv = new DataProviderCSV();

    @Test
    void createCsvCustomer() throws IOException{

        System.out.println(csv.createCustomer(readyCustomer()));

    }



    @Test
    void readCsvCustomer() throws IOException {

        System.out.println(csv.getCustomerById(readyCustomer().getId()));


    }

}