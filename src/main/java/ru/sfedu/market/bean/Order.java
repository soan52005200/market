package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Order")
public class Order {
    @Element
    @CsvBindByPosition(position = 0)
    private Long id;
    @Element
    @CsvCustomBindByPosition(position = 1,converter = ProductConverter.class)
    private Eatable eatable;
    @Element
    @CsvCustomBindByPosition(position = 1,converter = ProductConverter.class)
    private Uneatable uneatable;
    @Element
    @CsvCustomBindByPosition(position = 2, converter = CustomerConverter.class)
    private Customer customer;
    @Element



    public Order() { }

    public Order(Long id, Eatable eatable,Uneatable uneatable, Customer customer) {
        this.id = id;
        this.eatable = eatable;
        this.uneatable = uneatable;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Eatable getEatable() {
        return eatable;
    }

    public void setEatable(Eatable eatable) {
        this.eatable = eatable;
    }

    public Uneatable getUneatable() {
        return uneatable;
    }

    public void setUneatable(Uneatable uneatable) {
        this.uneatable = uneatable;
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
                ", eatable=" + eatable +
                ", uneatable=" + uneatable +
                ", customer=" + customer +
                '}';
    }
}
