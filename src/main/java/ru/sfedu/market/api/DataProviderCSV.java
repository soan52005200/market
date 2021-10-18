package ru.sfedu.market.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.Main;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;
import ru.sfedu.market.utils.Result;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.Constants.PRESENT_BEAN;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) {
        if (getCustomerById(customer.getId()).isEmpty()) {
            return append(customer, CSV_CUSTOMER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, customer, String.format(PRESENT_BEAN, customer.getId()));

    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return getAll(Customer.class, CSV_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();

    }

    @Override
    public Result<Customer> editCustomer(Customer customer) {
        return null;
    }

    @Override
    public Result<Void> removeCustomerById(Long id) {
        return null;
    }

    @Override
    public Result<Order> createOrder(Order order) {
        return null;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Result<Order> editOrder(Order order) {
        return null;
    }

    @Override
    public Result<Void> closeOrderById(Long id) {
        return null;
    }

    @Override
    public Result<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public Result<Product> editProduct(Product product) {
        return null;
    }

    @Override
    public Result<Void> removeProductById(Long id) {
        return null;
    }


    public <T> Result append(T bean,String key){
        Result<T> result;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(getConfigurationEntry(key), true));
                                                                                        /**В конце файла*/
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(bean);
            csvWriter.close();

            result = new Result<T>(SUCCESS, bean, String.format(PERSISTENCE_SUCCESS, bean.toString()));
        } catch (Exception e) {
            log.error(e);
            result = new Result<T>(ERROR, null, e.getMessage());
        }
        return result;


    }



    public <T> List<T> getAll(Class<T> clazz, String key){
        List<T> result;
        try {
            log.info(getConfigurationEntry(key));
            FileReader file = new FileReader(getConfigurationEntry(key));
            /**
              Разобраться!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            */
            List<T> csvToBean = new CsvToBeanBuilder<T>(file)
                    .withType(clazz)
                    .build()
                    .parse();

            file.close();

            result = csvToBean;
        } catch (Exception e) {
            log.error(e);
            result = new ArrayList<T>();
        }

        return result;
    }

}
