package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;



import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
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
            removeOrderByCustomerCascade(id);

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
            execute(String.format(EATABLE_INSERT, eatable.getId(), eatable.getName(), eatable.getType(),eatable.getBestBefore()));
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

            return writeToMongo(new Result(ERROR, new Eatable(id,null,null,0), READ, NPE_EATABLE));
        }
        else {
            return writeToMongo(new Result(SUCCESS, eatable , READ, EXIST_PRODUCT));
        }
    }


    @Override
    public Result<Eatable> updateEatable(Eatable eatable) throws IOException {
        if (readEatableById(eatable.getId()).getStatus().equals(SUCCESS)) {
            execute(String.format(EATABLE_UPDATE, eatable.getName(), eatable.getType(),eatable.getBestBefore(), eatable.getId()));
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
            removeOrderByEatableCascade(id);
            execute(String.format(EATABLE_DELETE, id));
            return writeToMongo(new Result(SUCCESS,eatable, DELETE, REMOVE_SUCCESS));
        }
        else{
            return writeToMongo(new Result(ERROR,new Eatable(id,null,null,0), DELETE, NPE_EATABLE));
        }
    }
    @Override
    public Result<Uneatable> createUneatable(Uneatable uneatable) throws IOException {

        if (readUneatableById(uneatable.getId()).getStatus().equals(ERROR)){
            execute(String.format(UNEATABLE_INSERT, uneatable.getId(), uneatable.getName(), uneatable.getType(),uneatable.getGuarantee()));
            return writeToMongo(new Result(SUCCESS,uneatable, CREATE,CREATE_SUCCESS_PRODUCT));
        }else{
            return writeToMongo(new Result(ERROR,uneatable, CREATE,CREATE_ERROR_PRODUCT));
        }

    }


    @Override
    public Result<Uneatable> readUneatableById(Long id) throws IOException {
        Uneatable uneatable = null;
        ResultSet set = select(String.format(UNEATABLE_SELECT, id));
        try {
            if (set != null && set.next()) {
                uneatable = new Uneatable(
                        set.getLong(1),
                        set.getString(2),
                        ProductType.valueOf(set.getString(3)),
                        set.getInt(4)
                );

            }
        } catch (Exception exception) {
            log.error(exception);

        }

        if (Optional.ofNullable(uneatable).equals(Optional.empty())){

            return writeToMongo(new Result(ERROR, new Uneatable(id,null,null,0), READ, NPE_UNEATABLE));
        }
        else {
            return writeToMongo(new Result(SUCCESS, uneatable , READ, EXIST_PRODUCT));
        }
    }


    @Override
    public Result<Uneatable> updateUneatable(Uneatable uneatable) throws IOException {
        if (readUneatableById(uneatable.getId()).getStatus().equals(SUCCESS)) {
            execute(String.format(UNEATABLE_UPDATE, uneatable.getName(), uneatable.getType(),uneatable.getGuarantee(), uneatable.getId()));
            return writeToMongo(new Result(SUCCESS,uneatable, UPDATE, SUCCESS_UPDATE));
        }
        else{
            return writeToMongo(new Result(ERROR,uneatable, UPDATE, NPE_UNEATABLE));
        }
    }

    @Override
    public Result<Uneatable> deleteUneatableById(Long id) throws IOException {

        Uneatable uneatable = readUneatableById(id).getBean();
        if (readUneatableById(id).getStatus().equals(SUCCESS)) {
            removeOrderByUneatableCascade(id);
            execute(String.format(UNEATABLE_DELETE, id));
            return writeToMongo(new Result(SUCCESS,uneatable, DELETE, REMOVE_SUCCESS));
        }
        else{
            return writeToMongo(new Result(ERROR,new Uneatable(id,null,null,0), DELETE, NPE_UNEATABLE));
        }
    }


    @Override
    public Result<Order> createOrder(Order order) throws IOException {
        /**Проверка на возраст покупателя если в заказе алкоголь.*/
        if (checkAge(order.getCustomer().getAge())==false){
            return writeToMongo(new Result(ERROR, order, CREATE,AGE_ERROR));
        }

        /**Проверка на срок годности если в заказе есть съедобный продукт*/

        if (checkBestBefore(order.getEatable().getBestBefore())==false){

            return writeToMongo(new Result(ERROR, order, CREATE,BESTBEFORE_ERROR));

        }

        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            Customer customer = order.getCustomer();
            Eatable eatable = order.getEatable();
            Uneatable uneatable = order.getUneatable();
            if (readEatableById(eatable.getId()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, eatable, CREATE, String.format(EMPTY_BEAN, order.getEatable().getId())));
            }if (readUneatableById(uneatable.getId()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, uneatable, CREATE, String.format(EMPTY_BEAN, order.getUneatable().getId())));
            }
            if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, customer, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId())));
            }


            execute(String.format(ORDER_INSERT, order.getId(), order.getEatable().getId(),order.getUneatable().getId(),order.getCustomer().getId()));
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
                order.setId(set.getLong(1));
                Result eatableResult = readEatableById(set.getLong(2));
                Result uneatableResult = readUneatableById(set.getLong(3));
                Customer customer = readCustomerById(set.getLong(4)).getBean();

                if (eatableResult.getStatus().equals(ERROR)) {

                    return writeToMongo(new Result(ERROR,new Order(id,new Eatable(id,null,null,0),new Uneatable(id,null,null,0),new Customer(id,null,null)),READ,NPE_EATABLE));}
                if (uneatableResult.getStatus().equals(ERROR)) {

                    return writeToMongo(new Result(ERROR,new Order(id,new Eatable(id,null,null,0),new Uneatable(id,null,null,0),new Customer(id,null,null)),READ,NPE_UNEATABLE));

                }
                if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {

                    return writeToMongo(new Result(ERROR,new Order(id,new Eatable(id,null,null,0),new Uneatable(id,null,null,0),new Customer(id,null,null)),READ,NPE_CUSTOMER));
                }

                order.setEatable((Eatable) eatableResult.getBean());
                order.setUneatable((Uneatable) uneatableResult.getBean());
                order.setCustomer(customer);
            }
            else{

                return writeToMongo(new Result(ERROR,new Order(id,new Eatable(id,null,null,0),new Uneatable(id,null,null,0),new Customer(id,null,null)),READ,NPE_CUSTOMER));
            }
        } catch (Exception exception) {
            log.error(exception);
        }

        return writeToMongo(new Result(SUCCESS,order,READ,EXIST_ORDER));
    }

    @Override
    public Result<Order> updateOrder(Order order) throws IOException {
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            execute(String.format(ORDER_UPDATE, order.getEatable().getId(),order.getUneatable().getId(), order.getCustomer().getId(), order.getId()));
            return writeToMongo(new Result(ERROR,order, UPDATE, NPE_ORDER ));
        } else {
            return writeToMongo(new Result(SUCCESS, order, UPDATE, UPDATE_SUCCESS));
        }
    }



    @Override
    public Result<Order> deleteOrderById(Long id) throws IOException {
        Order order = (readOrderById(id).getBean());
        if (readOrderById(id).getStatus().equals(ERROR)) {

            return writeToMongo(new Result(ERROR,new Order(id,null,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }
        execute(String.format(ORDER_DELETE, id));
        return writeToMongo(new Result(SUCCESS, order, DELETE, REMOVE_SUCCESS));
    }

    @Override
    public void removeOrderByEatableCascade(Long id) {
        if (getCountingAllOrdersForCascade(id,ORDER_SELECT_EATABLE)>1){
            execute(String.format(ORDER_DELETE_CASCADE_BY_EATABLE, id));
        }
    }

    @Override
    public void removeOrderByUneatableCascade(Long id) {
        if (getCountingAllOrdersForCascade(id,ORDER_SELECT_UNEATABLE)>1){
            execute(String.format(ORDER_DELETE_CASCADE_BY_UNEATABLE, id));
        }
        execute(String.format(UNEATABLE_DELETE, id));

    }

    @Override
    public void removeOrderByCustomerCascade(Long id) {
        if (getCountingAllOrdersForCascade(id,ORDER_SELECT_CUSTOMER)>1){
            execute(String.format(ORDER_DELETE_CASCADE_BY_CUSTOMER, id));
        }
    }

    @Override
    public boolean checkBestBefore(int eatableBestbefore) throws IOException {
        if (DAY<eatableBestbefore){
            return false;
        }
        return true;
    }
    @Override
    public boolean checkAge(int customerAge){
        if (customerAge<18){
            return false;}
        return true;
    }


    /** ПОШЛИ СИСТЕМНЫЕ МЕТОДЫ */
    /** ПОШЛИ СИСТЕМНЫЕ МЕТОДЫ */
    /** ПОШЛИ СИСТЕМНЫЕ МЕТОДЫ */



    /**
     *
     * Проверка на количество заказов с этой сущностью для дальнейшего удаления
     *
     */

    public Long getCountingAllOrdersForCascade(Long id,String SqlRequest) {
        List<Order> list = new ArrayList<>();
        ResultSet set = select(String.format(SqlRequest,id));
        try {
            while (set != null && set.next()) {
                list.add(new Order(
                        set.getLong(1),
                        readEatableById(set.getLong(2)).getBean(),
                        readUneatableById(set.getLong(3)).getBean(),
                        readCustomerById(set.getLong(4)).getBean()
                ));
            }

        } catch (Exception exception) {
            log.error(exception);
        }
        return list.stream().collect(Collectors.counting());
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
