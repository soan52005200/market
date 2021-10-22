package ru.sfedu.market.api;

import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;

import static ru.sfedu.market.bean.ProductType.*;

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
    public Product readyProduct1(){
        return new Product(1L, "Moloko_Vkusnoteevo", MILK);

    }
    public Product readyProduct2(){
        return new Product(1L, "Borodinskiy", BAKERY);

    }
    public Product readyProduct3(){
        return new Product(2L, "Stolichnaya", ALCOHOL);

    }
    public Order readyOrder1(){
        return new Order(1L, readyProduct1(), readyCustomer1());

    }
    public Order readyOrder2(){
        return new Order(1L, readyProduct2(), readyCustomer2());

    }
    public Order readyOrder3(){
        return new Order(2L, readyProduct3(), readyCustomer3());

    }

}
