package ru.sfedu.market;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static ru.sfedu.market.Constants.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        try {
            logger.debug(System.getProperty(ENV_PROPERTIES));


        } catch (Exception exception) {
            logger.error(exception);
        }



        logger.error("Hello world(error)");
        logger.debug("Hello world(debug)");
        logger.info("Hello world(info)");
    }

}
