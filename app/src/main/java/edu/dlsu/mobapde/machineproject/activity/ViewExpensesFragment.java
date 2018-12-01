package edu.dlsu.mobapde.machineproject.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.recyclerview1.FutureExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview2.PastExpenseAdapter;
import edu.dlsu.mobapde.machineproject.recyclerview3.ExpensesViewAdapter;

public class ViewExpensesFragment extends Fragment {

    private RecyclerView expenseRecyclerView;
    private ExpensesViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_view_expenses, container, false);

        expenseRecyclerView = root.findViewById(R.id.all_expenses_rarea);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseRecyclerView.setAdapter(adapter);

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

    }
}
