package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.Constants.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.utils.ConfigurationUtil;
import ru.sfedu.market.api.DataProviderCSV.*;

import javax.xml.crypto.Data;
import java.io.IOException;

import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;

class TestPrintConstantTest {


    private final IDataProvider csv = new DataProviderCSV();


    @Test
    void createCsvCustomer() throws IOException{

        System.out.println(csv.createCustomer(new Customer(12L,"Andrew",28)));

    }



    @Test
    void readCsvCustomer() throws IOException {




    }

}