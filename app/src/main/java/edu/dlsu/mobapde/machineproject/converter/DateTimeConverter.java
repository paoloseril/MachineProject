package edu.dlsu.mobapde.machineproject.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeConverter {

    // Convert milliseconds to date and time in string format
    public static String toDate(Long value) {
        if (value != null) {

            Date date = new Date(value);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            return formatter.format(date);
        }
        return null;
    }

    // Convert datetime in string to milliseconds
    public static Long toMilliseconds(String value) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return value == null ? null : formatter.parse(value).getTime();
        } catch (ParseException e) {
            return null;
        }
    }


}
