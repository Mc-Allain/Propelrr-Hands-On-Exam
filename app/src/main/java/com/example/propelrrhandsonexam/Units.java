package com.example.propelrrhandsonexam;

public class Units {

    private static final long MS_IN_YEAR = 31556952000L;

    public static double msToYear(double milliseconds) {
        return milliseconds / MS_IN_YEAR;
    }

}
