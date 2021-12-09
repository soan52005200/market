package ru.sfedu.market.api;


import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;

public class XMLDataProviderTest extends BeanTest {
    private final IDataProvider xml = new DataProviderXML();


    @Test
    void crudXmlCustomerSuccess() throws IOException{

        assertEquals(xml.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud */
        assertEquals(xml.readCustomerById(readyCustomer1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(xml.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(xml.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }



    @Test
    void crudXmlCustomerUnsuccessful() throws IOException {
        xml.createCustomer(readyCustomer3());

        assertEquals(xml.createCustomer(readyCustomer3()).getStatus(),ERROR);/** Crud  */
        assertEquals(xml.readCustomerById(readyCustomer1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(xml.updateCustomer(readyCustomer2()).getStatus(),ERROR);/** crUd  */
        assertEquals(xml.deleteCustomerById(readyCustomer2().getId()).getStatus(),ERROR); /** cruD*/


        xml.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudXmlEatableSuccess() throws IOException{
        assertEquals(xml.createEatable(readyEatable1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(xml.readEatableById(readyEatable1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(xml.updateEatable(readyEatable2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(xml.deleteEatableById(readyEatable2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudXmlEatableUnsuccessful() throws IOException {
        xml.createEatable(readyEatable3());

        assertEquals(xml.createEatable(readyEatable3()).getStatus(),ERROR);/** Crud  */
        assertEquals(xml.readEatableById(readyEatable1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(xml.updateEatable(readyEatable1()).getStatus(),ERROR);/** crUd  */
        assertEquals(xml.deleteEatableById(readyEatable2().getId()).getStatus(),ERROR); /** cruD*/


        xml.deleteEatableById(readyEatable3().getId());
    }
    @Test
    void crudXmlUneatableSuccess() throws IOException{
        assertEquals(xml.createUneatable(readyUneatable1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(xml.readUneatableById(readyUneatable1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(xml.updateUneatable(readyUneatable2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(xml.deleteUneatableById(readyUneatable2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudXmlUneatableUnsuccessful() throws IOException {
        xml.createUneatable(readyUneatable3());

        assertEquals(xml.createUneatable(readyUneatable3()).getStatus(),ERROR);/** Crud  */
        assertEquals(xml.readUneatableById(readyUneatable1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(xml.updateUneatable(readyUneatable1()).getStatus(),ERROR);/** crUd  */
        assertEquals(xml.deleteUneatableById(readyUneatable2().getId()).getStatus(),ERROR); /** cruD*/


        xml.deleteUneatableById(readyUneatable3().getId());
    }
    @Test
    void crudXmlOrderSuccess() throws IOException{
        xml.createCustomer(readyCustomer1());
        xml.createUneatable(readyUneatable1());
        xml.createEatable(readyEatable1());



        assertEquals(xml.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(xml.readOrderById(readyOrder1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(xml.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(xml.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        xml.deleteCustomerById(readyCustomer1().getId());
        xml.deleteEatableById(readyEatable1().getId());
        xml.deleteUneatableById(readyUneatable1().getId());

    }



    @Test
    void crudXmlOrderUnsuccessful() throws IOException {
        xml.createCustomer(readyCustomer1());
        xml.createEatable(readyEatable1());
        xml.createUneatable(readyUneatable1());
        xml.createCustomer(readyCustomer3());
        xml.createEatable(readyEatable3());
        xml.createUneatable(readyUneatable3());
        xml.createOrder(readyOrder3());



        assertEquals(xml.createOrder(readyOrder3()).getStatus(),ERROR);/** Crud  */
        assertEquals(xml.readOrderById(readyOrder1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(xml.updateOrder(readyOrder1()).getStatus(),ERROR);/** crUd  */
        assertEquals(xml.deleteOrderById(readyOrder1().getId()).getStatus(),ERROR); /** cruD*/



        xml.deleteOrderById((readyOrder3().getId()));
        xml.deleteCustomerById(readyCustomer1().getId());
        xml.deleteEatableById(readyEatable1().getId());
        xml.deleteUneatableById(readyUneatable1().getId());
        xml.deleteCustomerById(readyCustomer3().getId());
        xml.deleteEatableById(readyEatable3().getId());
        xml.deleteUneatableById(readyUneatable3().getId());

    }

}