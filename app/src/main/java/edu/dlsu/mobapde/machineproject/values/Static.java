package edu.dlsu.mobapde.machineproject.values;

import android.app.AlarmManager;
import android.app.Application;
import android.os.Vibrator;

import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;

public class Static extends Application {

    private static ExpenseDatabase instance;
    private static AlarmManager manager;
    private static Vibrator vibrator;

    public static Vibrator getVibratorInstance() {
        return vibrator;
    }

    public static AlarmManager getManagerInstance() {
        return manager;
    }

    public static ExpenseDatabase getDatabaseInstance() {
        return instance;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = ExpenseDatabase.getDatabase(getApplicationContext());
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        activityVisible = true;
    }
}
