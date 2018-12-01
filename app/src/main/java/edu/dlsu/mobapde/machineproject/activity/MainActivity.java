package edu.dlsu.mobapde.machineproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;

public class MainActivity extends BaseActivity {

    private ExpenseDatabase expenseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        // initialize database
        // expenseDatabase = ExpenseDatabase.getDatabase(this);

        // initialize components



        ////////////////////////////////////////////////////////////////////////////////////////
        // Testing if can insert and retrieve values properly

        /*Expense expense = new Expense();
        expense.setName("Hello");
        expense.setType(Constants.TYPE_BILL);
        expense.setRegretLevel(1);
        expense.setDateTimeMillis(Converter.toMilliseconds("01/21/1998 4:56 am"));

        expenseDatabase = ExpenseDatabase.getDatabase(this);

        expenseDatabase.dao().addExpense(expense);

        for (Expense e: expenseDatabase.dao().getAllExpenses()) {
            Log.d("Expense", e.getName());
            Log.d("Expense RL", String.valueOf(e.getRegretLevel()));
            Log.d("Expense Date", Converter.toDate(e.getDateTimeMillis()));
            Log.d("Expense Type", e.getType());
        }*/

        // Testing to drop the table
        // expenseDatabase = ExpenseDatabase.getDatabase(this);
        // expenseDatabase.clearAllTables();
        //////////////////////////////////////////////////////////

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy|h:mm a", Locale.ENGLISH);

        Log.d("Date", formatter.format(System.currentTimeMillis()));

        Log.d("Date2", Converter.toDate(System.currentTimeMillis()));
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.main_screen;
    }


}
