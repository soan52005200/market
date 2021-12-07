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
import ru.sfedu.market.bean.ProductType;
import ru.sfedu.market.utils.Result;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.Constants.CSV_ORDER_KEY;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Crud.*;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSV extends IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) throws IOException {
        if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
            create(customer,CSV_CUSTOMER_KEY);
            return writeToMongo(new Result(SUCCESS, customer, CREATE, CREATE_SUCCESS_CUSTOMER));
        }
        return writeToMongo(new Result(ERROR, customer, CREATE, String.format(PRESENT_BEAN, customer.getId())));

    }

    @Override
    public Result<Customer> readCustomerById(Long id) throws IOException {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        Optional optional = getAll(Customer.class, CSV_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {

            return writeToMongo(new Result(ERROR,new Customer(id,null,null),READ,NPE_CUSTOMER));

        }
        else{

            return writeToMongo(new Result(SUCCESS,optional.get(),READ, CSV_CUSTOMER_KEY));

        }



    }


    @Override
    public Result<Customer> updateCustomer(Customer customer) throws IOException {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return writeToMongo(new Result(ERROR, customer, UPDATE, String.format(EMPTY_BEAN, customer.getId())));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result refresh = remove(customers, CSV_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, customer, UPDATE, String.format(UPDATE_SUCCESS, customer.toString())));
        } else {
            return writeToMongo(new Result(ERROR, customer, UPDATE, refresh.getLog()));
        }
    }

    @Override
    public Result<Customer> deleteCustomerById(Long id) throws IOException {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (readCustomerById(id).getStatus().equals(ERROR)) {
            return writeToMongo(new Result(ERROR, new Customer(id,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }
        Customer customer = readCustomerById(id).getBean();
        /**removeOrderByCustomerCascade(id);   Удаление заказа(include)*/
        customers.removeIf(o -> o.getId().equals(id));
        remove(customers,CSV_CUSTOMER_KEY);
        return writeToMongo(new Result(SUCCESS,customer,DELETE, REMOVE_SUCCESS));
    }
    @Override
    public Result<Product> createProduct(Product product) throws IOException {
        if (readProductById(product.getId()).getStatus().equals(ERROR)) {
            create(product,CSV_PRODUCT_KEY);
            return writeToMongo(new Result(SUCCESS, product, CREATE, CREATE_SUCCESS_PRODUCT));
        }
        return writeToMongo(new Result(ERROR, product, CREATE, String.format(PRESENT_BEAN, product.getId())));


    }

    @Override
    public Result<Product> readProductById(Long id) throws IOException {
        Optional optional = getAll(Product.class, CSV_PRODUCT_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Product(id,null,null),READ,NPE_PRODUCT));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ, CSV_PRODUCT_KEY));
        }
    }



    @Override
    public Result<Product> updateProduct(Product product) throws IOException {
        List<Product> products = getAll(Product.class, CSV_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(product.getId()))) {
            return writeToMongo(new Result(ERROR, product, UPDATE, String.format(EMPTY_BEAN, product.getId())));
        }
        products.removeIf(o -> o.getId().equals(product.getId()));
        products.add(product);
        Result refresh = remove(products, CSV_PRODUCT_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, product, UPDATE, String.format(UPDATE_SUCCESS, product.toString())));
        } else {
            return writeToMongo(new Result(ERROR, product, UPDATE, refresh.getLog()));
        }
    }


    @Override
    public Result<Product> deleteProductById(Long id) throws IOException {

        List<Product> products = getAll(Product.class, CSV_PRODUCT_KEY);
        if (readProductById(id).getStatus().equals(ERROR)) {
            return writeToMongo(new Result(ERROR, new Product(id,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }
        /**Надо реализовать автоматическое удаление заказов с этим покупателем
         *
         * реализовать удаление заказов с этими товарами.*/
        Product product = readProductById(id).getBean();
        products.removeIf(o -> o.getId().equals(id));
        remove(products,CSV_PRODUCT_KEY);
        return writeToMongo(new Result(SUCCESS,product,DELETE, REMOVE_SUCCESS));
    }

    @Override
    public Result<Order> createOrder(Order order) throws IOException {
        if ((order.getCustomer().getAge()<18)&&(order.getProduct().getType().equals(ProductType.ALCOHOL)))
        {

            return writeToMongo(new Result(ERROR, order,CREATE,AGE_ERROR));
        }/**Проверка на возраст покупателя если в заказе алкоголь.*/

        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            create(order,CSV_ORDER_KEY);
            return writeToMongo(new Result(SUCCESS, order, CREATE, CREATE_SUCCESS_ORDER));
        }
        return writeToMongo(new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId())));
    }




    @Override

    public Result<Order> readOrderById(Long id) throws IOException {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        Optional optional = getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();

        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Order(id,null,null),READ,NPE_ORDER));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ, EXIST_OBJECT));

        }
    }



    @Override
    public Result<Order> updateOrder(Order order) throws IOException {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(order.getId()))) {
            return writeToMongo(new Result(ERROR, order, UPDATE, String.format(EMPTY_BEAN, order.getId())));
        }
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        Result refresh = remove(orders, CSV_ORDER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, order, UPDATE, String.format(UPDATE_SUCCESS, order.toString())));
        } else {
            return writeToMongo(new Result(ERROR, order, UPDATE, refresh.getLog()));
        }
    }

    @Override
    public Result<Order> deleteOrderById(Long id) throws IOException {
        Order order = readOrderById(id).getBean();
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (readOrderById(id).getStatus().equals(ERROR)) {
            return writeToMongo(new Result(ERROR, new Order(id,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }

        orders.removeIf(o -> o.getId().equals(id));
        remove(orders,CSV_ORDER_KEY);
        return writeToMongo(new Result(SUCCESS,order,DELETE, REMOVE_SUCCESS));
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

            result = new Result(SUCCESS, bean, CREATE, String.format(PERSISTENCE_SUCCESS, bean.toString()));

        } catch (Exception e) {

            log.error(e);
            result = new Result(ERROR, bean, CREATE, e.getMessage());

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


    private <T> Result remove(List<T> beans, String key) {

        Result<Void> result;

        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(getConfigurationEntry(key), false));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();

            beanToCsv.write(beans);
            csvWriter.close();

            result = new Result(SUCCESS, null, DELETE, REMOVE_SUCCESS);

        } catch (Exception e) {
            log.error(e);
            result = new Result(ERROR, null, DELETE, e.getMessage());
        }
        return result;
    }

}
