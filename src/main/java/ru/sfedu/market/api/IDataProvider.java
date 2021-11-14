package ru.sfedu.market.api;


import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface IDataProvider {

    /**
     * Регистрация клиента в сервисе
     * @param customer
     * @return
     */

    Result<Customer> createCustomer(Customer customer);

    /**
     * Получить информацию о клиенте по его id
     * @param id
     * @return
     */

    Optional<Customer> readCustomerById(Long id);

    /**
     * Изменение информации о клиенте
     * @param customer
     * @return
     */

    Result<Customer> updateCustomer(Customer customer);

    /**
     * Удаление клиента из сервиса
     * @param id
     * @return
     */

    Result<Void> deleteCustomerById(Long id);

    /**
     * Регистрация продукта в сервисе
     * @param product
     * @return
     */

    Result<Product> createProduct(Product product);

    /**
     * Получить информацию о продукте по его id
     * @param id
     * @return
     */

    Optional<Product> readProductById(Long id);

    /**
     * Изменение информации о продукте
     * @param product
     * @return
     */

    Result<Product> updateProduct(Product product);

    /**
     * Удаление продукта из сервиса
     * @param id
     * @return
     */

    Result<Void> deleteProductById(Long id);


    /**
     * Создать заказ
     * @param order
     * @return
     */

    Result<Order> createOrder(Order order);

    /**
     * Получить информацию о заказе
     * @param id
     * @return
     */

    Optional<Order> readOrderById(Long id);

    /**
     * Изменить заказ
     * @param order
     * @return
     */
    Result<Order> updateOrder(Order order);

    /**
     * Закрыть заказ
     * @param id
     * @return
     */

    Result<Void> deleteOrderById(Long id);



}
