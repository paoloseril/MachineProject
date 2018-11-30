package edu.dlsu.mobapde.machineproject.values;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constants {

    // Types
    public static final String TYPE_EDUCATION = "education";
    public static final String TYPE_GENERAL = "general";
    public static final String TYPE_FOOD = "food";
    public static final String TYPE_DEBT = "debt";
    public static final String TYPE_BILL = "bill";

    // avoid instantiation
    private Constants() {

    }

    // For AlarmService and Notifications
    public final static String UI_UPDATE_TAG = "ph.mobapde.machineproject";
    public final static String UI_NOTIFICATION_CHANNEL = "ph.mobapde.notifs.machineproject";

    // request codes for accessing and using the camera and accessing the photo library
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int ACCESS_PHOTO_LIBRARY_REQUEST_CODE = 1046;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
