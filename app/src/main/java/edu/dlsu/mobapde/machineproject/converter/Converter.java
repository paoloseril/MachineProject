package edu.dlsu.mobapde.machineproject.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Converter {

    // avoid instiantiation
    private Converter() { }

    // convert byte array to Bitmap image
    // to be used in setting image for ImageView
    // ImageView img = (ImageView) findViewById();
    // img.setImageBitmap(<the converted Bitmap object>);
    //
    public static Bitmap toImage(byte[] imageByte) {
        return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
    }

    // convert Bitmap object to byte array
    public static byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // Convert milliseconds to date and time in string format
    public static String toDate(Long value) {
        if (value != null) {

            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy|h:mm a", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));

            return formatter.format(value);
        }
        return null;
    }

    // Convert datetime in string to milliseconds
    public static Long toMilliseconds(String value) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy|h:mm a", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        try {
            return value == null ? null : formatter.parse(value).getTime();
        } catch (ParseException e) {
            return null;
        }
    }


}
