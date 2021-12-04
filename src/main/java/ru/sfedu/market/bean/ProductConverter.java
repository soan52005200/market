package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;
import ru.sfedu.market.utils.Result;

import java.util.Optional;

import static ru.sfedu.market.Constants.NPE_CUSTOMER;
import static ru.sfedu.market.Constants.NPE_PRODUCT;
import static ru.sfedu.market.utils.Status.ERROR;


public class ProductConverter extends AbstractBeanField<Product> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Product product = csv.readProductById(Long.parseLong(s)).getBean();

        if (csv.readProductById(product.getId()).getStatus().equals(ERROR)) {
            throw new NullPointerException(NPE_PRODUCT);
        }
        return product;
    }
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Product product = (Product) value;
        if (csv.readProductById(product.getId()).getStatus().equals(ERROR)) {
            throw new NullPointerException(NPE_PRODUCT);
        }
        return product.getId().toString();
    }

}
