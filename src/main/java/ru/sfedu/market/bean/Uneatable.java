package ru.sfedu.market.bean;

import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name="Uneatable")
public class Uneatable extends Product{

    @Element
    @CsvCustomBindByPosition(position = 3,converter = IntegerConverterForOpenCsv.class)
    protected Integer guarantee;


    public Uneatable () { }

    public Uneatable(Long id, String name, ProductType type,Integer guarantee) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.guarantee = guarantee;

    }

    public Integer getGuarantee() {return guarantee;
    }

    public void setGuarantee(Integer guarantee) {this.guarantee = guarantee;
    }

    @Override
    public String toString() {
        return "Uneatable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", guarantee=" + guarantee +
                '}';
    }
}
