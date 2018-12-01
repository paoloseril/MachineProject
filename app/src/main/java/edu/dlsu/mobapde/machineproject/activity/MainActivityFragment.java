package edu.dlsu.mobapde.machineproject.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.database.Database;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.recyclerview1.FutureExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview2.PastExpenseAdapter;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class MainActivityFragment extends Fragment {

    private ExpenseDatabase expenseDatabase;
    private RecyclerView expenseHistoryRecyclerArea, futureExpensesRecyclerArea;
    private PastExpenseAdapter pastExpenseAdapter;
    private FutureExpenseAdapter futureExpenseAdapter;
    LinearLayout emptymessageLayoutH, emptymessageLayoutF;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);

        emptymessageLayoutH = root.findViewById(R.id.noexpense_added);
        emptymessageLayoutF = root.findViewById(R.id.noexpensefuture_added);

        expenseHistoryRecyclerArea = root.findViewById(R.id.expense_history_rarea);
        futureExpensesRecyclerArea = root.findViewById(R.id.future_expense_rarea);

        pastExpenseAdapter = new PastExpenseAdapter();
        futureExpenseAdapter = new FutureExpenseAdapter();

        expenseHistoryRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseHistoryRecyclerArea.setAdapter(pastExpenseAdapter);

        futureExpensesRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        futureExpensesRecyclerArea.setAdapter(futureExpenseAdapter);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //Expense e = new Expense("Hello", 1, Constants.TYPE_BILL, System.currentTimeMillis(), 250.00);
        //Database.getInstance().dao().addExpense(e);

    }

    public void refresh() {

        // add to database

        if (futureExpensesRecyclerArea.getVisibility() == View.GONE) {
            futureExpensesRecyclerArea.setVisibility(View.VISIBLE);
            emptymessageLayoutF.setVisibility(View.GONE);
        }

        if (expenseHistoryRecyclerArea.getVisibility() == View.GONE) {
            expenseHistoryRecyclerArea.setVisibility(View.VISIBLE);
            emptymessageLayoutH.setVisibility(View.GONE);
        }
    }

    public void viewExpenseEntry() {

    }
}
