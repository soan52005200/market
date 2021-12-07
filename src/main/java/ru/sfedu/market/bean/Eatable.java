package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;
@Root(name="Uneatable")
public class Eatable extends Product{
    @Element
    @CsvBindByPosition(position = 3)
    private int bestBefore;

    //
    // Constructors
    //
    public Eatable () { };

    public Eatable(Long id, String name, ProductType type,Integer bestBefore) {
        this.id=id;
        this.name=name;
        this.type=type;
        this.bestBefore=bestBefore;
    }

    public int getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(int bestBefore) {
        this.bestBefore = bestBefore;
    }
}
