package ru.sfedu.market.utils;

import com.mongodb.Mongo;

public class Result<T> {
    private final Status status;
    private final T bean;
    private final Crud methodName;
    private final String log;

    public Result(Status status, T bean, Crud methodName, String log) {
        this.status = status;
        this.bean = bean;
        this.methodName = methodName;
        this.log = log;
    }

    public Status getStatus() {
        return status;
    }

    public T getBean() {
        return bean;
    }

    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", bean=" + bean +
                ", methodName=" + methodName +
                ", log='" + log + '\'' +
                '}';
    }
}
