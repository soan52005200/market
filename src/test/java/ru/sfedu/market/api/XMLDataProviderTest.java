package ru.sfedu.market.api;


import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;

public class XMLDataProviderTest extends BeanTest {
    private final IDataProvider xml = new DataProviderXML();


    @Test
    public void registerXmlCustomerSuccess() throws IOException {

        assertEquals(xml.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud  */
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
    void crudXmlProductSuccess() throws IOException{
        assertEquals(xml.createProduct(readyProduct1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(xml.readProductById(readyProduct1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(xml.updateProduct(readyProduct2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(xml.deleteProductById(readyProduct2().getId()).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudXmlProductUnsuccessful() throws IOException {
        xml.createProduct(readyProduct3());

        assertEquals(xml.createProduct(readyProduct3()).getStatus(),ERROR);/** Crud  */
        assertEquals(xml.readProductById(readyProduct1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(xml.updateProduct(readyProduct1()).getStatus(),ERROR);/** crUd  */
        assertEquals(xml.deleteProductById(readyProduct2().getId()).getStatus(),ERROR); /** cruD*/


        xml.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudXmlOrderSuccess() throws IOException{
        xml.createCustomer(readyCustomer1());
        xml.createProduct(readyProduct1());


        assertEquals(xml.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(xml.readOrderById(readyOrder1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(xml.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(xml.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        xml.deleteCustomerById(readyCustomer1().getId());
        xml.deleteProductById(readyProduct1().getId());

    }



    @Test
    void crudXmlOrderUnsuccessful() throws IOException {
        xml.createCustomer(readyCustomer1());
        xml.createProduct(readyProduct1());
        xml.createCustomer(readyCustomer3());
        xml.createProduct(readyProduct3());
        xml.createOrder(readyOrder3());



        assertEquals(xml.createOrder(readyOrder3()).getStatus(),ERROR);/** Crud  */
        assertEquals(xml.readOrderById(readyOrder1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(xml.updateOrder(readyOrder1()).getStatus(),ERROR);/** crUd  */
        assertEquals(xml.deleteOrderById(readyOrder1().getId()).getStatus(),ERROR); /** cruD*/



        xml.deleteOrderById((readyOrder3().getId()));
        xml.deleteCustomerById(readyCustomer1().getId());
        xml.deleteProductById(readyProduct1().getId());
        xml.deleteCustomerById(readyCustomer3().getId());
        xml.deleteProductById(readyProduct3().getId());

    }

}