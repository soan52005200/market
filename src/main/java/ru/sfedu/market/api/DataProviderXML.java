package ru.sfedu.market.api;

import ru.sfedu.market.bean.Customer;
import ru.sfedu.market.bean.Order;
import ru.sfedu.market.bean.Product;
import ru.sfedu.market.utils.Result;

import java.util.Optional;

public class DataProviderXML implements IDataProvider{
    @Override
    public Result<Customer> createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.empty();
    }

    @Override
    public Result<Customer> updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Result<Void> deleteCustomerById(Long id) {
        return null;
    }

    @Override
    public Result<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
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
    public Optional<Order> getOrderById(Long id) {
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


}
