package com.example.propelrrhandsonexam;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Credentials {

    public static int REQUIRED_PERSON_NAME_LENGTH = 2;
    public static int REQUIRED_PHONE_NUMBER_LENGTH_PH = 11;

    public static String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public static String fullTrim(String string) {
        if (string == null) return null;

        String[] splitStrings = string.split(" ");
        List<String> trimmedStrings = new ArrayList<>();

        for (String splitString : splitStrings)
            if (!splitString.equals(" ") && splitString.trim().length() != 0)
                trimmedStrings.add(splitString.trim());

        return TextUtils.join(" ", trimmedStrings);
    }

    public static boolean isEmpty(String string) {
        return string == null || fullTrim(string).length() == 0;
    }

    public static boolean isValidLength(String string, int minLength, int maxLength) {
        return !isEmpty(string) && fullTrim(string).length() >= minLength &&
                (maxLength == 0 || fullTrim(string).length() <= maxLength);
    }

    public static boolean isValidPersonName(String string) {
        return !isEmpty(string) && string.matches("[A-Za-z.,\\s]*");
    }

    public static boolean isValidPhoneNumber(String phoneNumber, int length) {
        return !isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches()
                && phoneNumber.length() == length;
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        return !isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public static String getUniqueId() {
        StringBuilder idBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            Random rnd = new Random();
            idBuilder.append(ALPHA_NUMERIC.charAt(rnd.nextInt(ALPHA_NUMERIC.length())));
        }

        return idBuilder.toString();
    }
}
