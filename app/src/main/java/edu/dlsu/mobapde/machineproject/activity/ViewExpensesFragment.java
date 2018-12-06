package edu.dlsu.mobapde.machineproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.values.Static;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.recyclerview3.ExpensesViewAdapter;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class ViewExpensesFragment extends Fragment {

    private RecyclerView expenseRecyclerView;
    private ExpensesViewAdapter adapter;

    private Object value;

    private Spinner keySpinner, valueSpinner;
    private TextView warningIfEmptyView;
    private EditText categoryText;

    public static ViewExpensesFragment newInstance() {
        return new ViewExpensesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_view_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(ev -> {
            Intent intent = new Intent(getContext(), EditExpenseActivity.class);
            intent.putExtra("Status", "New");
            startActivity(intent);
        });
        warningIfEmptyView = view.findViewById(R.id.empty_warning);

        keySpinner = view.findViewById(R.id.keySpinner);
        valueSpinner = view.findViewById(R.id.valueSpinner);

        categoryText = view.findViewById(R.id.categoricalText);

        adapter = new ExpensesViewAdapter(getContext());

        categoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = charSequence.toString();
                refresh("name");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ArrayAdapter<CharSequence> keyAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.the_keys, android.R.layout.simple_spinner_item);

        keyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        keySpinner.setAdapter(keyAdapter);
        keySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                Log.d("Selected", selected);
                switch (selected) {
                    case "Name": {
                        categoryText.setEnabled(true);
                        valueSpinner.setEnabled(false);

                        break;
                    }
                    case "Type": {
                        categoryText.setEnabled(false);
                        valueSpinner.setEnabled(true);
                        ArrayAdapter<CharSequence> valueAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.types, android.R.layout.simple_spinner_item);
                        valueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        valueSpinner.setAdapter(valueAdapter);
                        valueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String selected = adapterView.getItemAtPosition(i).toString();
                                Log.d("Selected", selected);
                                switch (selected) {
                                    case Constants.TYPE_BILL: {
                                        value = Constants.TYPE_BILL;
                                        break;
                                    }
                                    case Constants.TYPE_DEBT: {
                                        value = Constants.TYPE_DEBT;
                                        break;
                                    }
                                    case Constants.TYPE_EDUCATION: {
                                        value = Constants.TYPE_EDUCATION;
                                        break;
                                    }
                                    case Constants.TYPE_FOOD: {
                                        value = Constants.TYPE_FOOD;
                                        break;
                                    }
                                    case Constants.TYPE_GENERAL: {
                                        value = Constants.TYPE_GENERAL;
                                        break;
                                    }
                                }
                                refresh("type");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    }
                    case "Regret Level": {
                        categoryText.setEnabled(false);
                        valueSpinner.setEnabled(true);
                        Log.d("Enabled", String.valueOf(valueSpinner.isEnabled()));
                        ArrayAdapter<CharSequence> valueAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                R.array.regret_levels, android.R.layout.simple_spinner_item);
                        valueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        valueSpinner.setAdapter(valueAdapter);
                        valueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String selected = adapterView.getItemAtPosition(i).toString();
                                Log.d("Selected", selected);
                                switch (selected) {
                                    case "0": {
                                        value = 0;
                                        refresh("regret level");
                                        break;
                                    }
                                    case "1": {
                                        value = 1;
                                        refresh("regret level");
                                        break;
                                    }
                                    case "2": {
                                        value = 2;
                                        refresh("regret level");
                                        break;
                                    }
                                    case "3": {
                                        value = 3;
                                        refresh("regret level");
                                        break;
                                    }
                                    case "4": {
                                        value = 4;
                                        refresh("regret level");
                                        break;
                                    }
                                    default: {
                                        refresh("default");
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    }
                    case "Default": {
                        valueSpinner.setEnabled(false);
                        categoryText.setEnabled(false);
                        refresh("default");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        expenseRecyclerView = view.findViewById(R.id.all_expenses_rarea);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }

    private void enableRecyclerView() {
        expenseRecyclerView.setVisibility(View.VISIBLE);
        warningIfEmptyView.setVisibility(View.GONE);
    }

    private void disableRecyclerView() {
        expenseRecyclerView.setVisibility(View.GONE);
        warningIfEmptyView.setVisibility(View.VISIBLE);
    }

    private void refresh(String key) {
        adapter.clear();
        disableRecyclerView();
        // name
        switch (key) {
            case "name": {
                String val = String.valueOf(value);
                int size = Static.getDatabaseInstance().dao().getExpensesBy("%".concat(val)).size();
                if (size != 0) {
                    enableRecyclerView();
                    for (Expense e : Static.getDatabaseInstance().dao().getExpensesBy("%".concat(val))) {
                        adapter.addView(e.getId(), e.getName(), e.getType(), Converter.toDate(e.getDateTimeMillis()), e.getCost());
                    }
                }
                else {
                    disableRecyclerView();
                }
                break;
            }

            // regret level
            case "regret level": {
                Integer val = (Integer) value;
                int size = Static.getDatabaseInstance().dao().getExpensesByRegretLevel(val).size();
                if (size != 0) {
                    enableRecyclerView();
                    for (Expense e : Static.getDatabaseInstance().dao().getExpensesByRegretLevel(val)) {
                        adapter.addView(e.getId(), e.getName(), e.getType(), Converter.toDate(e.getDateTimeMillis()), e.getCost());
                    }
                }
                else {
                    disableRecyclerView();
                }
                break;
            }
            case "type": {
                String val = String.valueOf(value);
                int size = Static.getDatabaseInstance().dao().getExpensesByType(val).size();
                if (size != 0) {
                    enableRecyclerView();
                    for (Expense e : Static.getDatabaseInstance().dao().getExpensesByType(val)) {
                        adapter.addView(e.getId(), e.getName(), e.getType(), Converter.toDate(e.getDateTimeMillis()), e.getCost());
                    }
                }
                else {
                    disableRecyclerView();
                }
                break;
            }
            // by default
            default: {
                int size = Static.getDatabaseInstance().dao().getAllExpenses().size();
                if (size != 0) {
                    enableRecyclerView();
                    for (Expense e : Static.getDatabaseInstance().dao().getAllExpenses()) {
                        adapter.addView(e.getId(), e.getName(), e.getType(), Converter.toDate(e.getDateTimeMillis()), e.getCost());
                    }
                } else {
                    disableRecyclerView();
                }
                break;
            }
        }
    }
}
