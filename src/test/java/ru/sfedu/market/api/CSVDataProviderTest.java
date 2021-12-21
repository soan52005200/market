package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

import static ru.sfedu.market.utils.Status.*;

class CSVDataProviderTest extends BeanTest {


    private static final Logger log = LogManager.getLogger(CSVDataProviderTest.class.getName());

    private final IDataProvider csv = new DataProviderCSV();


    @Test
    void crudCsvCustomerSuccess() throws IOException{

        assertEquals(csv.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud */
        //assertEquals(csv.readCustomerById(readyCustomer1().getId()).getStatus(),SUCCESS);/** cRud  */
        //assertEquals(csv.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        //assertEquals(csv.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

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
    void crudCsvEatableSuccess() throws IOException{
        assertEquals(csv.createEatable(readyEatable1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(csv.readEatableById(readyEatable1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(csv.updateEatable(readyEatable2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteEatableById(readyEatable2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvEatableUnsuccessful() throws IOException {
        csv.createEatable(readyEatable3());

        assertEquals(csv.createEatable(readyEatable3()).getStatus(),ERROR);/** Crud  */
        assertEquals(csv.readEatableById(readyEatable1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(csv.updateEatable(readyEatable1()).getStatus(),ERROR);/** crUd  */
        assertEquals(csv.deleteEatableById(readyEatable2().getId()).getStatus(),ERROR); /** cruD*/


        csv.deleteEatableById(readyEatable3().getId());
    }
    @Test
    void crudCsvUneatableSuccess() throws IOException{
        assertEquals(csv.createUneatable(readyUneatable1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(csv.readUneatableById(readyUneatable1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(csv.updateUneatable(readyUneatable2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteUneatableById(readyUneatable2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudCsvUneatableUnsuccessful() throws IOException {
        csv.createUneatable(readyUneatable3());

        assertEquals(csv.createUneatable(readyUneatable3()).getStatus(),ERROR);/** Crud  */
        assertEquals(csv.readUneatableById(readyUneatable1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(csv.updateUneatable(readyUneatable1()).getStatus(),ERROR);/** crUd  */
        assertEquals(csv.deleteUneatableById(readyUneatable2().getId()).getStatus(),ERROR); /** cruD*/


        csv.deleteUneatableById(readyUneatable3().getId());
    }
    @Test
    void crudCsvOrderSuccess() throws IOException{
        csv.createCustomer(readyCustomer1());
        csv.createUneatable(readyUneatable1());
        csv.createEatable(readyEatable1());



        assertEquals(csv.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(csv.readOrderById(readyOrder1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(csv.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(csv.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        csv.deleteCustomerById(readyCustomer1().getId());
        csv.deleteEatableById(readyEatable1().getId());
        csv.deleteUneatableById(readyUneatable1().getId());

    }



    @Test
    void crudCsvOrderUnsuccessful() throws IOException {
        csv.createCustomer(readyCustomer1());
        csv.createEatable(readyEatable1());
        csv.createUneatable(readyUneatable1());
        csv.createCustomer(readyCustomer3());
        csv.createEatable(readyEatable3());
        csv.createUneatable(readyUneatable3());
        csv.createOrder(readyOrder3());



        assertEquals(csv.createOrder(readyOrder3()).getStatus(),ERROR);/** Crud  */
        assertEquals(csv.readOrderById(readyOrder1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(csv.updateOrder(readyOrder1()).getStatus(),ERROR);/** crUd  */
        assertEquals(csv.deleteOrderById(readyOrder1().getId()).getStatus(),ERROR); /** cruD*/



        csv.deleteOrderById((readyOrder3().getId()));
        csv.deleteCustomerById(readyCustomer1().getId());
        csv.deleteEatableById(readyEatable1().getId());
        csv.deleteUneatableById(readyUneatable1().getId());
        csv.deleteCustomerById(readyCustomer3().getId());
        csv.deleteEatableById(readyEatable3().getId());
        csv.deleteUneatableById(readyUneatable3().getId());

    }

}