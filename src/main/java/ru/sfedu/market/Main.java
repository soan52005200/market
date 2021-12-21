package ru.sfedu.market;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.DataProviderJDBC;
import ru.sfedu.market.api.DataProviderXML;
import ru.sfedu.market.api.IDataProvider;
import ru.sfedu.market.bean.*;

import java.io.IOException;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.Constants.CLI_EXCEPTION;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        try {
            IDataProvider provider = initDataSource(args[0]);
            log.info(selectBean(provider, args));
        } catch (Exception exception) {
            log.error(exception);
        }




    }
    protected static IDataProvider initDataSource(String str) {
        switch (str.toLowerCase()) {
            case CLI_CSV:
                return new DataProviderCSV();
            case CLI_XML:
                return new DataProviderXML();
            case CLI_JDBC:
                return new DataProviderJDBC();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, str));
        }
    }


    protected static String selectBean(IDataProvider provider, String[] s) throws IOException {
        switch (s[1].toLowerCase()) {
            case CLI_CUSTOMER:
                return customer(provider, s);
            case CLI_EATABLE:
                return eatable(provider, s);
            case CLI_UNEATABLE:
                return uneatable(provider, s);
            case CLI_ORDER:
                return order(provider, s);
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[1]));
        }
    }

    /**
     *  0     1      2   3   4  5
     * csv customer create 1 Ivan 18
     * csv customer upd  1 Petr 21
     * csv customer read 1
     * csv customer del  1
     * @param provider
     * @param s
     * @return
     */
    protected static String customer(IDataProvider provider, String[] s) throws IOException {
        switch (s[2].toLowerCase()) {
            case CLI_CREATE:
                return provider.createCustomer(new Customer(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).getLog();
            case CLI_UPD:
                return provider.updateCustomer(new Customer(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).getLog();
            case CLI_DEL:
                return provider.deleteCustomerById(Long.parseLong(s[3])).getLog();
            case CLI_READ:
                return provider.readCustomerById(Long.parseLong(s[3])).toString();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

    /**
     *  0     1     2   3  4   5
     * csv eatable save 1 Milk 6
     * csv eatable save 2 Milk 10
     * csv eatable get 1
     * csv eatable del 1
     * csv eatable del 2
     * @param provider
     * @param s
     * @return
     */
    protected static String eatable(IDataProvider provider, String[] s) throws IOException {
        switch (s[2].toLowerCase()) {
            case CLI_CREATE:
                return provider.createEatable(new Eatable(Long.parseLong(s[3]), s[4],ProductType.valueOf(s[5].toUpperCase()),Integer.parseInt(s[6]))).getLog();
            case CLI_READ:
                return provider.readEatableById(Long.parseLong(s[3])).getBean().toString();
            case CLI_UPD:
                return provider.updateEatable(new Eatable(Long.parseLong(s[3]), s[4],ProductType.valueOf(s[5].toLowerCase()),Integer.parseInt(s[6]))).toString();
            case CLI_DEL:
                return provider.deleteEatableById(Long.parseLong(s[3])).getLog();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

    /**
     *  0     1         2   3       4           5       6
     * csv uneatable create 1 Mobile_phone ELECTRONICS 365
     * csv uneatable get 1
     * csv uneatable del 1
     * @param provider
     * @param s
     * @return
     */
    protected static String uneatable(IDataProvider provider, String[] s) throws IOException {
        switch (s[2].toLowerCase()) {
            case CLI_CREATE:
                return provider.createUneatable(new Uneatable(Long.parseLong(s[3]), s[4],ProductType.valueOf(s[5].toUpperCase()),Integer.parseInt(s[6]))).getLog();
            case CLI_READ:
                return provider.readUneatableById(Long.parseLong(s[3])).getBean().toString();
            case CLI_UPD:
                return provider.updateUneatable(new Uneatable(Long.parseLong(s[3]), s[4],ProductType.valueOf(s[5].toUpperCase()),Integer.parseInt(s[6]))).toString();
            case CLI_DEL:
                return provider.deleteUneatableById(Long.parseLong(s[3])).getLog();

            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

    /** 0    1     2    3 4 5 6
     *csv customer create 1 Ivan 18
     *csv eatable save 1 Milk 6
     *csv uneatable create 1 Mobile_phone ELECTRONICS 365


     * csv order create 1 1 1 1


     * @param provider
     * @param s
     * @return
     */
    protected static String order(IDataProvider provider, String[] s) throws IOException {
        switch (s[2].toLowerCase()) {
            case CLI_CREATE:
                return provider.createOrder(new Order(Long.parseLong(s[3]),provider.readEatableById(Long.parseLong(s[4])).getBean(),provider.readUneatableById(Long.parseLong(s[5])).getBean(),provider.readCustomerById(Long.parseLong(s[6])).getBean())).toString();
                case CLI_DEL:
                return provider.deleteOrderById(Long.parseLong(s[3])).getLog();
            case CLI_READ:
                return provider.readOrderById(Long.parseLong(s[3])).toString();
            case CLI_UPD:
                return provider.updateOrder(new Order(Long.parseLong(s[3]),provider.readEatableById(Long.parseLong(s[4])).getBean(),provider.readUneatableById(Long.parseLong(s[5])).getBean(),provider.readCustomerById(Long.parseLong(s[6])).getBean())).toString();

            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

}
