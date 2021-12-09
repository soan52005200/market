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


public class EatableConverter extends AbstractBeanField<Eatable> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        try {
            Eatable eatable = csv.readEatableById(Long.parseLong(s)).getBean();
            if (csv.readEatableById(eatable.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_PRODUCT);
            }
            return eatable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        try {
            Eatable eatable = (Eatable) value;
            if (csv.readEatableById(eatable.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_PRODUCT);

            }
            return eatable.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
