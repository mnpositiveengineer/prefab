package com.prefab.sales.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTestSuite {

    @ParameterizedTest
    @ValueSource(strings = {"test", "test test", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSUVWXYZ"})
    void onlyLettersPositiveValidation(String string) {
        Validation.onlyLetters(string);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "@", "!", "#", "$", "%", "^", "&", "*", "(", "<", "?"})
    void onlyLettersNegativeValidation(String string) {
        assertThrows(IllegalArgumentException.class, ()-> Validation.onlyLetters(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc123@gmail.com",
                            "abc.def@microsoft.org",
                            "test-test@test-test.io",
                            "test_test@test.com",
                            "TEST@123.eu"})
    void onlyEmailPositiveValidation(String string) {
        Validation.onlyEmail(string);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc 123@gmail.com",
                            "<abc>@microsoft.org",
                            "test.test.com",
                            "test@test",
                            "test@test test.com"})
    void onlyEmailNegativeValidation(String string) {
        assertThrows(IllegalArgumentException.class, ()-> Validation.onlyEmail(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1",
                            "123456789",
                            "0048123456789",
                            "11111111111111111111"})
    void onlyPhoneNumberPositiveValidation(String string) {
        Validation.onlyPhoneNumber(string);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ",
                            "abc",
                            "0048123-456789",
                            "111111111111111111111"})
    void onlyPhoneNumberNegativeValidation(String string) {
        assertThrows(IllegalArgumentException.class, ()-> Validation.onlyPhoneNumber(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc123",
                            "00-000",
                            "Nowaka-Jana 32/15",
                            "Czysta 5j/2",
                            "Test S.A", "Credit-Suisse", "Goldman-Sachs"})
    void alphanumericValuePositiveValidation(String string) {
        Validation.alphanumericValue(string);
    }

    @ParameterizedTest
    @ValueSource(strings = {"<script>", "@", "!", "#", "$", "%", "^", "&", "*", "(", "<", "?"})
    void alphanumericValueNegativeValidation(String string) {
        assertThrows(IllegalArgumentException.class, ()-> Validation.alphanumericValue(string));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 1})
    void onlyPositiveValuePositiveValidation(double number) {
        Validation.greaterOrEqualZero(number);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -2})
    void onlyPositiveValueNegativeValidation(double number) {
        assertThrows(IllegalArgumentException.class, ()-> Validation.greaterOrEqualZero(number));
    }
}