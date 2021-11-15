package ru.sfedu.market.api;

import org.junit.jupiter.api.Test;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.*;


public class JDBCDataProviderTest extends BeanTest{

    private final IDataProvider jdbc = new DataProviderJDBC();

    @Test
    public void crudJDBCCustomerSuccess() {


        assertEquals(jdbc.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(jdbc.readCustomerById(readyCustomer1().getId()).isPresent()); /** cRud  */
        assertEquals(jdbc.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }
    @Test
    void crudJDBCCustomerUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer3());

        assertEquals(jdbc.createCustomer(readyCustomer3()).getStatus(),ERROR);/** Crud  */
        assertFalse(jdbc.readCustomerById(readyCustomer1().getId()).isPresent());/** cRud  */
        System.out.println(jdbc.updateCustomer(readyCustomer2()));/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/


        jdbc.deleteCustomerById(readyCustomer3().getId());
    }
    @Test
    public void crudJDBCProductSuccess() {


        assertEquals(jdbc.createProduct(readyProduct1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(jdbc.readProductById(readyProduct1().getId()).isPresent()); /** cRud  */
        assertEquals(jdbc.updateProduct(readyProduct2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteProductById(readyProduct2().getId()).getStatus(),SUCCESS); /** cruD*/

    }
    @Test
    void crudCsvProductUnsuccessful() throws IOException {
        jdbc.createProduct(readyProduct3());

        assertEquals(jdbc.createProduct(readyProduct3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(jdbc.readProductById(readyProduct1().getId()).isPresent());/** cRud  */
        assertEquals(jdbc.updateProduct(readyProduct1()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(jdbc.deleteProductById(readyProduct2().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/


        jdbc.deleteProductById(readyProduct3().getId());
    }
    @Test
    void crudCsvOrderSuccess() throws IOException{
        jdbc.createCustomer(readyCustomer1());
        jdbc.createProduct(readyProduct1());


        assertEquals(jdbc.createOrder(readyOrder1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(jdbc.readOrderById(readyOrder1().getId()).isPresent());/** cRud  */
        assertEquals(jdbc.updateOrder(readyOrder2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteOrderById(readyOrder2().getId()).getStatus(),SUCCESS); /** cruD*/

        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteProductById(readyProduct1().getId());

    }



    @Test
    void crudCsvOrderUnsuccessful() throws IOException {
        jdbc.createCustomer(readyCustomer1());
        jdbc.createProduct(readyProduct1());
        jdbc.createCustomer(readyCustomer3());
        jdbc.createProduct(readyProduct3());
        jdbc.createOrder(readyOrder3());



        assertEquals(jdbc.createOrder(readyOrder3()).getStatus(),UNSUCCESSFUL);/** Crud  */
        assertFalse(jdbc.readOrderById(readyOrder1().getId()).isPresent());/** cRud  */
        assertEquals(jdbc.updateOrder(readyOrder1()).getStatus(),UNSUCCESSFUL);/** crUd  */
        assertEquals(jdbc.deleteOrderById(readyOrder1().getId()).getStatus(),UNSUCCESSFUL); /** cruD*/



        jdbc.deleteOrderById((readyOrder3().getId()));
        jdbc.deleteCustomerById(readyCustomer1().getId());
        jdbc.deleteProductById(readyProduct1().getId());
        jdbc.deleteCustomerById(readyCustomer3().getId());
        jdbc.deleteProductById(readyProduct3().getId());

    }
}