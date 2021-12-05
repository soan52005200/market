package ru.sfedu.market.api;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ru.sfedu.market.bean.ProductType.*;


public class Mongo {
    private static final Logger log = LogManager.getLogger(Mongo.class.getName());

    private Long id;

    private Class className;

    private Date date;

    private String actor;

    private String methodName;

    private Map<String,Object> object;

    private Status status;




    public Result WriteToMongo(Result result) throws IOException{

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                ClassModel.builder(Mongo.class).enableDiscriminator(true).build()
                        ).automatic(true)
                        .build()
                )
        );
        MongoCollection<Mongo> collection = new MongoClient()
                .getDatabase("data")
                .withCodecRegistry(codecRegistry).getCollection("data", Mongo.class);

        System.out.println("Connected to database!");


    return result;
    }


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
