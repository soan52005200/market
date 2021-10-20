package ru.sfedu.market.api;

import ru.sfedu.market.bean.Customer;

public class BeanTest {
    public Customer readyCustomer1(){
        return new Customer(1L, "Ivan", 18);

    }
    public Customer readyCustomer2(){
        return new Customer(1L, "Yasha", 20);

    }
    public Customer readyCustomer3(){
        return new Customer(2L, "Dima", 45);

    }
    public Customer readyOrder(){
        return new Customer(1L, "Ivan", 18);

    }
    public Customer readyPruduct(){
        return new Customer(1L, "Ivan", 18);

    }

}
