package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.util.Optional;

import static ru.sfedu.market.Constants.NPE_CUSTOMER;

public class CustomerConverter extends AbstractBeanField<Customer> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Customer customer = csv.readCustomerById(Long.parseLong(s)).getBean();
        if (customer.equals(null)) {
            throw new NullPointerException(NPE_CUSTOMER);
        }
        return customer;
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Customer customer = csv.readCustomerById(((Product) value).getId()).getBean();
        if (customer.equals(null)) {
            throw new NullPointerException(NPE_CUSTOMER);
        }
        return customer.toString();
    }
}
