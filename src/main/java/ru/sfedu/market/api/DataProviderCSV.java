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
import static ru.sfedu.market.Constants.CSV_ORDER_KEY;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) {
        if (getCustomerById(customer.getId()).isEmpty()) {
            return create(customer, CSV_CUSTOMER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, customer, String.format(PRESENT_BEAN, customer.getId()));

    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return getAll(Customer.class, CSV_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();

    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, customer.getId()));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Void> refresh = remove(customers, CSV_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, customer, String.format(UPDATE_SUCCESS, customer.toString()));
        } else {
            return new Result<>(ERROR, customer, refresh.getLog());
        }
    }

    @Override
    public Result<Void> removeCustomerById(Long id) {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        /**removeOrderByCustomerCascade(id);   Удаление заказа(include)*/
        customers.removeIf(o -> o.getId().equals(id));
        return remove(customers, CSV_CUSTOMER_KEY);
    }
    @Override
    public Result<Product> createProduct(Product product) {
        if (getProductById(product.getId()).isEmpty()) {
            return create(product, CSV_PRODUCT_KEY);
        }
        return new Result<>(UNSUCCESSFUL, product, String.format(PRESENT_BEAN, product.getId()));


    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return getAll(Product.class, CSV_PRODUCT_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Result<Product> updateProduct(Product product) {
        List<Product> products = getAll(Product.class, CSV_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(product.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, product.getId()));
        }
        products.removeIf(o -> o.getId().equals(product.getId()));
        products.add(product);
        Result<Void> refresh = remove(products, CSV_PRODUCT_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, product, String.format(UPDATE_SUCCESS, product.toString()));
        } else {
            return new Result<>(ERROR, product, refresh.getLog());
        }
    }

    @Override
    public Result<Void> removeProductById(Long id) {

        List<Product> products = getAll(Product.class, CSV_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        /**Можно реализовать удаление заказов с этими товарами.*/
        products.removeIf(o -> o.getId().equals(id));
        return remove(products, CSV_PRODUCT_KEY);
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





    public <T> Result create(T bean,String key){
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
            CSVReader reader =new CSVReader(new FileReader(getConfigurationEntry(key)));
            /**
              Разобраться!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            */
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .build();
            List<T> listUser = csvToBean.parse();

            reader.close();

            result = listUser;
        } catch (Exception e) {
            log.error(e);
            result = new ArrayList<T>();
        }

        return result;
    }


    private <T> Result<Void> remove(List<T> beans, String key) {
        Result<Void> result;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(getConfigurationEntry(key), false));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(beans);
            csvWriter.close();

            result = new Result<Void>(SUCCESS, null, REMOVE_SUCCESS);
        } catch (Exception e) {
            log.error(e);
            result = new Result<Void>(ERROR, null, e.getMessage());
        }
        return result;
    }

}
