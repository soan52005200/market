package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.bean.*;
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
    public Result<Customer> createCustomer(Customer customer) throws IOException {
        if (readCustomerById(customer.getId()).getStatus().equals(ERROR)){
            execute(String.format(CUSTOMER_INSERT, customer.getId(), customer.getFio(), customer.getAge()));
            return writeToMongo(new Result(SUCCESS,customer, CREATE,CREATE_SUCCESS_CUSTOMER));
        }else{
            return writeToMongo(new Result(ERROR,customer, CREATE,CREATE_ERROR_CUSTOMER));
        }

    }

    @Override
    public Result<Customer> readCustomerById(Long id) throws IOException {
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
            return writeToMongo(new Result(ERROR, new Customer(id,null,null), READ, NPE_CUSTOMER));
        }
        else {
            return writeToMongo(new Result(SUCCESS, customer, READ, EXIST_CUSTOMER));
        }

    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) throws IOException {
        if (readCustomerById(customer.getId()).getStatus().equals(SUCCESS)) {
            execute(String.format(CUSTOMER_UPDATE, customer.getFio(), customer.getAge(), customer.getId()));
            return writeToMongo(new Result(SUCCESS,customer, UPDATE, SUCCESS_UPDATE));
        }
        else{
            return writeToMongo(new Result(ERROR,customer, UPDATE, NPE_CUSTOMER));
        }
    }

    @Override
    public Result<Customer> deleteCustomerById(Long id) throws IOException {
        Customer customer = readCustomerById(id).getBean();
        if (readCustomerById(id).getStatus().equals(SUCCESS)) {
            execute(String.format(CUSTOMER_DELETE, id));
            return writeToMongo(new Result(SUCCESS,customer, DELETE, REMOVE_SUCCESS));
        }
        else{
            return writeToMongo(new Result(ERROR,new Customer(id,null,null), DELETE, NPE_CUSTOMER));
        }
    }


    @Override
    public Result<Eatable> createEatable(Eatable eatable) throws IOException {

        if (readEatableById(eatable.getId()).getStatus().equals(ERROR)){
            execute(String.format(EATABLE_INSERT, eatable.getId(), eatable.getName(), eatable.getType()));
            return writeToMongo(new Result(SUCCESS,eatable, CREATE,CREATE_SUCCESS_PRODUCT));
        }else{
            return writeToMongo(new Result(ERROR,eatable, CREATE,CREATE_ERROR_PRODUCT));
        }

    }


    @Override
    public Result<Eatable> readEatableById(Long id) throws IOException {
        Eatable eatable = null;
        ResultSet set = select(String.format(EATABLE_SELECT, id));
        try {
            if (set != null && set.next()) {
                eatable = new Eatable(
                        set.getLong(1),
                        set.getString(2),
                        ProductType.valueOf(set.getString(3)),
                        set.getInt(4)
                );

            }
        } catch (Exception exception) {
            log.error(exception);

        }

        if (Optional.ofNullable(eatable).equals(Optional.empty())){

            return writeToMongo(new Result(ERROR, new Eatable(id,null,null,null), READ, NPE_PRODUCT));
        }
        else {
            return writeToMongo(new Result(SUCCESS, eatable , READ, EXIST_PRODUCT));
        }
    }


    @Override
    public Result<Eatable> updateEatable(Eatable eatable) throws IOException {
        if (readEatableById(eatable.getId()).getStatus().equals(SUCCESS)) {
            execute(String.format(EATABLE_UPDATE, eatable.getName(), eatable.getType(), eatable.getId()));
            return writeToMongo(new Result(SUCCESS,eatable, UPDATE, SUCCESS_UPDATE));
        }
        else{
            return writeToMongo(new Result(ERROR,eatable, UPDATE, NPE_EATABLE));
        }
    }

    @Override
    public Result<Eatable> deleteEatableById(Long id) throws IOException {
        Eatable eatable = readEatableById(id).getBean();
        if (readEatableById(id).getStatus().equals(SUCCESS)) {
            execute(String.format(EATABLE_DELETE, id));
            return writeToMongo(new Result(SUCCESS,eatable, DELETE, REMOVE_SUCCESS));
        }
        else{
            return writeToMongo(new Result(ERROR,new Eatable(id,null,null,null), DELETE, NPE_EATABLE));
        }
    }

    @Override
    public Result<Order> createOrder(Order order) throws IOException {
        if ((order.getCustomer().getAge()<18)&&(order.getEatable().getType().equals(ProductType.ALCOHOL)))
        {

            return writeToMongo(new Result(ERROR, order,CREATE,AGE_ERROR));
        }
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            Customer customer = order.getCustomer();
            Eatable eatable = order.getEatable();
            if (readEatable(product.getId(),order.getType()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, customer, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId())));
            }if (readProductByIdAndType(product.getId(),order.getType()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, customer, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId())));
            }
            if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, product, CREATE, String.format(EMPTY_BEAN, order.getProduct().getId())));
            }


            execute(String.format(ORDER_INSERT, order.getId(), order.getProduct().getId(),order.getCustomer().getId()));
            return writeToMongo(new Result(SUCCESS,order,CREATE,CREATE_SUCCESS_ORDER));
        }
        else {
            return writeToMongo(new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId())));
        }
    }

    @Override
    public Result<Order> readOrderById(Long id) throws NullPointerException, IOException {

        Order order = new Order();
        ResultSet set = select(String.format(ORDER_SELECT, id));

        try {
            if (set != null && set.next()) {
                order = new Order();
                order.setType(ProductType.valueOf(set.getString(3)));
                order.setId(set.getLong(1));
                Result productResult =readProductByIdAndType(set.getLong(2),order.getType());
                Customer customer = readCustomerById(set.getLong(3)).getBean();

                if (productResult.getStatus().equals(ERROR)) {

                    return writeToMongo(new Result(ERROR,new Order(id,new Product(id,null,null),new Customer(id,null,null),null),READ,NPE_PRODUCT));
                }
                if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {

                    return writeToMongo(new Result(ERROR,new Order(id,new Product(id,null,null),new Customer(id,null,null)),READ,NPE_CUSTOMER));
                }

                order.setProduct(product);
                order.setCustomer(customer);
            }
            else{

                return writeToMongo(new Result(ERROR,new Order(id,new Product(id,null,MILK),new Customer(id,null,null)),READ,NPE_CUSTOMER));
            }
        } catch (Exception exception) {
            log.error(exception);
        }

        return writeToMongo(new Result(SUCCESS,order,READ,EXIST_ORDER));
    }

    @Override
    public Result<Order> updateOrder(Order order) throws IOException {
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            execute(String.format(ORDER_UPDATE, order.getProduct().getId(), order.getCustomer().getId(), order.getId()));
            return writeToMongo(new Result(ERROR,order, UPDATE, NPE_ORDER ));
        } else {
            return writeToMongo(new Result(SUCCESS, order, UPDATE, UPDATE_SUCCESS));
        }
    }



    @Override
    public Result<Order> deleteOrderById(Long id) throws IOException {
        Order order = (readOrderById(id).getBean());
        if (readOrderById(id).getStatus().equals(ERROR)) {

            return writeToMongo(new Result(ERROR,new Order(id,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }
        execute(String.format(ORDER_DELETE, id));
        return writeToMongo(new Result(SUCCESS, order, DELETE, REMOVE_SUCCESS));
    }

    @Override
    public Result readProductByTypeAndId(ProductType type, Long id) throws IOException {
        switch (type) {
            case MILK,MEET,BAKERY,BAKERY,FRUIT,VEGETABLE,SWEET,ALCOHOL:
                return readEatableById(id);
            case CLOTHES,STATIONERY:
                return readUneatableById(id);

            default:
                return new Result(ERROR,null,READ,NPE_OBJECT);
        }
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
