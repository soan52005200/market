package ru.sfedu.market;

public final class Constants {

    public static final String ENV_PROPERTIES = "env";

    public static final String ENV_DEFAULT = "./src/main/resources/environment.properties";
    public static final String EMPTY_BEAN = "Объект с ID=%d не существует";
    public static final String LOG4J2_PROPERTIES = "log4j2.configurationFile";
    public static final String UPDATE_SUCCESS = "Объект %s успешно обновлен";

    public static final String CLI_CSV = "csv";
    public static final String CLI_XML = "xml";
    public static final String CLI_JDBC = "jdbc";
    public static final String CLI_EXCEPTION = "Ключа %s не существует";
    public static final String CLI_CREATE = "create";
    public static final String CLI_DEL = "del";
    public static final String CLI_UPD = "upd";
    public static final String CLI_READ = "read";
    public static final String CLI_CUSTOMER = "customer";
    public static final String CLI_EATABLE = "eatable";
    public static final String CLI_UNEATABLE = "uneatable";
    public static final String CLI_ORDER = "order";

    public static final String SUCCESS_UPDATE = "Объект успешно обновлен";
    public static final String PERSISTENCE_SUCCESS = "Объект %s успешно сохранен";
    public static final String REMOVE_SUCCESS = "Объект успешно удалён";
    public static final String USER="user";

    public static final String CSV_CUSTOMER_KEY = "csvCustomer";
    public static final String CSV_EATABLE_KEY = "csvEatable";
    public static final String CSV_UNEATABLE_KEY = "csvUneatable";
    public static final String CSV_ORDER_KEY = "csvOrder";

    public static final String XML_CUSTOMER_KEY = "xmlCustomer";
    public static final String XML_UNEATABLE_KEY = "xmlUneatable";
    public static final String XML_EATABLE_KEY = "xmlEatable";
    public static final String XML_ORDER_KEY = "xmlOrder";

    public static final String AGE_ERROR="Покупателю меньше 18";

    public static final String ORDER_CLOSE = "Заказ успешно закрыт";
    public static final String PRESENT_BEAN = "Объект с ID=%d уже существует";

    public static final String CREATE_SUCCESS_CUSTOMER = "Клиент создан";
    public static final String CREATE_SUCCESS_PRODUCT = "Продукт создан";
    public static final String CREATE_SUCCESS_ORDER = "Заказ создан";

    public static final String UPDATE_SUCCESS_CUSTOMER = "Клиент обновлен";
    public static final String UPDATE_SUCCESS_PRODUCT = "Продукт обновлен";
    public static final String UPDATE_SUCCESS_ORDER = "Заказ обновлен";

    public static final String UPDATE_ERROR_CUSTOMER = "Клиент обновлен";
    public static final String UPDATE_ERROR_PRODUCT = "Продукт обновлен";
    public static final String UPDATE_ERROR_ORDER = "Заказ обновлен";

    public static final String CREATE_ERROR_CUSTOMER = "Клиент не создан";
    public static final String CREATE_ERROR_PRODUCT = "Продукт не создан";
    public static final String CREATE_ERROR_ORDER = "Заказ не создан";

    public static final String NPE_CUSTOMER = "Такого клиента не существует";
    public static final String NPE_EATABLE = "Такого продукта не существует";
    public static final String NPE_UNEATABLE = "Такого продукта не существует";
    public static final String NPE_ORDER = "Такоего заказа не существует";
    public static final String NPE_OBJECT = "Такоего объекта не существует";

    public static final String EXIST_CUSTOMER = "Клиент существует";
    public static final String EXIST_PRODUCT = "Продукт существует";
    public static final String EXIST_ORDER = "Заказ существует";
    public static final String EXIST_OBJECT = "Объект существует";

    public static final String JDBC_DRIVER = "jdbcDriver";
    public static final String JDBC_URL = "jdbcUrl";
    public static final String JDBC_USER = "jdbcUser";
    public static final String JDBC_PASSWORD = "jdbcPassword";

    public static final String CUSTOMER_INSERT = "INSERT INTO customer VALUES(%d, '%s', %d);";
    public static final String CUSTOMER_SELECT = "SELECT id, fio, age FROM customer WHERE id = %d";
    public static final String CUSTOMER_UPDATE = "UPDATE customer SET fio='%s', age=%d WHERE id = %d";
    public static final String CUSTOMER_DELETE = "DELETE FROM customer WHERE id = %d";

    public static final String EATABLE_INSERT = "INSERT INTO eatable VALUES (%d, '%s', '%s',%d);";
    public static final String EATABLE_SELECT = "SELECT id , name , type, bestbefore FROM eatable WHERE id = %d;";
    public static final String EATABLE_UPDATE = "UPDATE eatable SET name='%s', type='%s',bestbefore=%d WHERE id = %d;";
    public static final String EATABLE_DELETE = "DELETE FROM eatable WHERE id = %d;";

    public static final String UNEATABLE_INSERT = "INSERT INTO uneatable VALUES (%d, '%s', '%s',%d);";
    public static final String UNEATABLE_SELECT = "SELECT id , name , type, guarantee FROM uneatable WHERE id = %d;";
    public static final String UNEATABLE_UPDATE = "UPDATE uneatable SET name='%s', type='%s',guarantee=%d WHERE id = %d;";
    public static final String UNEATABLE_DELETE = "DELETE FROM uneatable WHERE id = %d;";

    public static final String ORDER_INSERT = "INSERT INTO \"order\" VALUES (%d, %d, %d, %d);";
    public static final String ORDER_UPDATE = "UPDATE \"order\" SET eatable_id=%d,uneatable_id=%d, customer_id=%d WHERE id = %d;";
    public static final String ORDER_SELECT = "SELECT id, eatable_id, uneatable_id, customer_id FROM \"order\" WHERE id = %d;";
    public static final String ORDER_DELETE = "DELETE FROM \"order\" WHERE id = %d;";

    public static final String ORDER_SELECT_EATABLE = "SELECT id FROM \"order\" WHERE eatable_id = %d;";
    public static final String ORDER_SELECT_UNEATABLE = "SELECT id FROM \"order\" WHERE uneatable_id = %d;";
    public static final String ORDER_SELECT_CUSTOMER = "SELECT id FROM \"order\" WHERE customer_id = %d;";

    public static final String ORDER_DELETE_CASCADE_BY_UNEATABLE = "DELETE FROM \"order\" WHERE uneatable_id = %d;";
    public static final String ORDER_DELETE_CASCADE_BY_EATABLE = "DELETE FROM \"order\" WHERE eatable_id = %d;";
    public static final String ORDER_DELETE_CASCADE_BY_CUSTOMER = "DELETE FROM \"order\" WHERE customer_id = %d;";


}
