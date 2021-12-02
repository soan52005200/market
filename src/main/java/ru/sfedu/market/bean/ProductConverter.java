package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.util.Optional;

import static ru.sfedu.market.Constants.NPE_CUSTOMER;
import static ru.sfedu.market.Constants.NPE_PRODUCT;


public class ProductConverter extends AbstractBeanField<Product> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Product product = csv.readProductById(Long.parseLong(s)).getBean();
        if (product.equals(null)) {
            throw new NullPointerException(NPE_PRODUCT);
        }
        return optional.get();
    }
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Optional<Product> optional = csv.readProductById(((Product) value).getId());
        if (optional.isEmpty()) {
            throw new NullPointerException(NPE_PRODUCT);
        }
        return String.valueOf(optional.get().getId());
    }

}
