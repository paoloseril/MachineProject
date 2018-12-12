package edu.dlsu.mobapde.machineproject.activity;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.values.Constants;
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

        ImageButton addButton = view.findViewById(R.id.add_Btn);
        addButton.setOnClickListener(ev -> {
            Intent intent = new Intent(getContext(), EditExpenseActivity.class);
            intent.putExtra("Status", "New");
            intent.putExtra("FragmentName", MainActivityFragment.class.getSimpleName());
            startActivity(intent);
        });
        avgText = view.findViewById(R.id.avgCostText);

        satisfactionText = view.findViewById(R.id.satisfactionText);

        expenseHistoryRecyclerArea = view.findViewById(R.id.expense_history_rarea);
        futureExpensesRecyclerArea = view.findViewById(R.id.future_expense_rarea);

        pastExpenseAdapter = new PastExpenseAdapter(getContext());
        futureExpenseAdapter = new FutureExpenseAdapter(getContext());

        expenseHistoryRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        expenseHistoryRecyclerArea.setAdapter(pastExpenseAdapter);

        futureExpensesRecyclerArea.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        futureExpensesRecyclerArea.setAdapter(futureExpenseAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        updateAverageDailyCost();
        updateDailySatisfaction();

        // Refresh the views if there are changes from the database
        refreshFutureExpenses();
        refreshHistory();
    }

    private void refreshHistory() {
        pastExpenseAdapter.clear();
        int size = Static.getDatabaseInstance().dao().getPastExpenses().size();
        if (size != 0) {
            enableRecyclerViewH();
            for (Expense e: Static.getDatabaseInstance().dao().getPastExpenses()) {
                pastExpenseAdapter.addView(e.getId(), e.getName(), e.getType(), Converter.toDate(e.getDateTimeMillis()), e.getCost());
            }
        }
        else {
            disableRecyclerViewH();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFutureExpenses();
        refreshHistory();
        updateAverageDailyCost();
        updateDailySatisfaction();
    }

    private void refreshFutureExpenses() {
        futureExpenseAdapter.clear();
        int size = Static.getDatabaseInstance().dao().getFutureExpenses().size();
        if (size != 0) {
            enableRecyclerViewF();

            for (Expense e: Static.getDatabaseInstance().dao().getFutureExpenses()) {
                futureExpenseAdapter.addView(e.getId(), e.getName(), e.getType(), Converter.toDate(e.getDateTimeMillis()), e.getCost());
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

    private void updateAverageDailyCost() {
        // get lower bound: today - 7 12:00 am
        // get upper bound: today - 1 11:59 pm
        String date7DaysAgoLB = Converter.toDate(-7).split(",")[0].concat(", 12:00 am");
        String dateYesterdayUB = Converter.toDate(0).split(",")[0].concat(", 11:59 pm");

        if (Static.getDatabaseInstance().dao().getExpensesFromPast7DaysCount(Converter.toMilliseconds(date7DaysAgoLB), Converter.toMilliseconds(dateYesterdayUB)) != 0) {
            double avgdc = Constants.round(Static.getDatabaseInstance().dao().getAverageCostOfPast7Days(Converter.toMilliseconds(date7DaysAgoLB), Converter.toMilliseconds(dateYesterdayUB)), 2);
            avgText.setText("₱".concat(Constants.format.format(avgdc)));
        }
        else {
            avgText.setText("₱".concat("--.--"));
        }
    }

    private void updateDailySatisfaction() {
        // get lower bound: today - 7 12:00 am
        // get upper bound: today - 1 11:59 pm
        String date7DaysAgoLB = Converter.toDate(-7).split(",")[0].concat(", 12:00 am");
        String dateYesterdayUB = Converter.toDate(0).split(",")[0].concat(", 11:59 pm");

        if (Static.getDatabaseInstance().dao().getExpensesFromPast7DaysCount(Converter.toMilliseconds(date7DaysAgoLB), Converter.toMilliseconds(dateYesterdayUB)) != 0) {
            double satis = Constants.round(Static.getDatabaseInstance().dao().getDailySatisfaction(Converter.toMilliseconds(date7DaysAgoLB), Converter.toMilliseconds(dateYesterdayUB)), 2);
            satisfactionText.setText(Constants.format.format(satis).concat("%"));
        }
        else {
            satisfactionText.setText("-.--%");
        }
   }

}
