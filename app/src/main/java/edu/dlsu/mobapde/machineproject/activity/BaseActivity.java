package edu.dlsu.mobapde.machineproject.activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.entity.Expense;
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
        String fragmentName = getIntent().getStringExtra("FragmentName");
        if (fragmentName != null) {
            if (fragmentName.equals(MainActivityFragment.class.getSimpleName())) {
                navigationView.setSelectedItemId(R.id.main_screen);
            }
            else {
                navigationView.setSelectedItemId(R.id.list_expenses);
            }
        }
        else {
            navigationView.setSelectedItemId(R.id.list_expenses);
        }
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

            String name = intent.getStringExtra("Name");
            int id = intent.getIntExtra("Id", 0);

            Log.d("Name", name);

            long vibration = Static.getDatabaseInstance().dao().getExpense(id).getVibratorSeconds();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Static.getVibratorInstance().vibrate(VibrationEffect.createOneShot(vibration, VibrationEffect.DEFAULT_AMPLITUDE));
                if (Static.isActivityVisible()) {
                    alert(name);
                }
                else {
                    createNotificationChannel();
                    createNotification(name);
                }
            }
            else {
                Static.getVibratorInstance().vibrate(vibration);
                if (Static.isActivityVisible()) {
                    alert(name);
                }
                else {
                    createNotificationChannel();
                    createNotification(name);
                }
            }
            Expense e = Static.getDatabaseInstance().dao().getExpense(id);
            e.setPast(true);
            Static.getDatabaseInstance().dao().updateExpense(e);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.UI_NOTIFICATION_CHANNEL, "The Farm APP", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("PayNa Channel");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(String expenseNames) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Constants.UI_NOTIFICATION_CHANNEL);

        //(3) Various notification attributes can be declared here. Note that ones that are important:
        builder.setSmallIcon(R.drawable.ic_stat_logo);
        builder.setContentTitle("Expense Due Today");
        builder.setContentText("'" + expenseNames + "'." + " is due today.");
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        //(4) These attributes are still important though not required to execute
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_stat_logo));
        builder.setAutoCancel(true);

        //(5) Notifications are run by calling a notification manager and calling the notify function
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
        Constants.NOTIFICATION_ID++;

    }

    private void alert(String expenseNames) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Expense Due Today")
                .setMessage("'" + expenseNames + "'" + " is due today.")
                .setPositiveButton("OK", (dialogInterface, i) -> {

                });

        builder.show();
    }
}
