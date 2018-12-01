package edu.dlsu.mobapde.machineproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.dlsu.mobapde.machineproject.R;

public class ViewExpensesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        getSupportActionBar().hide();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_view_expenses;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.list_expenses;
    }
}
