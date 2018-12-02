package edu.dlsu.mobapde.machineproject.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.database.Database;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.recyclerview1.FutureExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview2.PastExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview3.ExpensesViewAdapter;

public class ViewExpensesFragment extends Fragment {

    private RecyclerView expenseRecyclerView;
    private ExpensesViewAdapter adapter;
    private String key;
    private Object value;

    TextView warningIfEmptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_view_expenses, container, false);

        warningIfEmptyView = root.findViewById(R.id.empty_warning);
        adapter = new ExpensesViewAdapter();

        expenseRecyclerView = root.findViewById(R.id.all_expenses_rarea);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseRecyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
            value = bundle.get("value");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        if (key != null && value != null) {
            refresh(key);
        }
        else {
            refresh("default");
        }
    }

    public void addExpenseEntry(View view) {

    }

    public void filterExpenses(View view) {

    }

    private void refresh(String key) {
        // name
        if (key.equals("name")) {
            String val = String.valueOf(value);
            for (Expense e: Database.getInstance().dao().getExpensesBy("%".concat(val).concat("%"))) {
                adapter.addView(e.getName(), e.getType(), e.getDateTimeMillis(), e.getCost());
            }

        }

        // regret level
        else if (key.equals("regretlevel")) {
            Integer val = Integer.valueOf((String) value);
            for (Expense e: Database.getInstance().dao().getExpensesByRegretLevel(val)) {
                adapter.addView(e.getName(), e.getType(), e.getDateTimeMillis(), e.getCost());
            }
        }

        else if (key.equals("type")) {
            String val = String.valueOf(value);
            for (Expense e: Database.getInstance().dao().getExpensesByType(val)) {
                adapter.addView(e.getName(), e.getType(), e.getDateTimeMillis(), e.getCost());
            }
        }
        // by default
        else {
            for (Expense e: Database.getInstance().dao().getAllExpenses()) {
                adapter.addView(e.getName(), e.getType(), e.getDateTimeMillis(), e.getCost());
            }
        }
        if (expenseRecyclerView.getVisibility() == View.GONE
                && adapter.getItemCount() != 0) {
            expenseRecyclerView.setVisibility(View.VISIBLE);
            warningIfEmptyView.setVisibility(View.GONE);
        }
    }
}
