package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

public class Order {

    @CsvBindByPosition(position = 0)
    private Long id;

    @CsvCustomBindByPosition(position = 1,converter = ProductConverter.class)
    private Product product;

    @CsvCustomBindByPosition(position = 2, converter = CustomerConverter.class)
    private Customer customer;

    public Order() { }

    public Order(Long id, Product product, Customer customer) {
        this.id = id;
        this.product = product;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product=" + product +
                ", customer=" + customer +
                '}';
    }
}
