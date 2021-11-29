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
        }catch(Exception exception){
            return new Result<>(ERROR,customer, CREATE,CREATE_ERROR_CUSTOMER);
        }
        return new Result<>(SUCCESS,customer, CREATE,CREATE_ERROR_CUSTOMER);
    }

    @Override
    public Optional<Customer> readCustomerById(Long id) {
        Customer customer = null;
        ResultSet set = select(String.format(CUSTOMER_SELECT, id));
        try {
            if (set != null && set.next()) {
                customer = new Customer(
                        set.getLong(1),
                        set.getString(2),
                        set.getInt(3)
                );
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        if (readCustomerById(customer.getId()).isPresent()) {
            execute(String.format(CUSTOMER_UPDATE, customer.getFio(), customer.getAge(), customer.getId()));
            return new Result<>(SUCCESS,customer, UPDATE, UPDATE_SUCCESS);
        }
        else{
            return new Result<>(ERROR,null, UPDATE, NPE_CUSTOMER);
        }
    }

    @Override
    public Result<Void> deleteCustomerById(Long id) {
        if (readCustomerById(id).isPresent()) {
            execute(String.format(CUSTOMER_DELETE, id));
            return new Result<>(SUCCESS,null, DELETE, REMOVE_SUCCESS);
        }
        else{
            return new Result<>(ERROR,null, DELETE, NPE_CUSTOMER);
        }
    }


    @Override
    public Result<Product> createProduct(Product product){return execute(String.format(PRODUCT_INSERT, product.getId(), product.getName(), product.getType()));}

    @Override
    public Optional<Product> readProductById(Long id) {
        Product obj = null;
        ResultSet set = select(String.format(PRODUCT_SELECT, id));
        try {
            if (set != null && set.next()) {
                obj = new Product(
                        set.getLong(1),
                        set.getString(2),
                        ProductType.valueOf(set.getString(3))
                );
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return Optional.ofNullable(obj);
    }


    @Override
    public Result<Product> updateProduct(Product product) {
        if (readProductById(product.getId()).isPresent()) {
            execute(String.format(PRODUCT_UPDATE, product.getName(), product.getType(), product.getId()));
            return new Result<>(SUCCESS,product, UPDATE, UPDATE_SUCCESS);
        }
        else{
            return new Result<>(ERROR,null, UPDATE, NPE_PRODUCT);
        }
    }

    @Override
    public Result<Void> deleteProductById(Long id) {
        if (readProductById(id).isPresent()) {
            execute(String.format(PRODUCT_DELETE, id));
            return new Result<>(SUCCESS,null, DELETE, REMOVE_SUCCESS);
        }
        else{
            return new Result<>(ERROR,null, DELETE, NPE_CUSTOMER);
        }
    }

    @Override
    public Result<Order> createOrder(Order order) {
        if (readOrderById(order.getId()).isEmpty()) {
            Optional<Customer> customer = readCustomerById(order.getCustomer().getId());
            Optional<? extends Product> product = readProductById(order.getProduct().getId());
            if (customer.isEmpty()) {
                return new Result<Order>(ERROR, null, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId()));
            }
            if (product.isEmpty()) {
                return new Result<Order>(ERROR, null, CREATE, String.format(EMPTY_BEAN, order.getProduct().getId()));
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
        return new Result<>(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId()));
    }

    @Override
    public Optional<Order> readOrderById(Long id) {
            Order obj = null;
            ResultSet set = select(String.format(ORDER_SELECT, id));
            try {
                if (set != null && set.next()) {
                    obj = new Order();
                    obj.setId(set.getLong(1));
                    Optional<? extends Product> product = readProductById(set.getLong(2));
                    Optional<Customer> customer = readCustomerById(set.getLong(3));

                    if (product.isEmpty()) {
                        throw new NullPointerException(NPE_PRODUCT);
                    } else if (customer.isEmpty()) {
                        throw new NullPointerException(NPE_CUSTOMER);
                    }

                    obj.setProduct(product.get());
                    obj.setCustomer(customer.get());
                }
            } catch (Exception exception) {
                log.error(exception);
            }
            return Optional.ofNullable(obj);

    }

    @Override
    public Result<Order> updateOrder(Order order) {
        if (readOrderById(order.getId()).isPresent()) {
            execute(String.format(ORDER_UPDATE, order.getProduct().getId(), order.getCustomer().getId(), order.getId()));
            return new Result<>(SUCCESS, order, UPDATE, UPDATE_SUCCESS);
        } else {
            return new Result<>(ERROR, null, UPDATE, NPE_ORDER);
        }
    }



    @Override
    public Result<Void> deleteOrderById(Long id) {
        Optional<Order> optional = readOrderById(id);
        if (optional.isEmpty()) {

            return new Result<>(ERROR, null, UPDATE, String.format(EMPTY_BEAN, id));
        }
        execute(String.format(ORDER_DELETE, id));
        return new Result<Void>(SUCCESS, null, UPDATE, REMOVE_SUCCESS);
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
