package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name="Uneatable")
public class Uneatable extends Product{

        @Element
        @CsvBindByPosition(position = 3)
        private int guarantee;

        //
        // Constructors
        //
        public Uneatable () { };

    public Uneatable(Long id, String name, ProductType type,int guarantee) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.guarantee = guarantee;

    }

    public int getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }
}
