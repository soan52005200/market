package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;
import ru.sfedu.market.bean.ProductType;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;



import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Crud.*;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderJDBC implements IDataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderJDBC.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) {
        try{
            execute(String.format(CUSTOMER_INSERT, customer.getId(), customer.getFio(), customer.getAge()));
            return new Result(SUCCESS,customer, CREATE,CREATE_SUCCESS_CUSTOMER);
        }catch(Exception exception){
            return new Result(ERROR,customer, CREATE,CREATE_ERROR_CUSTOMER);
        }

    }

    @Override
    public Result<Customer> readCustomerById(Long id) {
        Customer customer = null;
        ResultSet set = select(String.format(CUSTOMER_SELECT, id));
        try {
            if (set != null && set.next()) {
                customer = new Customer(
                        set.getLong(1),
                        set.getString(2),
                        set.getInt(3)
                );
                return new Result(SUCCESS, Optional.ofNullable(customer),READ,CREATE_SUCCESS_CUSTOMER);
            }
        } catch (Exception exception) {
            log.error(exception);
            return new Result(ERROR, Optional.ofNullable(customer),READ,CREATE_ERROR_CUSTOMER);

        }


    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        if (readCustomerById(customer.getId()).equals(SUCCESS)) {
            execute(String.format(CUSTOMER_UPDATE, customer.getFio(), customer.getAge(), customer.getId()));
            return new Result(SUCCESS,customer, UPDATE, UPDATE_SUCCESS);
        }
        else{
            return new Result(ERROR,null, UPDATE, NPE_CUSTOMER);
        }
    }

    @Override
    public Result<Customer> deleteCustomerById(Long id) {
        if (readCustomerById(id).equals(SUCCESS)) {
            execute(String.format(CUSTOMER_DELETE, id));
            return new Result(SUCCESS,null, DELETE, REMOVE_SUCCESS);
        }
        else{
            return new Result(ERROR,null, DELETE, NPE_CUSTOMER);
        }
    }


    @Override
    public Result<Product> createProduct(Product product){
        try{
            execute(String.format(PRODUCT_INSERT, product.getId(), product.getName(), product.getType()));
            return new Result(SUCCESS,product, CREATE,CREATE_SUCCESS_PRODUCT);
        }catch(Exception exception){
            return new Result(ERROR,product, CREATE,CREATE_ERROR_PRODUCT);
        }

    }


    @Override
    public Result<Product> readProductById(Long id) {
        Product product = null;
        ResultSet set = select(String.format(PRODUCT_SELECT, id));
        try {
            if (set != null && set.next()) {
                product = new Product(
                        set.getLong(1),
                        set.getString(2),
                        ProductType.valueOf(set.getString(3))
                );

            }
        } catch (Exception exception) {
            log.error(exception);
            return new Result(UNSUCCESSFUL,product, DELETE, REMOVE_UNSUCCESS);
        }
        return new Result(SUCCESS,product, DELETE, REMOVE_SUCCESS);
    }


    @Override
    public Result<Product> updateProduct(Product product) {
        if (readProductById(product.getId()).equals(SUCCESS)) {
            execute(String.format(PRODUCT_UPDATE, product.getName(), product.getType(), product.getId()));
            return new Result(SUCCESS,product, UPDATE, UPDATE_SUCCESS);
        }
        else{
            return new Result(ERROR,null, UPDATE, NPE_PRODUCT);
        }
    }

    @Override
    public Result<Void> deleteProductById(Long id) {
        if (readProductById(id).equals(SUCCESS)) {
            execute(String.format(PRODUCT_DELETE, id));
            return new Result(SUCCESS,null, DELETE, REMOVE_SUCCESS);
        }
        else{
            return new Result(ERROR,null, DELETE, NPE_CUSTOMER);
        }
    }

    @Override
    public Result<Order> createOrder(Order order) {
        if (readOrderById(order.getId()).equals(SUCCESS)) {
            Customer customer = readCustomerById(order.getCustomer().getId()).getBean();
            Product product = readProductById(order.getProduct().getId()).getBean();
            if (customer.equals(null)) {
                return new Result(ERROR, null, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId()));
            }
            if (product.equals(null)) {
                return new Result(ERROR, null, CREATE, String.format(EMPTY_BEAN, order.getProduct().getId()));
            }
            /**
             *
             * Проверка на восраст
             *

            if (customer.get().getAge() < product.get().getAgeLimit()) {
                return new Result<>(UNSUCCESSFUL, null, EXCEPTION_AGE_LIMIT);
            }*/
            return execute(String.format(ORDER_INSERT, order.getId(), order.getProduct().getId(), order.getCustomer().getId()));
        }
        return new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId()));
    }

    @Override
    public Result<Order> readOrderById(Long id) {

        ResultSet set = select(String.format(ORDER_SELECT, id));
        Order order= new Order();
            try {

                if (set != null && set.next()) {

                    order.setId(set.getLong(1));
                    Product product = readProductById(set.getLong(2)).getBean();
                    Customer customer = readCustomerById(set.getLong(3)).getBean();

                    if (product.equals(null)) {
                        throw new NullPointerException(NPE_PRODUCT);
                    } else if (customer.equals(null)) {
                        throw new NullPointerException(NPE_CUSTOMER);
                    }

                    order.setProduct(product);
                    order.setCustomer(customer);
                    return new Result(SUCCESS, order, CREATE, String.format(PRESENT_BEAN, order.getId()));

                }
                else{
                    return new Result(ERROR, null, CREATE, String.format(PRESENT_BEAN, order.getId()));

                }
            } catch (Exception exception) {
                log.error(exception);

            }

    }

    @Override
    public Result<Order> updateOrder(Order order) {
        if (readOrderById(order.getId()).equals(null)) {
            execute(String.format(ORDER_UPDATE, order.getProduct().getId(), order.getCustomer().getId(), order.getId()));
            return new Result(SUCCESS, order, UPDATE, UPDATE_SUCCESS);
        } else {
            return new Result(ERROR, null, UPDATE, NPE_ORDER);
        }
    }



    @Override
    public Result<Order> deleteOrderById(Long id) {
        Order order = (readOrderById(id).getBean());
        if (order.equals(null)) {

            return new Result(ERROR, null, UPDATE, String.format(EMPTY_BEAN, id));
        }
        execute(String.format(ORDER_DELETE, id));
        return new Result(SUCCESS, null, UPDATE, REMOVE_SUCCESS);
    }

    private <T> Result<T> execute(String sql) {
        Statement statement;
        try {
            statement = initConnection().createStatement();
            statement.executeUpdate(sql);
            initConnection().close();
        } catch (Exception exception) {
            log.error(exception);
        }
        return null;
    }

    private ResultSet select(String sql) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = initConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            initConnection().close();
        } catch (Exception exception) {
            log.error(exception);
        }
        return resultSet;
    }

    private Connection initConnection() throws ClassNotFoundException, IOException, SQLException {
        Class.forName(getConfigurationEntry(JDBC_DRIVER));
        Connection connection = DriverManager.getConnection(
                getConfigurationEntry(JDBC_URL),
                getConfigurationEntry(JDBC_USER),
                getConfigurationEntry(JDBC_PASSWORD)
        );
        connection.setAutoCommit(true);
        return connection;
    }
}
