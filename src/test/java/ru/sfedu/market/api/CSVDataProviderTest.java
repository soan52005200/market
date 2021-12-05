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
import ru.sfedu.market.utils.Status;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.*;

class CSVDataProviderTest extends Mongo{


    private static final Logger log = LogManager.getLogger(CSVDataProviderTest.class.getName());

    private final IDataProvider csv = new DataProviderCSV();


    @Test
    void crudCsvCustomerSuccess() throws IOException{

        assertEquals(WriteToMongo(csv.createCustomer(readyCustomer1())).getStatus(),SUCCESS);/** Crud */
        assertEquals(WriteToMongo(csv.readCustomerById(readyCustomer1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(WriteToMongo(csv.updateCustomer(readyCustomer2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(WriteToMongo(csv.deleteCustomerById(readyCustomer2().getId())).getStatus(),SUCCESS); /** cruD*/

    }



    @Test
    void crudCsvCustomerUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer3());

        assertEquals(WriteToMongo(csv.createCustomer(readyCustomer3())).getStatus(),ERROR);/** Crud  */
        assertEquals(WriteToMongo(csv.readCustomerById(readyCustomer1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(WriteToMongo(csv.updateCustomer(readyCustomer2())).getStatus(),ERROR);/** crUd  */
        assertEquals(WriteToMongo(csv.deleteCustomerById(readyCustomer2().getId())).getStatus(),ERROR); /** cruD*/


        csv.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudCsvProductSuccess() throws IOException{
        assertEquals(WriteToMongo(csv.createProduct(readyProduct1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(WriteToMongo(csv.readProductById(readyProduct1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(WriteToMongo(csv.updateProduct(readyProduct2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(WriteToMongo(csv.deleteProductById(readyProduct2().getId())).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvProductUnsuccessful() throws IOException {
        csv.createProduct(readyProduct3());

        assertEquals(WriteToMongo(csv.createProduct(readyProduct3())).getStatus(),ERROR);/** Crud  */
        assertEquals(WriteToMongo(csv.readProductById(readyProduct1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(WriteToMongo(csv.updateProduct(readyProduct1())).getStatus(),ERROR);/** crUd  */
        assertEquals(WriteToMongo(csv.deleteProductById(readyProduct2().getId())).getStatus(),ERROR); /** cruD*/


        csv.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudCsvOrderSuccess() throws IOException{
        csv.createCustomer(readyCustomer1());
        csv.createProduct(readyProduct1());



        assertEquals(WriteToMongo(csv.createOrder(readyOrder1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(WriteToMongo(csv.readOrderById(readyOrder1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(WriteToMongo(csv.updateOrder(readyOrder2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(WriteToMongo(csv.deleteOrderById(readyOrder2().getId())).getStatus(),SUCCESS); /** cruD*/

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



        assertEquals(WriteToMongo(csv.createOrder(readyOrder3())).getStatus(),ERROR);/** Crud  */
        assertEquals(WriteToMongo(csv.readOrderById(readyOrder1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(WriteToMongo(csv.updateOrder(readyOrder1())).getStatus(),ERROR);/** crUd  */
        assertEquals(WriteToMongo(csv.deleteOrderById(readyOrder1().getId())).getStatus(),ERROR); /** cruD*/



        csv.deleteOrderById((readyOrder3().getId()));
        csv.deleteCustomerById(readyCustomer1().getId());
        csv.deleteProductById(readyProduct1().getId());
        csv.deleteCustomerById(readyCustomer3().getId());
        csv.deleteProductById(readyProduct3().getId());

    }

}