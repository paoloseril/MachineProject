package edu.dlsu.mobapde.machineproject.activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import edu.dlsu.mobapde.machineproject.R;

public class BaseActivity extends AppCompatActivity {


    BottomNavigationView navigationView;
    Fragment mainActivityFragment, viewExpensesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        mainActivityFragment = new MainActivityFragment();
        viewExpensesFragment = new ViewExpensesFragment();

        navigationView = findViewById(R.id.e_navigation);
        loadFragment(mainActivityFragment);

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

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

}
