package com.example.propelrrhandsonexam;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTime {

    private long dateTime = new Date().getTime();

    public DateTime() {
    }

    public DateTime(String dateString) {
        Date date = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        dateTime = date.getTime();
    }

    public String getDate() {
        return DateFormat.format("yyyy/MM/dd", new Date(dateTime)).toString();
    }

    public long getDateTimeValue() {
        return dateTime;
    }
}
