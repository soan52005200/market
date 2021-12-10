package ru.sfedu.market.api;

import org.junit.jupiter.api.Test;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;


public class JDBCDataProviderTest extends BeanTest {

    private final IDataProvider jdbc = new DataProviderJDBC();



    @Test
    void crudJdbcCustomerSuccess() throws IOException{

        assertEquals(jdbc.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud */
        assertEquals(jdbc.readCustomerById(readyCustomer1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(jdbc.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }



    @Test
    void crudJdbcCustomerUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer3());

        assertEquals(jdbc.createCustomer(readyCustomer3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readCustomerById(readyCustomer1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateCustomer(readyCustomer2()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),ERROR); /** cruD*/


        jdbc.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudJdbcEatableSuccess() throws IOException{

        assertEquals(jdbc.createEatable(readyEatable1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(jdbc.readEatableById(readyEatable1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(jdbc.updateEatable(readyEatable2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteEatableById(readyEatable2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudJdbcEatableUnsuccessful() throws IOException {
        jdbc.createEatable(readyEatable3());

        assertEquals(jdbc.createEatable(readyEatable3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readEatableById(readyEatable1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateEatable(readyEatable1()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteEatableById(readyEatable2().getId()).getStatus(),ERROR); /** cruD*/


        jdbc.deleteEatableById(readyEatable3().getId());
    }
    @Test
    void crudJdbcUneatableSuccess() throws IOException{
        assertEquals(jdbc.createUneatable(readyUneatable1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(jdbc.readUneatableById(readyUneatable1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(jdbc.updateUneatable(readyUneatable2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteUneatableById(readyUneatable2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudJdbcUneatableUnsuccessful() throws IOException {
        jdbc.createUneatable(readyUneatable3());

        assertEquals(jdbc.createUneatable(readyUneatable3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readUneatableById(readyUneatable1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateUneatable(readyUneatable1()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteUneatableById(readyUneatable2().getId()).getStatus(),ERROR); /** cruD*/


        jdbc.deleteUneatableById(readyUneatable3().getId());
    }
    @Test
    void crudJdbcOrderSuccess() throws IOException{
        jdbc.createCustomer(readyCustomer1());
        jdbc.createUneatable(readyUneatable1());
        jdbc.createEatable(readyEatable1());



        assertEquals(jdbc.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(jdbc.readOrderById(readyOrder1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(jdbc.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteEatableById(readyEatable1().getId());
        jdbc.deleteUneatableById(readyUneatable1().getId());

    }



    @Test
    void crudJdbcOrderUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer1());
        jdbc.createEatable(readyEatable1());
        jdbc.createUneatable(readyUneatable1());
        jdbc.createCustomer(readyCustomer3());
        jdbc.createEatable(readyEatable3());
        jdbc.createUneatable(readyUneatable3());
        jdbc.createOrder(readyOrder3());



        assertEquals(jdbc.createOrder(readyOrder3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readOrderById(readyOrder1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateOrder(readyOrder1()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteOrderById(readyOrder1().getId()).getStatus(),ERROR); /** cruD*/



        jdbc.deleteOrderById((readyOrder3().getId()));
        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteEatableById(readyEatable1().getId());
        jdbc.deleteUneatableById(readyUneatable1().getId());
        jdbc.deleteCustomerById(readyCustomer3().getId());
        jdbc.deleteEatableById(readyEatable3().getId());
        jdbc.deleteUneatableById(readyUneatable3().getId());

    }
}