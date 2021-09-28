package ru.sfedu.market.api;

import ru.sfedu.market.Constants.*;
import org.junit.jupiter.api.Test;
import ru.sfedu.market.utils.ConfigurationUtil;

import java.io.IOException;

import static ru.sfedu.market.Constants.FIRST_TEST_RESULT;

class TestPrintConstantTest {
    @Test
    void name() {
        System.out.println(FIRST_TEST_RESULT);
    }
}