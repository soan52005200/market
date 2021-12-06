package ru.sfedu.market.bean;

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

import static ru.sfedu.market.Constants.USER;
import static ru.sfedu.market.bean.ProductType.*;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;


public class Mongo {
    private static final Logger log = LogManager.getLogger(Mongo.class.getName());


    private Class className;

    private Date date;

    private String actor;

    private Crud methodName;

    private Object object;

    private Status status;


    public Mongo(Result result) throws IOException {
        this.className = result.getBean().getClass();
        this.date = new Date();
        this.actor = getConfigurationEntry(USER);
        this.methodName = result.getMethodName();
        this.object = result.getBean();
        this.status = result.getStatus();
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

