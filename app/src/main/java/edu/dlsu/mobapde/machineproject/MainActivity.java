package edu.dlsu.mobapde.machineproject;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.dlsu.mobapde.machineproject.database.Expense;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;

public class MainActivity extends AppCompatActivity {

    private ExpenseDatabase expenseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Expense expense = new Expense();
        expense.setName("Hello");

        // expenseDatabase.dao().addExpense(expense);

        //for (Expense e: expenseDatabase.dao().getAllExpenses()) {
          //  Log.d("Expense", e.getName());
        //}
    }
}
