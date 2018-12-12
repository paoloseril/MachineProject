package edu.dlsu.mobapde.machineproject.activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.values.Constants;
import edu.dlsu.mobapde.machineproject.values.Static;

public class BaseActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Fragment mainActivityFragment, viewExpensesFragment;
    private BroadcastReceiver receiver = new AlarmReceiver();

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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.UI_UPDATE_TAG);
        registerReceiver(receiver, filter);
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
            navigationView.setSelectedItemId(R.id.main_screen);
        }
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

    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String tname = intent.getStringExtra("Name");
            String name = tname == null ? "" : tname;

            Log.d("Name", name);

            long vibration = intent.getLongExtra("Vib", 2);

            Log.d("Vibration received", String.valueOf(vibration));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Static.getVibratorInstance().vibrate(VibrationEffect.createOneShot(vibration * 1000, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else {
                Static.getVibratorInstance().vibrate(vibration);
            }
            if (Static.isActivityVisible()) {
                alert(name);
            }
            else {
                createNotification(name);
            }
        }
    }

    private void createNotification(String expenseNames) {

        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra("FragmentName", MainActivityFragment.class.getSimpleName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Constants.UI_NOTIFICATION_CHANNEL);

        builder.setSmallIcon(R.drawable.ic_stat_logo);
        builder.setContentTitle("PayNa!");
        builder.setContentText("'" + expenseNames + "'" + " is due today.");
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());

        Constants.NOTIFICATION_ID++;

    }

    private void alert(String expenseNames) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("PayNa!")
                .setMessage("'" + expenseNames + "'" + " is due today.")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Intent intent = new Intent(this, BaseActivity.class);
                    intent.putExtra("FragmentName", MainActivityFragment.class.getSimpleName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                });

        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
