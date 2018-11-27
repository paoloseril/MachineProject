package edu.dlsu.mobapde.machineproject.values;

public class Constants {

    // Types
    public static final String TYPE_GAME = "game";
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

}
