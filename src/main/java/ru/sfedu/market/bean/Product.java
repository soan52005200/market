package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Element;

import javax.swing.text.html.Option;
import java.util.Optional;


public class Product extends Object {
    @Element
    @CsvBindByPosition(position = 0)
    protected Long id;
    @Element
    @CsvBindByPosition(position = 1)
    protected String name;
    @Element
    @CsvCustomBindByPosition(position = 2, converter = ProductTypeConverter.class)
    protected ProductType type;


    public Product() { }

    public Product(Long id, String name, ProductType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }
    public void setType(ProductType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
