package edu.dlsu.mobapde.machineproject.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.values.Static;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.recyclerview1.FutureExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview2.PastExpenseAdapter;

public class MainActivityFragment extends Fragment {

    private RecyclerView expenseHistoryRecyclerArea, futureExpensesRecyclerArea;
    private PastExpenseAdapter pastExpenseAdapter;
    private FutureExpenseAdapter futureExpenseAdapter;

    private LinearLayout emptymessageLayoutH, emptymessageLayoutF;
    private TextView avgText, satisfactionText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);

        emptymessageLayoutH = root.findViewById(R.id.noexpense_added);
        emptymessageLayoutF = root.findViewById(R.id.noexpensefuture_added);

        avgText = root.findViewById(R.id.avgCostText);
        satisfactionText = root.findViewById(R.id.satisfactionText);

        expenseHistoryRecyclerArea = root.findViewById(R.id.expense_history_rarea);
        futureExpensesRecyclerArea = root.findViewById(R.id.future_expense_rarea);

        pastExpenseAdapter = new PastExpenseAdapter(getActivity().getApplicationContext());
        futureExpenseAdapter = new FutureExpenseAdapter(getActivity().getApplicationContext());

        expenseHistoryRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseHistoryRecyclerArea.setAdapter(pastExpenseAdapter);

        futureExpensesRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        futureExpensesRecyclerArea.setAdapter(futureExpenseAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // Refresh the views if there are changes from the database
        refreshFutureExpenses();
        refreshHistory();

    }

    private void refreshHistory() {
        pastExpenseAdapter.removeAllViews();
        for (Expense e: Static.getDatabaseInstance().dao().getPastExpenses(System.currentTimeMillis())) {
            pastExpenseAdapter.addView(e.getId(), e.getName(), e.getType(), e.getDateTimeMillis(), e.getCost());
        }
        if (expenseHistoryRecyclerArea.getVisibility() == View.GONE
                && pastExpenseAdapter.getItemCount() != 0) {
            expenseHistoryRecyclerArea.setVisibility(View.VISIBLE);
            emptymessageLayoutH.setVisibility(View.GONE);
        }
        else {
            expenseHistoryRecyclerArea.setVisibility(View.GONE);
            emptymessageLayoutH.setVisibility(View.VISIBLE);
        }
    }

    private void refreshFutureExpenses() {
        futureExpenseAdapter.removeAllViews();
        for (Expense e: Static.getDatabaseInstance().dao().getFutureExpenses(System.currentTimeMillis())) {
            futureExpenseAdapter.addView(e.getId(), e.getName(), e.getType(), e.getDateTimeMillis(), e.getCost());
        }
        if (futureExpensesRecyclerArea.getVisibility() == View.GONE
                && futureExpenseAdapter.getItemCount() != 0) {
            futureExpensesRecyclerArea.setVisibility(View.VISIBLE);
            emptymessageLayoutF.setVisibility(View.GONE);
        }
        else {
            futureExpensesRecyclerArea.setVisibility(View.GONE);
            emptymessageLayoutF.setVisibility(View.VISIBLE);
        }
    }
}
