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
import static ru.sfedu.market.utils.Status.*;


public class DataProviderXML implements IDataProvider{

    private static final Logger log = LogManager.getLogger(DataProviderXML.class);

    @Override
    public Result<Customer> createCustomer(Customer customer) {
        if (getCustomerById(customer.getId()).isEmpty()) {
            List<Customer> list = getAll(Customer.class, XML_CUSTOMER_KEY);
            list.add(customer);
            return refresh(list, XML_CUSTOMER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, customer, String.format(PRESENT_BEAN, customer.getId()));
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return getAll(Customer.class, XML_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, customer.getId()));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Customer> refresh = refresh(customers, XML_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, customer, String.format(UPDATE_SUCCESS, customer.toString()));
        } else {
            return new Result<>(ERROR, customer, refresh.getLog());
        }
    }
    @Override
    public Result<Void> deleteCustomerById(Long id) {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        /**removeOrderByCustomerCascade(id); реалиовать удаление всех заказов пользователя*/
        customers.removeIf(o -> o.getId().equals(id));
        Result<Customer> result = refresh(customers, XML_CUSTOMER_KEY);
        return new Result<>(result.getStatus(), null, result.getLog());
    }



    @Override
    public Result<Product> createProduct(Product product) {
        if (getProductById(product.getId()).isEmpty()) {
            List<Product> list = getAll(Product.class, XML_PRODUCT_KEY);
            list.add(product);
            return refresh(list, XML_PRODUCT_KEY);
        }
        return new Result<>(UNSUCCESSFUL, product, String.format(PRESENT_BEAN, product.getId()));
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return getAll(Product.class, XML_PRODUCT_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Result<Product> updateProduct(Product product) {
        List<Product> products = getAll(Product.class, XML_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(product.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, product.getId()));
        }
        products.removeIf(o -> o.getId().equals(product.getId()));
        products.add(product);
        Result<Product> refresh = refresh(products, XML_PRODUCT_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, product, String.format(UPDATE_SUCCESS, product.toString()));
        } else {
            return new Result<>(ERROR, product, refresh.getLog());
        }
    }

    @Override
    public Result<Void> deleteProductById(Long id) {
        List<Product> products = getAll(Product.class, XML_PRODUCT_KEY);
        if (products.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }

        products.removeIf(o -> o.getId().equals(id));
        Result<Product> result = refresh(products, XML_PRODUCT_KEY);
        return new Result<>(result.getStatus(), null, result.getLog());
    }

    @Override
    public Result<Order> createOrder(Order order) {
        if (getOrderById(order.getId()).isEmpty()) {
            Optional<Customer> customer = getCustomerById(order.getCustomer().getId());
            Optional<? extends Product> product = getProductById(order.getProduct().getId());
            if (customer.isEmpty()) {
                return new Result<Order>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, order.getCustomer().getId()));
            }
            if (product.isEmpty()) {
                return new Result<Order>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, order.getProduct().getId()));
            }
            /**Проверка на возраст
             *
             * if (customer.get().getAge() < product.get().getAgeLimit()) {
                return new Result<>(UNSUCCESSFUL, null, EXCEPTION_AGE_LIMIT);
            }*/
            List<Order> list = getAll(Order.class, XML_ORDER_KEY);
            list.add(order);
            return refresh(list, XML_ORDER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, order, String.format(PRESENT_BEAN, order.getId()));
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return getAll(Order.class, XML_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(order.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, order.getId()));
        }
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        Result<Void> refresh = deleteOrderById(order.getId());
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, order, String.format(UPDATE_SUCCESS, order.toString()));
        } else {
            return new Result<>(ERROR, order, refresh.getLog());
        }
    }

    @Override
    public Result<Void> deleteOrderById(Long id) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }

        /**Order order = getOrderById(id).get();
        removeProductByOrderCascade(order.getType(), order.getProduct().getId());*/
        orders.removeIf(o -> o.getId().equals(id));

        Result<Order> result = refresh(orders, XML_ORDER_KEY);
        if (result.getStatus() == SUCCESS) {
            result = new Result<>(SUCCESS, null, ORDER_CLOSE);
        }
        return new Result<>(result.getStatus(), null, result.getLog());
    }



    private <T> Result<T> refresh(List<T> container, String key) {
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            serializer.write(new Container<T>(container), fileWriter);

            return new Result<T>(SUCCESS, null, PERSISTENCE_SUCCESS);
        } catch (Exception exception) {
            log.error(exception);
            return new Result<T>(ERROR, null, exception.getMessage());
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
