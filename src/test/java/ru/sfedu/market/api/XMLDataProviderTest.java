package ru.sfedu.market.api;


import org.junit.jupiter.api.Test;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;


import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;

public class XMLDataProviderTest extends Mongo{
    private final IDataProvider xml = new DataProviderXML();


    @Test
    public void registerXmlCustomerSuccess() throws IOException {

        assertEquals(writeToMongo(xml.createCustomer(readyCustomer1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(writeToMongo(xml.readCustomerById(readyCustomer1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(writeToMongo(xml.updateCustomer(readyCustomer2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(writeToMongo(xml.deleteCustomerById(readyCustomer2().getId())).getStatus(),SUCCESS); /** cruD*/
    }
    @Test
    void crudXmlCustomerUnsuccessful() throws IOException {
        xml.createCustomer(readyCustomer3());

        assertEquals(writeToMongo(xml.createCustomer(readyCustomer3())).getStatus(),ERROR);/** Crud  */
        assertEquals(writeToMongo(xml.readCustomerById(readyCustomer1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(writeToMongo(xml.updateCustomer(readyCustomer2())).getStatus(),ERROR);/** crUd  */
        assertEquals(writeToMongo(xml.deleteCustomerById(readyCustomer2().getId())).getStatus(),ERROR); /** cruD*/


        xml.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    void crudXmlProductSuccess() throws IOException{
        assertEquals(writeToMongo(xml.createProduct(readyProduct1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(writeToMongo(xml.readProductById(readyProduct1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(writeToMongo(xml.updateProduct(readyProduct2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(writeToMongo(xml.deleteProductById(readyProduct2().getId())).getStatus(),SUCCESS); /** cruD*/
    }



    @Test
    void crudXmlProductUnsuccessful() throws IOException {
        xml.createProduct(readyProduct3());

        assertEquals(writeToMongo(xml.createProduct(readyProduct3())).getStatus(),ERROR);/** Crud  */
        assertEquals(writeToMongo(xml.readProductById(readyProduct1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(writeToMongo(xml.updateProduct(readyProduct1())).getStatus(),ERROR);/** crUd  */
        assertEquals(writeToMongo(xml.deleteProductById(readyProduct2().getId())).getStatus(),ERROR); /** cruD*/


        xml.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudXmlOrderSuccess() throws IOException{
        xml.createCustomer(readyCustomer1());
        xml.createProduct(readyProduct1());


        assertEquals(writeToMongo(xml.createOrder(readyOrder1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(writeToMongo(xml.readOrderById(readyOrder1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(writeToMongo(xml.updateOrder(readyOrder2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(writeToMongo(xml.deleteOrderById(readyOrder2().getId())).getStatus(),SUCCESS); /** cruD*/

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



        assertEquals(writeToMongo(xml.createOrder(readyOrder3())).getStatus(),ERROR);/** Crud  */
        assertEquals(writeToMongo(xml.readOrderById(readyOrder1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(writeToMongo(xml.updateOrder(readyOrder1())).getStatus(),ERROR);/** crUd  */
        assertEquals(writeToMongo(xml.deleteOrderById(readyOrder1().getId())).getStatus(),ERROR); /** cruD*/



        xml.deleteOrderById((readyOrder3().getId()));
        xml.deleteCustomerById(readyCustomer1().getId());
        xml.deleteProductById(readyProduct1().getId());
        xml.deleteCustomerById(readyCustomer3().getId());
        xml.deleteProductById(readyProduct3().getId());

    }

}