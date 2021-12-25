package ru.sfedu.market.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.Constants.CSV_ORDER_KEY;
import static ru.sfedu.market.bean.ProductType.*;
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
        Optional optional = getAll(Customer.class, CSV_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {

            return writeToMongo(new Result(ERROR,new Customer(id,null,null),READ,NPE_CUSTOMER));

        }
        else{

            return writeToMongo(new Result(SUCCESS,optional.get(),READ, EXIST_CUSTOMER));

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
        /**CASCADE STARTif ()*/
        customers.removeIf(o -> o.getId().equals(id));
        remove(customers,CSV_CUSTOMER_KEY);
        return writeToMongo(new Result(SUCCESS,customer,DELETE, REMOVE_SUCCESS));
    }
    @Override
    public Result<Eatable> createEatable(Eatable eatable) throws IOException {
        if (readEatableById(eatable.getId()).getStatus().equals(ERROR)) {
            create(eatable,CSV_EATABLE_KEY);
            return writeToMongo(new Result(SUCCESS, eatable, CREATE, CREATE_SUCCESS_PRODUCT));
        }
        return writeToMongo(new Result(ERROR, eatable, CREATE, String.format(PRESENT_BEAN, eatable.getId())));


    }

    @Override
    public Result<Eatable> readEatableById(Long id) throws IOException {
        Optional optional = getAll(Eatable.class, CSV_EATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Product(id,null,null),READ,NPE_EATABLE));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ,CREATE_SUCCESS_PRODUCT));
        }
    }



    @Override
    public Result<Eatable> updateEatable(Eatable eatable) throws IOException {
        List<Eatable> eatables = getAll(Eatable.class, CSV_EATABLE_KEY);
        if (eatables.stream().noneMatch(o -> o.getId().equals(eatable.getId()))) {
            return writeToMongo(new Result(ERROR, eatable, UPDATE, String.format(EMPTY_BEAN, eatable.getId())));
        }
        eatables.removeIf(o -> o.getId().equals(eatable.getId()));
        eatables.add(eatable);
        Result refresh = remove(eatables, CSV_EATABLE_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, eatable, UPDATE, String.format(UPDATE_SUCCESS, eatable.toString())));
        } else {
            return writeToMongo(new Result(ERROR, eatable, UPDATE, refresh.getLog()));
        }
    }
    @Override
    public Result<Eatable> deleteEatableById(Long id) throws IOException {

        List<Eatable> eatables = getAll(Eatable.class, CSV_EATABLE_KEY);
        if (readEatableById(id).getStatus().equals(ERROR)) {
            return writeToMongo(new Result(ERROR, new Eatable(id,null,null,0), DELETE, String.format(EMPTY_BEAN, id)));
        }

        Optional optional = getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getEatable().getId().equals(id)).findFirst();

        if (!optional.isEmpty()){
            removeOrderByEatableCascade(id);
        }

        Eatable eatable = readEatableById(id).getBean();
        eatables.removeIf(o -> o.getId().equals(id));
        remove(eatables,CSV_EATABLE_KEY);
        return writeToMongo(new Result(SUCCESS,eatable,DELETE, REMOVE_SUCCESS));
    }
    @Override
    public Result<Uneatable> createUneatable(Uneatable uneatable) throws IOException {
        if (readUneatableById(uneatable.getId()).getStatus().equals(ERROR)) {
            create(uneatable,CSV_UNEATABLE_KEY);
            return writeToMongo(new Result(SUCCESS, uneatable, CREATE, CREATE_SUCCESS_PRODUCT));
        }
        return writeToMongo(new Result(ERROR, uneatable, CREATE, String.format(PRESENT_BEAN, uneatable.getId())));


    }

    @Override
    public Result<Uneatable> readUneatableById(Long id) throws IOException {
        Optional optional = getAll(Uneatable.class, CSV_UNEATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Uneatable(id,null,null, 0),READ,NPE_UNEATABLE));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ, EXIST_PRODUCT));
        }
    }



    @Override
    public Result<Uneatable> updateUneatable(Uneatable uneatable) throws IOException {
        List<Uneatable> uneatables = getAll(Uneatable.class, CSV_UNEATABLE_KEY);
        if (uneatables.stream().noneMatch(o -> o.getId().equals(uneatable.getId()))) {
            return writeToMongo(new Result(ERROR, uneatable, UPDATE, String.format(EMPTY_BEAN, uneatable.getId())));
        }
        uneatables.removeIf(o -> o.getId().equals(uneatable.getId()));
        uneatables.add(uneatable);
        Result refresh = remove(uneatables, CSV_UNEATABLE_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, uneatable, UPDATE, String.format(UPDATE_SUCCESS, uneatable.getId())));
        } else {
            return writeToMongo(new Result(ERROR, uneatable, UPDATE, UPDATE_SUCCESS));
        }
    }


    @Override
    public Result<Uneatable> deleteUneatableById(Long id) throws IOException {

        List<Uneatable> uneatables = getAll(Uneatable.class, CSV_UNEATABLE_KEY);
        if (readUneatableById(id).getStatus().equals(ERROR)) {
            return writeToMongo(new Result(ERROR, new Uneatable(id,null,null,0), DELETE, String.format(EMPTY_BEAN, id)));
        }
        Optional optional = getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getUneatable().getId().equals(id)).findFirst();

        if (!optional.isEmpty()){
            removeOrderByUneatableCascade(id);
        }


        Uneatable uneatable = readUneatableById(id).getBean();
        uneatables.removeIf(o -> o.getId().equals(id));
        remove(uneatables,CSV_UNEATABLE_KEY);
        return writeToMongo(new Result(SUCCESS,uneatable,DELETE, REMOVE_SUCCESS));
    }

    @Override
    public Result<Order> createOrder(Order order) throws IOException {
        if ((order.getCustomer().getAge()<18)&&(order.getEatable().getType().equals(ProductType.ALCOHOL)))
        {

            return writeToMongo(new Result(ERROR, order,CREATE,AGE_ERROR));
        }

        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            create(order,CSV_ORDER_KEY);
            return writeToMongo(new Result(SUCCESS, order, CREATE, CREATE_SUCCESS_ORDER));
        }
        return writeToMongo(new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId())));
    }




    @Override

    public Result<Order> readOrderById(Long id) throws IOException {

        Optional optional = getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();

        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Order(id,null,null,null),READ,NPE_ORDER));
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
            return writeToMongo(new Result(SUCCESS, order, UPDATE, CREATE_SUCCESS_ORDER));
        } else {
            return writeToMongo(new Result(ERROR, order, UPDATE, UPDATE_ERROR_ORDER));
        }
    }

    @Override
    public Result<Order> deleteOrderById(Long id) throws IOException {
        Order order = readOrderById(id).getBean();
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (readOrderById(id).getStatus().equals(ERROR)) {
            return writeToMongo(new Result(ERROR, new Order(id,null,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }
        Optional optional = getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getCustomer().getId().equals(id)).findFirst();

        if (!optional.isEmpty()){
            removeOrderByCustomerCascade(id);
        }
        orders.removeIf(o -> o.getId().equals(id));
        remove(orders,CSV_ORDER_KEY);
        return writeToMongo(new Result(SUCCESS,order,DELETE, REMOVE_SUCCESS));
    }

    @Override
    public void removeOrderByEatableCascade(Long productId) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        orders.removeIf(o -> o.getEatable().getId().equals(productId));
        remove(orders, CSV_ORDER_KEY);

    }

    @Override
    public void removeOrderByUneatableCascade(Long productId) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        orders.removeIf(o -> o.getUneatable().getId().equals(productId));
        remove(orders, CSV_ORDER_KEY);
    }


    @Override
    public void removeOrderByCustomerCascade(Long customerId) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        orders.removeIf(o -> o.getCustomer().getId().equals(customerId));
        remove(orders, CSV_ORDER_KEY);
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
