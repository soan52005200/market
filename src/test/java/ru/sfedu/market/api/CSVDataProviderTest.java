package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.bean.Mongo;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

import static ru.sfedu.market.utils.Status.*;

class CSVDataProviderTest extends BeanTest {


    private static final Logger log = LogManager.getLogger(CSVDataProviderTest.class.getName());

    private final IDataProvider csv = new DataProviderCSV();


    @Test
    void crudCsvCustomerSuccess() throws IOException{

        assertEquals(csv.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud */
        assertEquals(csv.readCustomerById(readyCustomer1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }



    @Test
    void crudCsvCustomerUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer3());

        assertEquals(csv.createCustomer(readyCustomer3()).getStatus(),ERROR);/** Crud  */
        assertEquals(csv.readCustomerById(readyCustomer1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),ERROR);/** crUd  */
        assertEquals(csv.deleteCustomerById(readyCustomer2().getId()).getStatus(),ERROR); /** cruD*/


        csv.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudCsvProductSuccess() throws IOException{
        assertEquals(csv.createProduct(readyProduct1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(csv.readProductById(readyProduct1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(csv.updateProduct(readyProduct2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteProductById(readyProduct2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvProductUnsuccessful() throws IOException {
        csv.createProduct(readyProduct3());

        assertEquals(csv.createProduct(readyProduct3()).getStatus(),ERROR);/** Crud  */
        assertEquals(csv.readProductById(readyProduct1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(csv.updateProduct(readyProduct1()).getStatus(),ERROR);/** crUd  */
        assertEquals(csv.deleteProductById(readyProduct2().getId()).getStatus(),ERROR); /** cruD*/


        csv.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudCsvOrderSuccess() throws IOException{
        csv.createCustomer(readyCustomer1());
        csv.createProduct(readyProduct1());



        assertEquals(csv.createOrder(readyOrder4()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(csv.readOrderById(readyOrder1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(csv.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        csv.deleteCustomerById(readyCustomer4().getId());
        csv.deleteProductById(readyProduct3().getId());

    }



    @Test
    void crudCsvOrderUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer1());
        csv.createProduct(readyProduct1());
        csv.createCustomer(readyCustomer3());
        csv.createProduct(readyProduct3());
        csv.createOrder(readyOrder3());



        assertEquals(csv.createOrder(readyOrder3()).getStatus(),ERROR);/** Crud  */
        assertEquals(csv.readOrderById(readyOrder1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(csv.updateOrder(readyOrder1()).getStatus(),ERROR);/** crUd  */
        assertEquals(csv.deleteOrderById(readyOrder1().getId()).getStatus(),ERROR); /** cruD*/



        csv.deleteOrderById((readyOrder3().getId()));
        csv.deleteCustomerById(readyCustomer1().getId());
        csv.deleteProductById(readyProduct1().getId());
        csv.deleteCustomerById(readyCustomer3().getId());
        csv.deleteProductById(readyProduct3().getId());

    }

}