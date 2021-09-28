package ru.sfedu.market;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        logger.error("Hello world(error)");
        logger.debug("Hello world(debug)");
        logger.info("Hello world(info)");
    }

}
