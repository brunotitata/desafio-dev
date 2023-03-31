package br.com.bycoders.application.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CPFUtilsTest {

    @Test
    public void validaCPF() {

        assertEquals(true, CPFUtils.isValid("72998832016"));
        assertEquals(false, CPFUtils.isValid("72998832017"));

    }

}
