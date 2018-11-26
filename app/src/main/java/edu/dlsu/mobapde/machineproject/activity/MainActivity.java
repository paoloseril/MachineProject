package edu.dlsu.mobapde.machineproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.time.LocalDate;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.DateTimeConverter;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class MainActivity extends AppCompatActivity {

    private ExpenseDatabase expenseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Testing if can insert and retrieve values properly

        /*Expense expense = new Expense();
        expense.setName("Hello");
        expense.setType(Constants.TYPE_BILL);
        expense.setRegretLevel(1);
        expense.setDateTimeMillis(DateTimeConverter.toMilliseconds("01/21/1998 4:56 am"));

        expenseDatabase = ExpenseDatabase.getDatabase(this);

        expenseDatabase.dao().addExpense(expense);

        for (Expense e: expenseDatabase.dao().getAllExpenses()) {
            Log.d("Expense", e.getName());
            Log.d("Expense RL", String.valueOf(e.getRegretLevel()));
            Log.d("Expense Date", DateTimeConverter.toDate(e.getDateTimeMillis()));
            Log.d("Expense Type", e.getType());
        }*/

        // Testing to drop the table
        // expenseDatabase = ExpenseDatabase.getDatabase(this);
        // expenseDatabase.clearAllTables();


    }
}
