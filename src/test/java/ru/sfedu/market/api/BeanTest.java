package ru.sfedu.market.api;



import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;
import ru.sfedu.market.utils.Crud;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;

import java.io.IOException;
import java.util.Date;

import static ru.sfedu.market.bean.ProductType.*;


public class BeanTest {





    /**Передача данных для mongo осуществляется через методы создания объектов*/



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
