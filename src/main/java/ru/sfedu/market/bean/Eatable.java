package ru.sfedu.market.bean;

import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name="Eatable")
public class Eatable extends Product{
    @Element
    @CsvCustomBindByPosition(position = 3,converter = IntegerConverterForOpenCsv.class)
    protected Integer bestBefore;

    //
    // Constructors
    //
    public Eatable () { }


    public Eatable(Long id, String name, ProductType type,Integer bestBefore) {
        this.id=id;
        this.name=name;
        this.type=type;
        this.bestBefore=bestBefore;
    }

    public Integer getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Integer bestBefore) {
        this.bestBefore = bestBefore;
    }

    @Override
    public String toString() {
        return "Eatable{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                "bestBefore=" + bestBefore +
                '}';
    }
}
