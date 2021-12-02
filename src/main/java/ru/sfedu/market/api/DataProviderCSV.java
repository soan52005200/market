package ru.sfedu.market.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import static ru.sfedu.market.utils.Crud.*;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) {
        if (readCustomerById(customer.getId()).equals(null)) {
            return new Result(SUCCESS, customer, CREATE, String.format(PRESENT_BEAN, customer.getId()));
        }
        return new Result(UNSUCCESSFUL, customer, CREATE, String.format(PRESENT_BEAN, customer.getId()));

    }

    @Override
    public Result<Customer> readCustomerById(Long id) {
        return new Result(SUCCESS,getAll(Customer.class, CSV_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst(),READ,String.format(PRESENT_BEAN, id));

    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, UPDATE, String.format(EMPTY_BEAN, customer.getId()));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Void> refresh = remove(customers, CSV_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, customer, UPDATE, String.format(UPDATE_SUCCESS, customer.toString()));
        } else {
            return new Result<>(ERROR, customer, UPDATE, refresh.getLog());
        }
    }

    @Override
    public Result<Customer> deleteCustomerById(Long id) {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result(UNSUCCESSFUL, null, DELETE, String.format(EMPTY_BEAN, id));
        }
        /**removeOrderByCustomerCascade(id);   Удаление заказа(include)*/
        customers.removeIf(o -> o.getId().equals(id));
        return new Result(SUCCESS,customers,DELETE, REMOVE_SUCCESS);
    }
    @Override
    public Result<Product> createProduct(Product product) {
        if (readProductById(product.getId()).equals(SUCCESS)) {
            return create(product, CSV_PRODUCT_KEY);
        }
        return new Result<>(UNSUCCESSFUL, product, CREATE, String.format(PRESENT_BEAN, product.getId()));


    }

    @Override
    public Result<Product> readProductById(Long id) {
        return getAll(Product.class, CSV_PRODUCT_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Result<Product> updateProduct(Product product) {
        List<Product> products = getAll(Product.class, CSV_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(product.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, UPDATE, String.format(EMPTY_BEAN, product.getId()));
        }
        products.removeIf(o -> o.getId().equals(product.getId()));
        products.add(product);
        Result<Void> refresh = remove(products, CSV_PRODUCT_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, product, UPDATE, String.format(UPDATE_SUCCESS, product.toString()));
        } else {
            return new Result<>(ERROR, product, UPDATE, refresh.getLog());
        }
    }

    @Override
    public Result<Void> deleteProductById(Long id) {

        List<Product> products = getAll(Product.class, CSV_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, UPDATE, String.format(EMPTY_BEAN, id));
        }
        /**Надо реализовать автоматическое удаление заказов с этим покупателем
         *
         * реализовать удаление заказов с этими товарами.*/

        products.removeIf(o -> o.getId().equals(id));
        return remove(products, CSV_PRODUCT_KEY);
    }

    @Override
    public Result<Order> createOrder(Order order) {
        if (readOrderById(order.getId()).isEmpty()) {
            return create(order, CSV_ORDER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, order,UPDATE, String.format(PRESENT_BEAN, order.getId()));
    }

    @Override
    public Result<Order> readOrderById(Long id) {
        return getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(order.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, UPDATE, String.format(EMPTY_BEAN, order.getId()));
        }
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        Result<Void> refresh = remove(orders, CSV_ORDER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, order, UPDATE, String.format(UPDATE_SUCCESS, order.toString()));
        } else {
            return new Result<>(ERROR, order, UPDATE, refresh.getLog());
        }
    }

    @Override
    public Result<Void> deleteOrderById(Long id) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, DELETE, String.format(EMPTY_BEAN, id));
        }
        /**Надо реализовать проверку на возраст покупателя если в заказе алкоголь.*/

        orders.removeIf(o -> o.getId().equals(id));
        return remove(orders, CSV_ORDER_KEY);
    }


    /** ПОШЛИ СИСТЕМНЫЕ МЕТОДЫ */
    /** ПОШЛИ СИСТЕМНЫЕ МЕТОДЫ */
    /** ПОШЛИ СИСТЕМНЫЕ МЕТОДЫ */


    public <T> Result create(T bean,String key){
        Result<T> result;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(getConfigurationEntry(key), true));
                                                                                        /**В конце файла*/
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(bean);
            csvWriter.close();

            result = new Result<T>(SUCCESS, bean, CREATE, String.format(PERSISTENCE_SUCCESS, bean.toString()));
        } catch (Exception e) {
            log.error(e);
            result = new Result<T>(ERROR, null, CREATE, e.getMessage());
        }
        return result;


    }



    public <T> List<T> getAll(Class<T> clazz, String key){
        List<T> result;
        try {
            CSVReader reader =new CSVReader(new FileReader(getConfigurationEntry(key)));

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

            result = new Result<Void>(SUCCESS, null, DELETE, REMOVE_SUCCESS);

        } catch (Exception e) {
            log.error(e);
            result = new Result<Void>(ERROR, null, DELETE, e.getMessage());
        }
        return result;
    }

}
