package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.io.IOException;

import static ru.sfedu.market.Constants.NPE_CUSTOMER;
import static ru.sfedu.market.utils.Status.ERROR;

public class CustomerConverter extends AbstractBeanField<Customer> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {


        try {
            Customer customer = null;
            customer = csv.readCustomerById(Long.parseLong(s)).getBean();
            if (csv.readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_CUSTOMER);
            }
            return customer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        try {
            Customer customer = (Customer) value;
            if (csv.readCustomerById(customer.getId()).getStatus().equals(ERROR)) {
                throw new NullPointerException(NPE_CUSTOMER);

            }
            return customer.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
