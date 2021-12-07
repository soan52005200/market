package ru.sfedu.market.api;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.io.IOException;
public abstract class IDataProvider{
/**
 *
 *
    /**
     * Регистрация клиента в сервисе
     * @param customer
     * @return
     */

    public abstract Result<Customer> createCustomer(Customer customer) throws IOException;

    /**
     * Получить информацию о клиенте по его id
     * @param id
     * @return
     */

    public abstract Result<Customer> readCustomerById(Long id) throws IOException;

    /**
     * Изменение информации о клиенте
     * @param customer
     * @return
     */

    public abstract Result<Customer> updateCustomer(Customer customer) throws IOException;

    /**
     * Удаление клиента из сервиса
     * @param id
     * @return
     */

    public abstract Result<Customer> deleteCustomerById(Long id) throws IOException;

    /**
     * Регистрация продукта в сервисе
     * @param product
     * @return
     */

    public abstract Result<Eatable> createEatable(Eatable eatable) throws IOException;

    /**
     * Получить информацию о продукте по его id
     * @param id
     * @return
     */

    public abstract Result<Eatable> readEatableById(Long id) throws IOException;

    /**
     * Изменение информации о продукте
     * @param eatable
     * @return
     */

    public abstract Result<Eatable> updateEatable(Eatable eatable) throws IOException;

    /**
     * Удаление продукта из сервиса
     * @param id
     * @return
     */

    public abstract Result<Eatable> deleteEatableById(Long id) throws IOException;


    /**
     * Создать заказ
     * @param order
     * @return
     */

    public abstract Result<Order> createOrder(Order order) throws IOException;

    /**
     * Получить информацию о заказе
     * @param id
     * @return
     */

    public abstract Result<Order> readOrderById(Long id) throws IOException;

    /**
     * Изменить заказ
     * @param order
     * @return
     */
    public abstract Result<Order> updateOrder(Order order) throws IOException;

    /**
     * Закрыть заказ
     * @param id
     * @return
     */

    public abstract Result<Order> deleteOrderById(Long id) throws IOException;


    protected Document beanToMongo(Mongo mongo){
        Document doc = new Document();
        doc.put("className",mongo.getClassName().toString());
        doc.put("date",mongo.getDate().toString());
        doc.put("actor",mongo.getActor());
        doc.put("methodName",mongo.getMethodName().toString());
        doc.put("object",mongo.getObject().toString());
        doc.put("status",mongo.getStatus().toString());
        doc.put("description",mongo.getDescription());
        return doc;
    }

    public Result writeToMongo(Result result) throws IOException {
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



}
