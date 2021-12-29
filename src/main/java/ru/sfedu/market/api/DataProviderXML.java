package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Crud.*;
import static ru.sfedu.market.utils.Status.*;


public class DataProviderXML extends IDataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderXML.class);

    @Override
    public Result<Customer> createCustomer(Customer customer) throws IOException {
        if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
            List<Customer> list = getAll(Customer.class, XML_CUSTOMER_KEY);
            list.add(customer);
            refresh(list, XML_CUSTOMER_KEY);
            return new Result(SUCCESS,customer,CREATE,CREATE_SUCCESS_CUSTOMER);
        }
        return writeToMongo(new Result(ERROR, customer, CREATE, String.format(PRESENT_BEAN, customer.getId())));
    }


    @Override
    public Result<Customer> readCustomerById(Long id) throws IOException {
        Optional optional = getAll(Customer.class, XML_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Customer(id,null,null),READ,NPE_CUSTOMER));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ,EXIST_CUSTOMER));
        }

    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) throws IOException {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return writeToMongo(new Result(ERROR, customer, UPDATE, String.format(EMPTY_BEAN, customer.getId())));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Customer> refresh = refresh(customers, XML_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result<>(SUCCESS, customer, UPDATE, String.format(UPDATE_SUCCESS, customer.getId())));
        } else {
            return writeToMongo(new Result<>(ERROR, customer, UPDATE, refresh.getLog()));
        }
    }
    @Override
    public Result<Customer> deleteCustomerById(Long id) throws IOException {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return writeToMongo(new Result(ERROR, new Customer(id,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }
        /**removeOrderByCustomerCascade(id); реалиовать удаление всех заказов пользователя*/
        customers.removeIf(o -> o.getId().equals(id));
        Result<Customer> result = refresh(customers, XML_CUSTOMER_KEY);
        return writeToMongo(new Result(result.getStatus(), new Customer(id,null,null), DELETE, result.getLog()));
    }


    @Override
    public Result<Eatable> createEatable(Eatable eatable) throws IOException {

        if (readEatableById(eatable.getId()).getStatus().equals(ERROR)) {
            List<Eatable> list = getAll(Eatable.class, XML_EATABLE_KEY);
            list.add(eatable);
            refresh(list, XML_EATABLE_KEY);
            return writeToMongo(new Result(SUCCESS, eatable, CREATE, String.format(PERSISTENCE_SUCCESS, eatable.getId())));
        }
        return writeToMongo(new Result(ERROR, eatable, CREATE, String.format(PRESENT_BEAN, eatable.getId())));
    }


    @Override
    public Result<Eatable> readEatableById(Long id) throws IOException {
        Optional optional = getAll(Eatable.class, XML_EATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Eatable(id,null,null,0),READ,NPE_EATABLE));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ,EXIST_PRODUCT));

        }
    }


    @Override
    public Result<Eatable> updateEatable(Eatable eatable) throws IOException {
        List<Eatable> eatables = getAll(Eatable.class, XML_EATABLE_KEY);
        if (eatables.stream().noneMatch(o -> o.getId().equals(eatable.getId()))) {
            return writeToMongo(new Result(ERROR, eatable, UPDATE, String.format(EMPTY_BEAN, eatable.getId())));
        }
        eatables.removeIf(o -> o.getId().equals(eatable.getId()));
        eatables.add(eatable);
        Result<Eatable> refresh = refresh(eatables, XML_EATABLE_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, eatable, UPDATE, String.format(UPDATE_SUCCESS, eatable.toString())));
        } else {
            return writeToMongo(new Result(ERROR, eatable, UPDATE, refresh.getLog()));
        }
    }

    @Override
    public Result<Eatable> deleteEatableById(Long id) throws IOException {
        List<Eatable> eatables = getAll(Eatable.class, XML_EATABLE_KEY);
        if (eatables.stream().noneMatch(o -> o.getId().equals(id))) {
            return writeToMongo(new Result(ERROR, new Eatable(id,null,null,0), DELETE, String.format(EMPTY_BEAN, id)));
        }

        eatables.removeIf(o -> o.getId().equals(id));
        Result<Eatable> result = refresh(eatables, XML_EATABLE_KEY);
        return writeToMongo(new Result(result.getStatus(), new Eatable(id,null,null,0), DELETE, result.getLog()));
    }
    @Override
    public Result<Uneatable> createUneatable(Uneatable uneatable) throws IOException {

        if (readUneatableById(uneatable.getId()).getStatus().equals(ERROR)) {
            List<Uneatable> list = getAll(Uneatable.class, XML_UNEATABLE_KEY);
            list.add(uneatable);
            refresh(list, XML_UNEATABLE_KEY);
            return writeToMongo(new Result(SUCCESS, uneatable, CREATE, String.format(PERSISTENCE_SUCCESS, uneatable.getId())));
        }
        return writeToMongo(new Result(ERROR, uneatable, CREATE, String.format(PRESENT_BEAN, uneatable.getId())));
    }


    @Override
    public Result<Uneatable> readUneatableById(Long id) throws IOException {
        Optional optional = getAll(Uneatable.class, XML_UNEATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR,new Uneatable(id,null,null,0),READ,NPE_UNEATABLE));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ,CSV_UNEATABLE_KEY));

        }
    }


    @Override
    public Result<Uneatable> updateUneatable(Uneatable uneatable) throws IOException {
        List<Uneatable> uneatables = getAll(Uneatable.class, XML_UNEATABLE_KEY);
        if (uneatables.stream().noneMatch(o -> o.getId().equals(uneatable.getId()))) {
            return writeToMongo(new Result(ERROR, uneatable, UPDATE, String.format(EMPTY_BEAN, uneatable.getId())));
        }
        uneatables.removeIf(o -> o.getId().equals(uneatable.getId()));
        uneatables.add(uneatable);
        Result<Uneatable> refresh = refresh(uneatables, XML_UNEATABLE_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, uneatable, UPDATE, String.format(UPDATE_SUCCESS,uneatable.getId())));
        } else {
            return writeToMongo(new Result(ERROR, uneatable, UPDATE, refresh.getLog()));
        }
    }

    @Override
    public Result<Uneatable> deleteUneatableById(Long id) throws IOException {
        List<Uneatable> uneatables = getAll(Uneatable.class, XML_UNEATABLE_KEY);
        if (uneatables.stream().noneMatch(o -> o.getId().equals(id))) {
            return writeToMongo(new Result(ERROR, new Uneatable(id,null,null,0), DELETE, String.format(EMPTY_BEAN, id)));
        }

        uneatables.removeIf(o -> o.getId().equals(id));
        Result<Uneatable> result = refresh(uneatables, XML_UNEATABLE_KEY);
        return writeToMongo(new Result(result.getStatus(), new Uneatable(id,null,null,0), DELETE, result.getLog()));
    }


    @Override
    public Result<Order> createOrder(Order order) throws IOException {
        /**Проверка на возраст покупателя если в заказе алкоголь.*/
        if (order.getEatable().getType().equals(ProductType.ALCOHOL))
        {
            if (checkAge(order.getCustomer().getAge())==false){
                return writeToMongo(new Result(ERROR, order, CREATE,AGE_ERROR));
            }

        }
        /**Проверка на срок годности если в заказе есть съедобный продукт*/
        if (!isNull(order.getEatable().getBestBefore())){
            if (checkBestBefore(order.getEatable().getBestBefore())){

                return writeToMongo(new Result(ERROR, order, CREATE,BESTBEFORE_ERROR));

            }
        }
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {

            if (readCustomerById(order.getId()).getStatus().equals(ERROR)) {
                return writeToMongo(new Result(ERROR, order, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId())));
            }
            if (readEatableById(order.getId()).getStatus().equals(ERROR)) {
                return new Result(ERROR, order, CREATE, String.format(EMPTY_BEAN, order.getEatable().getId()));
            }
            if (readUneatableById(order.getId()).getStatus().equals(ERROR)) {
                return new Result(ERROR, order, CREATE, String.format(EMPTY_BEAN, order.getUneatable().getId()));
            }

            List<Order> list = getAll(Order.class, XML_ORDER_KEY);
            list.add(order);
            refresh(list, XML_ORDER_KEY);
            return writeToMongo(new Result(SUCCESS,order,CREATE,CREATE_SUCCESS_ORDER));

        }
        return writeToMongo(new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId())));
    }


    @Override
    public Result<Order> readOrderById(Long id) throws IOException {
        Optional optional = getAll(Order.class, XML_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return writeToMongo(new Result(ERROR, new Order(id,null,null,null), READ, NPE_ORDER));
        }
        else{
            return writeToMongo(new Result(SUCCESS,optional.get(),READ,EXIST_ORDER));}
    }

    @Override
    public Result<Order> updateOrder(Order order) throws IOException {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(order.getId()))) {
            return writeToMongo(new Result(ERROR, order, UPDATE, String.format(EMPTY_BEAN, order.getId())));
        }
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        Result<Order> refresh = refresh(orders,XML_ORDER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return writeToMongo(new Result(SUCCESS, order, UPDATE,String.format(UPDATE_SUCCESS,order.getId())));
        } else {
            return writeToMongo(new Result(ERROR, order, UPDATE, refresh.getLog()));
        }
    }

    @Override
    public Result<Order> deleteOrderById(Long id) throws IOException {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(id))) {
            return writeToMongo(new Result(ERROR, new Order(id,null,null,null), DELETE, String.format(EMPTY_BEAN, id)));
        }


        orders.removeIf(o -> o.getId().equals(id));

        Result<Order> result = refresh(orders, XML_ORDER_KEY);
        if (result.getStatus() == SUCCESS) {
            result = new Result(SUCCESS, new Order(id,null,null,null), DELETE, ORDER_CLOSE);
        }
        return writeToMongo(new Result(result.getStatus(), new Order(id,null,null,null), DELETE, result.getLog()));
    }


    @Override
    public void removeOrderByEatableCascade(Long productId) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        orders.removeIf(o -> o.getEatable().getId().equals(productId));
        refresh(orders, XML_ORDER_KEY);
    }

    @Override
    public void removeOrderByUneatableCascade(Long productId) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        orders.removeIf(o -> o.getUneatable().getId().equals(productId));
        refresh(orders, XML_ORDER_KEY);

    }

    @Override
    public void removeOrderByCustomerCascade(Long customerId) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        orders.removeIf(o -> o.getCustomer().getId().equals(customerId));
        refresh(orders, XML_ORDER_KEY);
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

    private <T> Result<T> refresh(List<T> container, String key) {
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            serializer.write(new Container<T>(container), fileWriter);

            return new Result(SUCCESS, null, CREATE, PERSISTENCE_SUCCESS);
        } catch (Exception exception) {
            log.error(exception);
            return new Result(ERROR, null, CREATE, exception.getMessage());
        }
    }


    private <T> List<T> getAll(Class<T> clazz, String key) {
        try {
            FileReader fileReader = new FileReader(getConfigurationEntry(key));
            Serializer serializer = new Persister();

            Container<T> container = serializer.read(Container.class, fileReader);
            fileReader.close();

            return container.getList();
        } catch (Exception exception) {
            log.error(exception);
            return new ArrayList<>();
        }
    }




    @Root(name = "Container")
    private static class Container<T> {
        @ElementList(inline = true, required = false)
        private List<T> list;

        public Container() {
        }

        public Container(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }}
    }

