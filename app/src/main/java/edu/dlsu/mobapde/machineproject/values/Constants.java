package edu.dlsu.mobapde.machineproject.values;

public class Constants {

    // Types
    public static String TYPE_GAME = "game";
    public static String TYPE_GENERAL = "general";
    public static String TYPE_FOOD = "food";
    public static String TYPE_DEBT = "debt";
    public static String TYPE_BILL = "bill";

    // avoid instantiation
    private Constants() {

    }

    // For AlarmService and Notifications
    public static String UI_UPDATE_TAG = "ph.mobapde.machineproject";
    public static String UI_NOTIFICATION_CHANNEL = "ph.mobapde.notifs.machineproject";

}
