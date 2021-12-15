package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Uneatable")
public class Eatable extends Product{
    @Element
    @CsvBindByPosition(position = 3)
    private int bestbefore;

    //
    // Constructors
    //
    public Eatable () { };

    public Eatable(Long id, String name, ProductType type,int bestbefore) {
        this.id=id;
        this.name=name;
        this.type=type;
        this.bestbefore=bestbefore;
    }

    public int getBestBefore() {
        return bestbefore;
    }

    public void setBestBefore(int bestBefore) {
        this.bestbefore = bestbefore;
    }
}
