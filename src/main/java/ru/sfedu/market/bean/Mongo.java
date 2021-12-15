package ru.sfedu.market.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.utils.Crud;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;
import java.io.IOException;
import java.util.Date;


import static ru.sfedu.market.Constants.USER;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;


public class Mongo {
    private static final Logger log = LogManager.getLogger(Mongo.class.getName());


    private Class className;

    private Date date;

    private String actor;

    private Crud methodName;

    private Object object;

    private Status status;

    private String description;


    public Mongo(Result result) throws IOException {
        this.className = result.getBean().getClass();
        this.date = new Date();
        this.actor = getConfigurationEntry(USER);
        this.methodName = result.getMethodName();
        this.object = result.getBean();
        this.status = result.getStatus();
        this.description = result.getLog();
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

    public String getDescription() {
        return description;
    }


}

