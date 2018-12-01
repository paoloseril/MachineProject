package edu.dlsu.mobapde.machineproject.database;

import android.app.Application;

public class Database extends Application {

    private static ExpenseDatabase instance;

    public static ExpenseDatabase getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = ExpenseDatabase.getDatabase(getApplicationContext());
    }
}
