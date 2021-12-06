package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
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
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Crud.*;
import static ru.sfedu.market.utils.Status.*;


public class DataProviderXML extends IDataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderXML.class);

    @Override
    public Result<Customer> createCustomer(Customer customer) {
        if (readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
            List<Customer> list = getAll(Customer.class, XML_CUSTOMER_KEY);
            list.add(customer);
            refresh(list, XML_CUSTOMER_KEY);
            return new Result(SUCCESS,customer,CREATE,CREATE_SUCCESS_CUSTOMER);
        }
        return new Result(ERROR, customer, CREATE, String.format(PRESENT_BEAN, customer.getId()));
    }


    @Override
    public Result<Customer> readCustomerById(Long id) {
        Optional optional = getAll(Customer.class, XML_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return new Result(ERROR,new Customer(id,null,null),READ,NPE_CUSTOMER);
        }
        else{
            return new Result(SUCCESS,optional.get(),READ,CSV_CUSTOMER_KEY);
        }

    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return new Result(ERROR, customer, UPDATE, String.format(EMPTY_BEAN, customer.getId()));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Customer> refresh = refresh(customers, XML_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, customer, UPDATE, String.format(UPDATE_SUCCESS, customer.toString()));
        } else {
            return new Result<>(ERROR, customer, UPDATE, refresh.getLog());
        }
    }
    @Override
    public Result<Customer> deleteCustomerById(Long id) {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result(ERROR, new Customer(id,null,null), DELETE, String.format(EMPTY_BEAN, id));
        }
        /**removeOrderByCustomerCascade(id); реалиовать удаление всех заказов пользователя*/
        customers.removeIf(o -> o.getId().equals(id));
        Result<Customer> result = refresh(customers, XML_CUSTOMER_KEY);
        return new Result(result.getStatus(), new Customer(id,null,null), DELETE, result.getLog());
    }


    @Override
    public Result<Product> createProduct(Product product) {
        if (readProductById(product.getId()).getStatus().equals(ERROR)) {
            List<Product> list = getAll(Product.class, XML_PRODUCT_KEY);
            list.add(product);
            refresh(list, XML_PRODUCT_KEY);
            return new Result(SUCCESS, product, CREATE, String.format(PERSISTENCE_SUCCESS, product.getId()));
        }
        return new Result(ERROR, product, CREATE, String.format(PRESENT_BEAN, product.getId()));
    }


    @Override
    public Result<Product> readProductById(Long id) {
        Optional optional = getAll(Product.class, XML_PRODUCT_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return new Result(ERROR,new Customer(id,null,null),READ,NPE_PRODUCT);
        }
        else{
            return new Result(SUCCESS,optional.get(),READ,CSV_PRODUCT_KEY);

        }
    }


    @Override
    public Result<Product> updateProduct(Product product) {
        List<Product> products = getAll(Product.class, XML_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(product.getId()))) {
            return new Result(ERROR, product, UPDATE, String.format(EMPTY_BEAN, product.getId()));
        }
        products.removeIf(o -> o.getId().equals(product.getId()));
        products.add(product);
        Result<Product> refresh = refresh(products, XML_PRODUCT_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result(SUCCESS, product, UPDATE, String.format(UPDATE_SUCCESS, product.toString()));
        } else {
            return new Result(ERROR, product, UPDATE, refresh.getLog());
        }
    }

    @Override
    public Result<Product> deleteProductById(Long id) {
        List<Product> products = getAll(Product.class, XML_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result(ERROR, new Product(id,null,null), DELETE, String.format(EMPTY_BEAN, id));
        }

        products.removeIf(o -> o.getId().equals(id));
        Result<Product> result = refresh(products, XML_PRODUCT_KEY);
        return new Result(result.getStatus(), new Product(id,null,null), DELETE, result.getLog());
    }

    @Override
    public Result<Order> createOrder(Order order) {
        if (readOrderById(order.getId()).getStatus().equals(ERROR)) {
            Customer customer = readCustomerById(order.getId()).getBean();
            Product product = readProductById(order.getId()).getBean();
            if (customer.equals(null)) {
                return new Result(ERROR, order, CREATE, String.format(EMPTY_BEAN, order.getCustomer().getId()));
            }
            if (product.equals(null)) {
                return new Result(ERROR, order, CREATE, String.format(EMPTY_BEAN, order.getProduct().getId()));
            }
            /**Проверка на возраст
             *
             * if (customer.get().getAge() < product.get().getAgeLimit()) {
                return new Result<>(UNSUCCESSFUL, null, EXCEPTION_AGE_LIMIT);
            }*/
            List<Order> list = getAll(Order.class, XML_ORDER_KEY);
            list.add(order);
            refresh(list, XML_ORDER_KEY);
            return new Result(SUCCESS,order,CREATE,CREATE_SUCCESS_ORDER);

        }
        return new Result(ERROR, order, CREATE, String.format(PRESENT_BEAN, order.getId()));
    }


    @Override
    public Result<Order> readOrderById(Long id) {
        Optional optional = getAll(Order.class, XML_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
        if (optional.isEmpty()) {
            return new Result(ERROR, new Order(id,null,null), READ, NPE_ORDER);
        }
        else{
            return new Result(SUCCESS,optional.get(),READ,CSV_ORDER_KEY);}
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(order.getId()))) {
            return new Result(ERROR, order, UPDATE, String.format(EMPTY_BEAN, order.getId()));
        }
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        Result<Order> refresh = refresh(orders,XML_ORDER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result(SUCCESS, order, UPDATE, String.format(UPDATE_SUCCESS, order.toString()));
        } else {
            return new Result(ERROR, order, UPDATE, refresh.getLog());
        }
    }

    @Override
    public Result<Order> deleteOrderById(Long id) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result(ERROR, new Order(id,null,null), DELETE, String.format(EMPTY_BEAN, id));
        }

        /**Order order = getOrderById(id).get();
        removeProductByOrderCascade(order.getType(), order.getProduct().getId());*/
        orders.removeIf(o -> o.getId().equals(id));

        Result<Order> result = refresh(orders, XML_ORDER_KEY);
        if (result.getStatus() == SUCCESS) {
            result = new Result(SUCCESS, new Order(id,null,null), DELETE, ORDER_CLOSE);
        }
        return new Result(result.getStatus(), new Order(id,null,null), DELETE, result.getLog());
    }



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


/** ПЕРЕМЕСТИТЬ В class отдельный */

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

