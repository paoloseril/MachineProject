package edu.dlsu.mobapde.machineproject.activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.values.Constants;
import edu.dlsu.mobapde.machineproject.values.Static;

public class BaseActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Fragment mainActivityFragment, viewExpensesFragment;
    private BroadcastReceiver alarmReceiver = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        mainActivityFragment = MainActivityFragment.newInstance();
        viewExpensesFragment = ViewExpensesFragment.newInstance();

        navigationView = findViewById(R.id.e_navigation);

        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.main_screen: {
                    loadFragment(mainActivityFragment);
                    return true;
                }
                case R.id.list_expenses: {
                    loadFragment(viewExpensesFragment);
                    return true;
                }
            }
            return false;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setSelectedItemId(R.id.list_expenses);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.UI_UPDATE_TAG);
        registerReceiver(alarmReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(alarmReceiver);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Static.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Static.activityPaused();
    }

    class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String name = getIntent().getStringExtra("Name");
            int id = getIntent().getIntExtra("Id", 0);

            long vibration = Static.getDatabaseInstance().dao().getExpense(id).getVibratorSeconds();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Static.getVibratorInstance().vibrate(VibrationEffect.createOneShot(vibration, VibrationEffect.DEFAULT_AMPLITUDE));
                if (Static.isActivityVisible()) {
                    alert(name);
                }
                else {
                    // createNotification
                }
            }
            else {
                Static.getVibratorInstance().vibrate(vibration);
                if (Static.isActivityVisible()) {
                    alert(name);
                }
                else {
                    // createNotification
                }
            }
        }
    }

    private void createNotificationChannel() {

    }

    private void createNotification() {

    }

    private void alert(String... expenseNames) {

        StringBuilder messageBuilder = new StringBuilder();

        if (expenseNames.length == 1) {
            messageBuilder.append("Item ");
        }
        else {
            messageBuilder.append("Items ");
        }

        for (int i = 0; i < expenseNames.length; i++) {
            if (i != expenseNames.length - 1)
                messageBuilder.append("'").append(expenseNames[i])
                        .append("'")
                        .append("and ");
            else
                messageBuilder.append("'").append(expenseNames[i]).append("'.");
        }

        if (expenseNames.length == 1) {
            messageBuilder.append(" is due today.");
        }
        else {
            messageBuilder.append(" are due today.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alarm Alarm")
                .setMessage(messageBuilder.toString())
                .setPositiveButton("OK", (dialogInterface, i) -> {
                });

        builder.show();
    }
}
