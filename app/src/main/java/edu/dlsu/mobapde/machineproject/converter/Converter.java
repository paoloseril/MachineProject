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

    // convert a File object to a Bitmap image
    // to be used in setting image for ImageView
    // ImageView img = (ImageView) findViewById();
    // img.setImageBitmap(<the converted Bitmap object>);
    //
    public static Bitmap toImage(File imageFile) {
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
    }

    //public static byte[] toByteArray(Bitmap bitmap) {
      //  Bitmap bitmap = BitmapFactory.decodeResource();
        //ByteArrayOutputStream opstream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, opstream);
    //    return opstream.toByteArray();
    //}

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
