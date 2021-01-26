package com.prefab.sales.utils;

public class Validation {

    public static void onlyLetters(String string) {
        if (!Validation.checkMatching(string, "^[a-zA-Z ]+$"))
            throw new IllegalArgumentException("Only letters acceptable.");
    }

    public static void onlyEmail(String email) {
        if (!Validation.checkMatching(email, "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$"))
            throw new IllegalArgumentException("Invalid email.");
    }

    public static void onlyPhoneNumber(String phone) {
        if (!Validation.checkMatching(phone, "^[0-9]{1,20}$"))
            throw new IllegalArgumentException("Phone number must have only digits.");
    }

    public static void alphanumericValue(String string) {
        if (!Validation.checkMatching(string, "^[a-zA-Z 0-9_+-/\\.]*$"))
            throw new IllegalArgumentException("Only alphanumeric values.");
    }

    public static void greaterOrEqualZero(double number) {
        if (number < 0 )
            throw new IllegalArgumentException("Value cannot be less than 0.");
    }

    private static boolean checkMatching(String string, String regexp) {
        return string.trim().matches(regexp);
    }

}
