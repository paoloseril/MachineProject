package edu.dlsu.mobapde.machineproject.recyclerview3;

import android.content.Context;
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
    private Context context;

    public ExpensesViewAdapter(Context context) {
        listOfExpenses = new ArrayList<>();
        this.context = context;
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
        expenseHolder.setContext(context);
        expenseHolder.setId(listOfExpenses.get(i).getId());
    }

    @Override
    public int getItemCount() {
        return listOfExpenses.size();
    }

    public void addView(int id, String name, String type, long millis, double cost) {
        listOfExpenses.add(new ExpenseModel(id, name, type, Converter.toDate(millis), cost));
        notifyItemInserted(listOfExpenses.size() - 1);
    }

    public void clear() {
        final int size = listOfExpenses.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listOfExpenses.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

}
