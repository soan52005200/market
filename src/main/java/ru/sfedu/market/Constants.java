package ru.sfedu.market;

public final class Constants {

    public static final String ENV_PROPERTIES = "env";

    public static final String ENV_DEFAULT = "./src/main/resources/environment.properties";
    public static final String EMPTY_BEAN = "Объект с ID=%d не существует";
    public static final String LOG4J2_PROPERTIES = "log4j2.configurationFile";
    public static final String UPDATE_SUCCESS = "Объект %s успешно обновлен";
    public static final String CSV_ORDER_KEY = "csvOrder";

    public static final String FIRST_TEST_RESULT="Первый тест успешно пройдет";

    public static final String PERSISTENCE_SUCCESS = "Объект %s успешно сохранен";
    public static final String REMOVE_SUCCESS = "Объект успешно удалён";

    public static final String CSV_CUSTOMER_KEY = "csvCustomer";
    public static final String CSV_PRODUCT_KEY = "csvProduct";
    public static final String PRESENT_BEAN = "Клиент с ID=%d уже существует";

    public static final String NPE_CUSTOMER = "Такого клиента не существует";
    public static final String NPE_PRODUCT = "Такого продукта не существует";
}
