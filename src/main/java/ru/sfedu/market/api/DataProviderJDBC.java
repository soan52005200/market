package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJDBC implements IDataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderJDBC.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) {
        return execute(String.format(CUSTOMER_INSERT, customer.getId(), customer.getFio(), customer.getAge()));
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
        return execute(String.format(CUSTOMER_UPDATE, customer.getFio(), customer.getAge(), customer.getId()));
    }

    @Override
    public Result<Void> deleteCustomerById(Long id) {
        return execute(String.format(CUSTOMER_DELETE, id));
    }


    @Override
    public Result<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public Optional<Product> readProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public Result<Product> updateProduct(Product product) {
        return null;
    }

    @Override
    public Result<Void> deleteProductById(Long id) {
        return null;
    }

    @Override
    public Result<Order> createOrder(Order order) {
        return null;
    }

    @Override
    public Optional<Order> readOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        return null;
    }

    @Override
    public Result<Void> deleteOrderById(Long id) {
        return null;
    }

    private <T> Result<T> execute(String sql) {
        Statement statement;
        try {
            statement = initConnection().createStatement();
            statement.executeUpdate(sql);
            initConnection().close();
            return new Result<>(Status.SUCCESS, null, null);
        } catch (Exception exception) {
            log.error(exception);
            return new Result<>(Status.ERROR, null, null);
        }
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
