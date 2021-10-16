package ru.sfedu.market.api;

import ru.sfedu.market.bean.Customer;

public class BeanTest {
    public Customer createCustomer(){
        return new Customer(1L, "Ivan", 18);

    }

}
