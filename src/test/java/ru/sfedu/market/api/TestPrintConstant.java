package ru.sfedu.market.api;
import ru.sfedu.market.utils.ConfigurationUtil;

import java.io.IOException;


public class TestPrintConstant {
    public static void main(String... args) throws IOException {
        System.out.println(ConfigurationUtil.getConfigurationEntry("test1"));


    }
}
