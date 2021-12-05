package ru.sfedu.market.api;

import org.junit.jupiter.api.Test;
import ru.sfedu.market.utils.Status;


import java.io.IOException;
import java.util.Date;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;


public class JDBCDataProviderTest extends Mongo{

    private final IDataProvider jdbc = new DataProviderJDBC();



    @Test
    public void crudJDBCCustomerSuccess() throws IOException {


        assertEquals(writeToMongo(jdbc.createCustomer(readyCustomer1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(writeToMongo(jdbc.readCustomerById(readyCustomer1().getId())).getStatus(),SUCCESS); /** cRud  */
        assertEquals(writeToMongo(jdbc.updateCustomer(readyCustomer2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(writeToMongo(jdbc.deleteCustomerById(readyCustomer2().getId())).getStatus(),SUCCESS); /** cruD*/

    }
    @Test
    void crudJDBCCustomerUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer3());

        assertEquals(writeToMongo(jdbc.createCustomer(readyCustomer3())).getStatus(),ERROR);/** Crud  */
        assertEquals(writeToMongo(jdbc.readCustomerById(readyCustomer1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(writeToMongo(jdbc.updateCustomer(readyCustomer2())).getStatus(),ERROR);/** crUd  */
        assertEquals(writeToMongo(jdbc.deleteCustomerById(readyCustomer2().getId())).getStatus(),ERROR); /** cruD*/

        jdbc.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    public void crudJDBCProductSuccess() throws IOException {

        assertEquals(writeToMongo(jdbc.createProduct(readyProduct1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(writeToMongo(jdbc.readProductById(readyProduct1().getId())).getStatus(),SUCCESS); /** cRud  */
        assertEquals(writeToMongo(jdbc.updateProduct(readyProduct2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(writeToMongo(jdbc.deleteProductById(readyProduct2().getId())).getStatus(),SUCCESS); /** cruD*/

    }
    @Test
    void crudJDBCProductUnsuccessful() throws IOException {
        jdbc.createProduct(readyProduct3());

        assertEquals(writeToMongo(jdbc.createProduct(readyProduct3())).getStatus(),ERROR);/** Crud  */
        assertEquals(writeToMongo(jdbc.readProductById(readyProduct1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(writeToMongo(jdbc.updateProduct(readyProduct1())).getStatus(),ERROR);/** crUd  */
        assertEquals(writeToMongo(jdbc.deleteProductById(readyProduct2().getId())).getStatus(),ERROR); /** cruD*/


        jdbc.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudJDBCOrderSuccess() throws IOException{
        jdbc.createCustomer(readyCustomer1());
        jdbc.createProduct(readyProduct1());


        assertEquals(writeToMongo(jdbc.createOrder(readyOrder1())).getStatus(),SUCCESS);/** Crud  */
        assertEquals(writeToMongo(jdbc.readOrderById(readyOrder1().getId())).getStatus(),SUCCESS);/** cRud  */
        assertEquals(writeToMongo(jdbc.updateOrder(readyOrder2())).getStatus(),SUCCESS);/** crUd  */
        assertEquals(writeToMongo(jdbc.deleteOrderById(readyOrder2().getId())).getStatus(),SUCCESS); /** cruD*/

        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteProductById(readyProduct1().getId());

    }



    @Test
    void crudJDBCOrderUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer1());
        jdbc.createProduct(readyProduct1());
        jdbc.createCustomer(readyCustomer3());
        jdbc.createProduct(readyProduct3());
        jdbc.createOrder(readyOrder3());



        assertEquals(writeToMongo(jdbc.createOrder(readyOrder3())).getStatus(),ERROR);/** Crud  */
        assertEquals(writeToMongo(jdbc.readOrderById(readyOrder1().getId())).getStatus(),ERROR);/** cRud  */
        assertEquals(writeToMongo(jdbc.updateOrder(readyOrder1())).getStatus(),ERROR);/** crUd  */
        assertEquals(writeToMongo(jdbc.deleteOrderById(readyOrder2().getId())).getStatus(),ERROR); /** cruD*/



        jdbc.deleteOrderById((readyOrder3().getId()));
        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteProductById(readyProduct1().getId());
        jdbc.deleteCustomerById(readyCustomer3().getId());
        jdbc.deleteProductById(readyProduct3().getId());

    }
}