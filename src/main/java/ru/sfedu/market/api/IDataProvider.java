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

    Result<Customer> register(Customer customer);

    /**
     * Получить информацию о клиенте по его id
     * @param id
     * @return
     */

    Optional<Customer> getCustomerById(Long id);

    /**
     * Изменение информации о клиенте
     * @param customer
     * @return
     */

    Result<Customer> editCustomer(Customer customer);

    /**
     * Удаление клиента из сервиса
     * @param id
     * @return
     */

    Result<Void> removeCustomerById(Long id);


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

    Optional<Order> getOrderById(Long id);

    /**
     * Изменить заказ
     * @param order
     * @return
     */
    Result<Order> editOrder(Order order);

    /**
     * Закрыть заказ
     * @param id
     * @return
     */

    Result<Void> closeOrderById(Long id);

    /**
     * Регистрация клиента в сервисе
     * @param product
     * @return
     */

    Result<Product> register(Product product);

    /**
     * Получить информацию о клиенте по его id
     * @param id
     * @return
     */

    Optional<Product> getProductById(Long id);

    /**
     * Изменение информации о клиенте
     * @param product
     * @return
     */

    Result<Product> editProduct(Product product);

    /**
     * Удаление клиента из сервиса
     * @param id
     * @return
     */

    Result<Void> removeProductById(Long id);

}
