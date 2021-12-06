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
import static ru.sfedu.market.bean.ProductType.MILK;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Crud.*;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderJDBC extends IDataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderJDBC.class);


    @Override
    public Result<Customer> createCustomer(Customer customer) {
        if (readCustomerById(customer.getId()).getStatus().equals(ERROR)){
            execute(String.format(CUSTOMER_INSERT, customer.getId(), customer.getFio(), customer.getAge()));
            return new Result(SUCCESS,customer, CREATE,CREATE_SUCCESS_CUSTOMER);
        }else{
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

                }
            } catch (Exception exception) {
                log.error(exception);



            }

        if (Optional.ofNullable(customer).equals(Optional.empty())){
            return new Result(ERROR, new Customer(id,null,null), READ, NPE_CUSTOMER);
        }
        else {
            return new Result(SUCCESS, customer, READ, EXIST_CUSTOMER);
        }

    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        if (readCustomerById(customer.getId()).getStatus().equals(SUCCESS)) {
            execute(String.format(CUSTOMER_UPDATE, customer.getFio(), customer.getAge(), customer.getId()));
            return new Result(SUCCESS,customer, UPDATE, SUCCESS_UPDATE);
        }
        else{
            return new Result(ERROR,customer, UPDATE, NPE_CUSTOMER);
        }
    }

    @Override
    public Result<Customer> deleteCustomerById(Long id) {
        Customer customer = readCustomerById(id).getBean();
        if (readCustomerById(id).getStatus().equals(SUCCESS)) {
            execute(String.format(CUSTOMER_DELETE, id));
            return new Result(SUCCESS,customer, DELETE, REMOVE_SUCCESS);
        }
        else{
            return new Result(ERROR,new Customer(id,null,null), DELETE, NPE_CUSTOMER);
        }
    }


    @Override
    public Result<Product> createProduct(Product product){

        if (readProductById(product.getId()).getStatus().equals(ERROR)){
            execute(String.format(PRODUCT_INSERT, product.getId(), product.getName(), product.getType()));
            return new Result(SUCCESS,product, CREATE,CREATE_SUCCESS_PRODUCT);
        }else{
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

        }

        if (Optional.ofNullable(product).equals(Optional.empty())){

            return new Result(ERROR, new Product(id,null,null), READ, NPE_PRODUCT);
        }
        else {
            return new Result(SUCCESS, product , READ, EXIST_PRODUCT);
        }
    }


    @Override
    public Result<Product> updateProduct(Product product) {
        if (readProductById(product.getId()).getStatus().equals(SUCCESS)) {
            execute(String.format(PRODUCT_UPDATE, product.getName(), product.getType(), product.getId()));
            return new Result(SUCCESS,product, UPDATE, UPDATE_SUCCESS);
        }
        else{
            return new Result(ERROR,product, UPDATE, NPE_PRODUCT);
        }
    }

    @Override
    public Result<Product> deleteProductById(Long id) {
        Product product = readProductById(id).getBean();
        if (readProductById(id).getStatus().equals(SUCCESS)) {
            execute(String.format(PRODUCT_DELETE, id));
            return new Result(SUCCESS,product, DELETE, REMOVE_SUCCESS);
        }
        else{
            return new Result(ERROR,new Product(id,null,null), DELETE, NPE_CUSTOMER);
        }
    }

    @Override
    public Result<Order> createOrder(Order order) {
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            Customer customer = order.getCustomer();
            Product product = order.getProduct();
            if (readProductById(product.getId()).getStatus().equals(ERROR)) {
                return new Result(ERROR, customer, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId()));
            }
            if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
                return new Result(ERROR, product, CREATE, String.format(EMPTY_BEAN, order.getProduct().getId()));
            }
            /**
             *
             * Проверка на восраст
             *

            if (customer.get().getAge() < product.get().getAgeLimit()) {
                return new Result<>(UNSUCCESSFUL, null, EXCEPTION_AGE_LIMIT);
            }*/

            execute(String.format(ORDER_INSERT, order.getId(), order.getProduct().getId(),order.getCustomer().getId()));
            return new Result(SUCCESS,order,CREATE,CREATE_SUCCESS_ORDER);
        }
        else {
            return new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId()));
        }
    }

    @Override
    public Result<Order> readOrderById(Long id) throws NullPointerException {

        Order order = new Order();
        ResultSet set = select(String.format(ORDER_SELECT, id));

        try {
            if (set != null && set.next()) {
                order = new Order();
                order.setId(set.getLong(1));
                Product product = readProductById(set.getLong(2)).getBean();
                Customer customer = readCustomerById(set.getLong(3)).getBean();
                if (readProductById(product.getId()).getStatus().equals(ERROR)) {

                    return new Result(ERROR,new Order(id,new Product(id,null,null),new Customer(id,null,null)),READ,NPE_PRODUCT);
                }
                if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {

                    return new Result(ERROR,new Order(id,new Product(id,null,null),new Customer(id,null,null)),READ,NPE_CUSTOMER);
                }

                order.setProduct(product);
                order.setCustomer(customer);
            }
            else{

                return new Result(ERROR,new Order(id,new Product(id,null,MILK),new Customer(id,null,null)),READ,NPE_CUSTOMER);
            }
        } catch (Exception exception) {
            log.error(exception);
        }

        return new Result(SUCCESS,order,READ,EXIST_ORDER);
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            execute(String.format(ORDER_UPDATE, order.getProduct().getId(), order.getCustomer().getId(), order.getId()));
            return new Result(ERROR,order, UPDATE, NPE_ORDER );
        } else {
            return new Result(SUCCESS, order, UPDATE, UPDATE_SUCCESS);
        }
    }



    @Override
    public Result<Order> deleteOrderById(Long id) {
        Order order = (readOrderById(id).getBean());
        if (readOrderById(id).getStatus().equals(ERROR)) {

            return new Result(ERROR,new Order(id,null,null), DELETE, String.format(EMPTY_BEAN, id));
        }
        execute(String.format(ORDER_DELETE, id));
        return new Result(SUCCESS, order, DELETE, REMOVE_SUCCESS);
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
