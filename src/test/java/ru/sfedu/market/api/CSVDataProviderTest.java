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
    void crudCsvCustomerSuccess() throws IOException{

        assertEquals(csv.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(csv.readCustomerById(readyCustomer1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }



    @Test
    void crudCsvCustomerUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer3());

        assertEquals(csv.createCustomer(readyCustomer3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(csv.readCustomerById(readyCustomer1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(csv.deleteCustomerById(readyCustomer2().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/


        csv.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudCsvProductSuccess() throws IOException{
        assertEquals(csv.createProduct(readyProduct1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(csv.readProductById(readyProduct1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateProduct(readyProduct2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteProductById(readyProduct2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvProductUnsuccessful() throws IOException {
        csv.createProduct(readyProduct3());

        assertEquals(csv.createProduct(readyProduct3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(csv.readProductById(readyProduct1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateProduct(readyProduct1()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(csv.deleteProductById(readyProduct2().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/


        csv.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudCsvOrderSuccess() throws IOException{
        csv.createCustomer(readyCustomer1());
        csv.createProduct(readyProduct1());


        assertEquals(csv.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(csv.readOrderById(readyOrder1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        csv.deleteCustomerById(readyCustomer1().getId());
        csv.deleteProductById(readyProduct1().getId());

    }



    @Test
    void crudCsvOrderUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer1());
        csv.createProduct(readyProduct1());
        csv.createCustomer(readyCustomer3());
        csv.createProduct(readyProduct3());
        csv.createOrder(readyOrder3());



        assertEquals(csv.createOrder(readyOrder3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(csv.readOrderById(readyOrder1().getId()).isPresent());/** cRud  */
        assertEquals(csv.updateOrder(readyOrder1()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(csv.deleteOrderById(readyOrder1().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/



        csv.deleteOrderById((readyOrder3().getId()));
        csv.deleteCustomerById(readyCustomer1().getId());
        csv.deleteProductById(readyProduct1().getId());
        csv.deleteCustomerById(readyCustomer3().getId());
        csv.deleteProductById(readyProduct3().getId());

    }

}