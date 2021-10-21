package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.Constants.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.Main;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.utils.ConfigurationUtil;
import ru.sfedu.market.api.DataProviderCSV.*;
import ru.sfedu.market.utils.Result;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.SUCCESS;
import static ru.sfedu.market.utils.Status.UNSUCCESSFUL;

class CSVDataProviderTest extends BeanTest{


    private static final Logger log = LogManager.getLogger(CSVDataProviderTest.class.getName());

    private final IDataProvider csv = new DataProviderCSV();

    @Test
    void crudCsvCustomerSeccess() throws IOException{

        assertEquals(csv.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(csv.getCustomerById(readyCustomer1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.removeCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvCustomerUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer3());

        assertEquals(csv.createCustomer(readyCustomer3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(csv.getCustomerById(readyCustomer1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(csv.removeCustomerById(readyCustomer2().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/


        csv.removeCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudCsvProductSuccess() throws IOException{
        assertEquals(csv.createProduct(readyProduct1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(csv.getProductById(readyProduct1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateProduct(readyProduct2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.removeProductById(readyProduct2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvProductUnsuccessful() throws IOException {
        csv.createProduct(readyProduct3());

        assertEquals(csv.createProduct(readyProduct3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(csv.getProductById(readyProduct1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateProduct(readyProduct1()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(csv.removeProductById(readyProduct2().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/


        csv.removeProductById(readyProduct3().getId());
    }

}