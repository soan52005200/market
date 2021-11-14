package ru.sfedu.market.api;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static ru.sfedu.market.utils.Status.SUCCESS;


public class JDBCDataProviderTest extends BeanTest{

    private final IDataProvider jdbc = new DataProviderJDBC();

    @Test
    public void crudJDBCCustomerSuccess() {


        assertEquals(jdbc.createCustomer(readyCustomer1()).getStatus(),SUCCESS);/** Crud  */
        assertTrue(jdbc.readCustomerById(readyCustomer1().getId()).isPresent()); /** cRud  */
        assertEquals(jdbc.updateCustomer(readyCustomer2()).getStatus(),SUCCESS);/** crUd  */
        assertEquals(jdbc.deleteCustomerById(readyCustomer2().getId()).getStatus(),SUCCESS); /** cruD*/

    }
}