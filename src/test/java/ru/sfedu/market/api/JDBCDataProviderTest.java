package ru.sfedu.market.api;

import org.junit.jupiter.api.Test;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;


public class JDBCDataProviderTest extends Mongo{

    private final IDataProvider jdbc = new DataProviderJDBC();

    @Test
    public void crudJDBCCustomerSuccess() {


        assertEquals(jdbc.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(jdbc.readCustomerById(readyCustomer1().getId()).getStatus(),SUCCESS); /** cRud  */
        assertEquals(jdbc.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }
    @Test
    void crudJDBCCustomerUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer3());

        assertEquals(jdbc.createCustomer(readyCustomer3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readCustomerById(readyCustomer1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateCustomer(readyCustomer2()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),ERROR); /** cruD*/

        jdbc.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    public void crudJDBCProductSuccess() {

        assertEquals(jdbc.createProduct(readyProduct1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(jdbc.readProductById(readyProduct1().getId()).getStatus(),SUCCESS); /** cRud  */
        assertEquals(jdbc.updateProduct(readyProduct2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteProductById(readyProduct2().getId()).getStatus(),SUCCESS); /** cruD*/

    }
    @Test
    void crudJDBCProductUnsuccessful() throws IOException {
        jdbc.createProduct(readyProduct3());

        assertEquals(jdbc.createProduct(readyProduct3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readProductById(readyProduct1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateProduct(readyProduct1()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteProductById(readyProduct2().getId()).getStatus(),ERROR); /** cruD*/


        jdbc.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudJDBCOrderSuccess() throws IOException{
        jdbc.createCustomer(readyCustomer1());
        jdbc.createProduct(readyProduct1());


        assertEquals(jdbc.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertEquals(jdbc.readOrderById(readyOrder1().getId()).getStatus(),SUCCESS);/** cRud  */
        assertEquals(jdbc.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

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



        assertEquals(jdbc.createOrder(readyOrder3()).getStatus(),ERROR);/** Crud  */
        assertEquals(jdbc.readOrderById(readyOrder1().getId()).getStatus(),ERROR);/** cRud  */
        assertEquals(jdbc.updateOrder(readyOrder1()).getStatus(),ERROR);/** crUd  */
        assertEquals(jdbc.deleteOrderById(readyOrder1().getId()).getStatus(),ERROR); /** cruD*/



        jdbc.deleteOrderById((readyOrder3().getId()));
        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteProductById(readyProduct1().getId());
        jdbc.deleteCustomerById(readyCustomer3().getId());
        jdbc.deleteProductById(readyProduct3().getId());

    }
}