package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.io.IOException;

import static ru.sfedu.market.Constants.NPE_UNEATABLE;
import static ru.sfedu.market.utils.Status.ERROR;


public class UneatableConverter extends AbstractBeanField<Uneatable> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        try {
            Uneatable uneatable = csv.readUneatableById(Long.parseLong(s)).getBean();
            if (csv.readUneatableById(uneatable.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_UNEATABLE);
            }
            return uneatable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        try {
            Uneatable uneatable = (Uneatable) value;
            if (csv.readUneatableById(uneatable.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_UNEATABLE);

            }
            return uneatable.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
