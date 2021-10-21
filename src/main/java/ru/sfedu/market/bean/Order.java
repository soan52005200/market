package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;

public class Order {

    @CsvBindByPosition(position = 0)
    private Long id;

    @CsvBindByPosition(position = 1)
    private Product product;

    @CsvBindByPosition(position = 2)
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
