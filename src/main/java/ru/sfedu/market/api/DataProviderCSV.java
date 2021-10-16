package ru.sfedu.market.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
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

import static ru.sfedu.market.Constants.PERSISTENCE_SUCCESS;
import static ru.sfedu.market.Constants.REMOVE_SUCCESS;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);


    @Override
    public Result<Customer> register(Customer customer) {

        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.empty();
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
    public Result<Product> register(Product product) {
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

    private static final Logger logs = LogManager.getLogger(Main.class.getName());

    public <T> List<T> getAll(Class<T> clazz, String key){
        List<T> result;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(getConfigurationEntry(key)));
            /**

              Разобраться!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            */
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(clazz).build();

            logs.debug(csvToBean);

            /** CSVToBeanBuilder - парсинг csv с помощью аннотаций


            List<T> collection = csvToBean.parse();
            csvReader.close();

            result = collection;*/
        } catch (Exception e) {
            log.error(e);
            result = new ArrayList<T>();
        }
        return null;
        /**return result;*/
    }

}
