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
    private PastExpenseAdapter view;
    private FutureExpenseAdapter futureExpenseAdapter;

    private LinearLayout emptymessageLayoutH, emptymessageLayoutF;
    private TextView avgText, satisfactionText;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptymessageLayoutH = view.findViewById(R.id.noexpense_added);
        emptymessageLayoutF = view.findViewById(R.id.noexpensefuture_added);

        avgText = view.findViewById(R.id.avgCostText);
        satisfactionText = view.findViewById(R.id.satisfactionText);

        expenseHistoryRecyclerArea = view.findViewById(R.id.expense_history_rarea);
        futureExpensesRecyclerArea = view.findViewById(R.id.future_expense_rarea);

        this.view = new PastExpenseAdapter(getActivity().getApplicationContext());
        futureExpenseAdapter = new FutureExpenseAdapter(getActivity().getApplicationContext());

        expenseHistoryRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseHistoryRecyclerArea.setAdapter(this.view);

        futureExpensesRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        futureExpensesRecyclerArea.setAdapter(futureExpenseAdapter);
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
        view.clear();
        int size = Static.getDatabaseInstance().dao().getPastExpenses().size();
        if (size != 0) {
            enableRecyclerViewH();
            for (Expense e: Static.getDatabaseInstance().dao().getPastExpenses()) {
                view.addView(e.getId(), e.getName(), e.getType(), e.getDateTime(), e.getCost());
            }
        }
        else {
            disableRecyclerViewH();
        }
    }

    private void refreshFutureExpenses() {
        futureExpenseAdapter.clear();
        int size = Static.getDatabaseInstance().dao().getFutureExpenses().size();
        if (size != 0) {
            enableRecyclerViewF();

            for (Expense e: Static.getDatabaseInstance().dao().getFutureExpenses()) {
                futureExpenseAdapter.addView(e.getId(), e.getName(), e.getType(), e.getDateTime(), e.getCost());
            }
        }
        else {
            disableRecyclerViewF();
        }
    }

    private void enableRecyclerViewH() {
        expenseHistoryRecyclerArea.setVisibility(View.VISIBLE);
        emptymessageLayoutH.setVisibility(View.GONE);
    }

    private void enableRecyclerViewF() {
        futureExpensesRecyclerArea.setVisibility(View.VISIBLE);
        emptymessageLayoutF.setVisibility(View.GONE);
    }

    private void disableRecyclerViewH() {
        expenseHistoryRecyclerArea.setVisibility(View.GONE);
        emptymessageLayoutH.setVisibility(View.VISIBLE);
    }

    private void disableRecyclerViewF() {
        futureExpensesRecyclerArea.setVisibility(View.GONE);
        emptymessageLayoutF.setVisibility(View.VISIBLE);
    }
}
