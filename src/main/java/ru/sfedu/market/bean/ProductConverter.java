package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.io.IOException;

import static ru.sfedu.market.Constants.NPE_PRODUCT;
import static ru.sfedu.market.utils.Status.ERROR;


public class ProductConverter extends AbstractBeanField<Product> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        try {
            Product product = null;
            product = csv.readProductById(Long.parseLong(s)).getBean();if (csv.readProductById(product.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_PRODUCT);
            }
            return product;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        try {
            Product product = (Product) value;
            if (csv.readProductById(product.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_PRODUCT);

            }
            return product.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
