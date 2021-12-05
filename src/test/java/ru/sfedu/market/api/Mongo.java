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
import ru.sfedu.market.utils.Crud;
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


    private Class className;

    private Date date;

    private String actor;

    private Crud methodName;

    private Object object;

    private Status status;

    public Mongo(){ }

    public Mongo(Result result) {
        this.className = result.getBean().getClass();
        this.date = new Date();
        this.actor = "MAIN ACTOR";
        this.methodName = result.getMethodName();
        this.object = result.getBean();
        this.status = result.getStatus();
    }

    protected Document beanToMongo(Mongo mongo){
        Document doc = new Document();
        doc.put("className",mongo.getClassName().toString());
        doc.put("date",mongo.getDate().toString());
        doc.put("actor",mongo.getActor());
        doc.put("methodName",mongo.getMethodName().toString());
        doc.put("object",mongo.getObject().toString());
        doc.put("status",mongo.getStatus().toString());
        return doc;
    }

    public Result writeToMongo(Result result) throws IOException{
        Document bean = beanToMongo(new Mongo(result));
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                ClassModel.builder(Document.class).enableDiscriminator(true).build()
                        ).automatic(true)
                        .build()
                )
        );
        MongoCollection<Document> collection = new MongoClient()
                .getDatabase("data")
                .withCodecRegistry(codecRegistry).getCollection("history", Document.class);
        collection.insertOne(bean);



    return result;
    }



    public Class getClassName() {
        return this.className;
    }

    public Date getDate() {
        return date;
    }

    public String getActor() {
        return actor;
    }

    public Crud getMethodName() {
        return methodName;
    }

    public Object getObject() {
        return object;
    }

    public Status getStatus() {
        return status;
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

    @Override
    public String toString() {
        return "Mongo{" +
                "className=" + className +
                ", date=" + date +
                ", actor='" + actor + '\'' +
                ", methodName=" + methodName +
                ", object=" + object +
                ", status=" + status +
                '}';
    }
}
