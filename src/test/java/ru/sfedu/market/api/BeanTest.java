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
import ru.sfedu.market.bean.*;
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
    public Customer readyCustomer4(){

        return new Customer(1L, "Ivan", 17);

    }
    public Eatable readyEatable1(){
        return new Eatable(1L, "Moloko_Vkusnoteevo", MILK,10);

    }
    public Eatable readyEatable2(){
        return new Eatable(1L, "Borodinskiy", BAKERY,5);

    }
    public Eatable readyEatable3(){
        return new Eatable(2L, "Stolichnaya", ALCOHOL,365);

    }
    public Uneatable readyUneatable1(){
        return new Uneatable(1L, "Moloko_Vkusnoteevo", MILK,10);

    }
    public Uneatable readyUneatable2(){
        return new Uneatable(1L, "Borodinskiy", BAKERY,5);

    }
    public Uneatable readyUneatable3(){
        return new Uneatable(2L, "Stolichnaya", ALCOHOL,365);

    }
    public Order readyOrder1(){
        return new Order(1L, readyEatable1(),readyUneatable1(),readyCustomer1());

    }
    public Order readyOrder2(){
        return new Order(1L, readyEatable2(),readyUneatable2(), readyCustomer2());

    }
    public Order readyOrder3(){
        return new Order(2L, readyEatable3(),readyUneatable3(), readyCustomer3());

    }
    public Order readyOrder4(){
        return new Order(6L, readyEatable3(),readyUneatable3(), readyCustomer4());

    }



}
