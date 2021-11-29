package ru.sfedu.market;

public final class Constants {

    public static final String ENV_PROPERTIES = "env";

    public static final String ENV_DEFAULT = "./src/main/resources/environment.properties";
    public static final String EMPTY_BEAN = "Объект с ID=%d не существует";
    public static final String LOG4J2_PROPERTIES = "log4j2.configurationFile";
    public static final String UPDATE_SUCCESS = "Объект %s успешно обновлен";


    public static final String FIRST_TEST_RESULT="Первый тест успешно пройдет";

    public static final String PERSISTENCE_SUCCESS = "Объект %s успешно сохранен";
    public static final String REMOVE_SUCCESS = "Объект успешно удалён";


    public static final String CSV_CUSTOMER_KEY = "csvCustomer";
    public static final String CSV_PRODUCT_KEY = "csvProduct";
    public static final String CSV_ORDER_KEY = "csvOrder";

    public static final String XML_CUSTOMER_KEY = "xmlCustomer";
    public static final String XML_PRODUCT_KEY = "xmlProduct";
    public static final String XML_ORDER_KEY = "xmlOrder";

    public static final String ORDER_CLOSE = "Заказ успешно закрыт";
    public static final String PRESENT_BEAN = "Объект с ID=%d уже существует";

    public static final String CREATE_SUCCESS_CUSTOMER = "Клиент создан";
    public static final String CREATE_SUCCESS_PRODUCT = "Продукт создан";
    public static final String CREATE_SUCCESS_ORDER = "Заказ создан";

    public static final String CREATE_ERROR_CUSTOMER = "Клиент не создан";
    public static final String CREATE_ERROR_PRODUCT = "Продукт не создан";
    public static final String CREATE_ERROR_ORDER = "Заказ не создан";

    public static final String NPE_CUSTOMER = "Такого клиента не существует";
    public static final String NPE_PRODUCT = "Такого продукта не существует";
    public static final String NPE_ORDER = "Такоего заказа не существует";

    public static final String JDBC_DRIVER = "jdbcDriver";
    public static final String JDBC_URL = "jdbcUrl";
    public static final String JDBC_USER = "jdbcUser";
    public static final String JDBC_PASSWORD = "jdbcPassword";

    public static final String CUSTOMER_INSERT = "INSERT INTO customer VALUES(%d, '%s', %d);";
    public static final String CUSTOMER_SELECT = "SELECT id, fio, age FROM customer WHERE id = %d";
    public static final String CUSTOMER_UPDATE = "UPDATE customer SET fio='%s', age=%d WHERE id = %d";
    public static final String CUSTOMER_DELETE = "DELETE FROM customer WHERE id = %d";

    public static final String PRODUCT_INSERT = "INSERT INTO product VALUES (%d, '%s', '%s');";
    public static final String PRODUCT_SELECT = "SELECT id , name , type FROM product WHERE id = %d;";
    public static final String PRODUCT_UPDATE = "UPDATE product SET name='%s', type='%s' WHERE id = %d;";
    public static final String PRODUCT_DELETE = "DELETE FROM product WHERE id = %d;";





    public static final String ORDER_INSERT = "INSERT INTO \"order\" VALUES (%d, %d, %d);";
    public static final String ORDER_UPDATE = "UPDATE \"order\" SET product_id=%d, customer_id=%d WHERE id = %d;";
    public static final String ORDER_SELECT = "SELECT id, product_id, customer_id FROM \"order\" WHERE id = %d;";
    public static final String ORDER_DELETE = "DELETE FROM \"order\" WHERE id = %d;";
    public static final String ORDER_SELECT_CUSTOMER = "SELECT id, product_id, customer_id FROM \"order\" WHERE customer_id = %d;";

    /**
     *
     *

     public static final String UNEATABLE_INSERT = "INSERT INTO uneatable VALUES (%d, '%s', '%s', '%s', %d, '%s');";
     public static final String UNEATABLE_SELECT = "SELECT id, receiptDate, name, manufacturer, ageLimit, type FROM uneatable WHERE id = %d;";
     public static final String UNEATABLE_DELETE = "DELETE FROM uneatable WHERE id = %d;";
     public static final String UNEATABLE_DELETE_CASCADE = "DELETE FROM \"order\" WHERE product_id = %d and type_='UNEATABLE';";


     public static final String EATABLE_SELECT_ALL = "SELECT id, receiptDate, name, manufacturer, ageLimit, type, bestBefore FROM eatable;";
     public static final String EATABLE_DELETE = "DELETE FROM eatable WHERE id = %d;";
     public static final String EATABLE_DELETE_CASCADE = "DELETE FROM \"order\" WHERE product_id = %d and type_='EATABLE';";

     */

}
