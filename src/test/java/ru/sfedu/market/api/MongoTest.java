package ru.sfedu.market.api;



import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.utils.Status;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


public class MongoTest {

    private Long id;

    private Class className;

    private Date date;

    private String actor;

    private String methodName;

    private Map<String,Object> object;

    private Status status;
    @Test
    void mongoStart() throws IOException{
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://84.47.149.214:27017"));
        DB database = mongoClient.getDB("History");
    }
}
