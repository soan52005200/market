package ru.sfedu.market;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static ru.sfedu.market.Constants.*;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        try {

            log.debug(System.getProperty(ENV_PROPERTIES));
            log.debug(System.getProperty(LOG4J2_PROPERTIES));
            

        } catch (Exception exception) {
            log.error(exception);
        }



        log.error("Hello world(error)");
        log.debug("Hello world(debug)");
        log.info("Hello world(info)");
    }

}
