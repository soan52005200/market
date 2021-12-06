package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;


public class ProductTypeConverter extends AbstractBeanField<Product> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        ProductType type = ProductType.valueOf(s);
        return type;
    }

}
