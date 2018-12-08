package edu.dlsu.mobapde.machineproject.recyclerview1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.activity.MainActivityFragment;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.recyclerview_common.ExpenseHolder;
import edu.dlsu.mobapde.machineproject.recyclerview_common.ExpenseModel;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class FutureExpenseAdapter extends RecyclerView.Adapter<ExpenseHolder> {

    private ArrayList<ExpenseModel> futureExpenses;
    private Context context;

    public FutureExpenseAdapter(Context context) {
        this.context = context;
        futureExpenses = new ArrayList<>();
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
        expenseHolder.setLogo(futureExpenses.get(i).getIcon());
        expenseHolder.setCost(Constants.round(futureExpenses.get(i).getCost(), 2));
        expenseHolder.setName(futureExpenses.get(i).getName());
        expenseHolder.setTimeStamp(futureExpenses.get(i).getTimestamp());
        expenseHolder.setContext(context);
        expenseHolder.setId(futureExpenses.get(i).getId());
        expenseHolder.setFragmentName(MainActivityFragment.class.getSimpleName());
    }

    @Override
    public int getItemCount() {
        return futureExpenses.size();
    }

    public void addView(int id, String name, String type, String date, double cost) {
        futureExpenses.add(new ExpenseModel(id, name, type, date, cost));
        notifyItemInserted(futureExpenses.size() - 1);
    }

    public void clear() {
        final int size = futureExpenses.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                futureExpenses.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}
