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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;
import edu.dlsu.mobapde.machineproject.recyclerview1.FutureExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview2.PastExpenseAdapter;

public class MainActivityFragment extends Fragment {

    private ExpenseDatabase expenseDatabase;
    private RecyclerView expenseHistoryRecyclerArea, futureExpensesRecyclerArea;
    private PastExpenseAdapter pastExpenseAdapter;
    private FutureExpenseAdapter futureExpenseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);

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
        expenseDatabase = ExpenseDatabase.getDatabase(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    public void addExpenseEntry(View view) {


    }

    public void viewExpenseEntry(View view) {

    }
}
