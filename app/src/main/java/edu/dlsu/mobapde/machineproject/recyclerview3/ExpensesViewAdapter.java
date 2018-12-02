package edu.dlsu.mobapde.machineproject.recyclerview3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.recyclerview_common.ExpenseHolder;
import edu.dlsu.mobapde.machineproject.recyclerview_common.ExpenseModel;
import edu.dlsu.mobapde.machineproject.values.Constants;


public class ExpensesViewAdapter extends RecyclerView.Adapter<ExpenseHolder> {

    private ArrayList<ExpenseModel> listOfExpenses;

    public ExpensesViewAdapter() {
        listOfExpenses = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.past_expense, viewGroup, false);

        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder expenseHolder, int i) {
        expenseHolder.setLogo(listOfExpenses.get(i).getIcon());
        expenseHolder.setCost(Constants.round(listOfExpenses.get(i).getCost(), 2));
        expenseHolder.setName(listOfExpenses.get(i).getName());
        expenseHolder.setTimeStamp(listOfExpenses.get(i).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return listOfExpenses.size();
    }

    public void addView(String name, String type, long millis, double cost) {
        listOfExpenses.add(new ExpenseModel(name, type, Converter.toDate(millis), cost));
        notifyItemInserted(listOfExpenses.size() - 1);
    }

    public void removeAllViews() {
        listOfExpenses.clear();
        notifyItemRangeRemoved(0, listOfExpenses.size());
    }

}
