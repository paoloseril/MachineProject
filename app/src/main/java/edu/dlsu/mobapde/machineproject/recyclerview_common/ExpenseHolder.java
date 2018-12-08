package edu.dlsu.mobapde.machineproject.recyclerview_common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.activity.EditExpenseActivity;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class ExpenseHolder extends RecyclerView.ViewHolder {

    private ImageView iconView;
    private TextView nameTextComponent, timestampTextComponent, costTextComponent;
    private Context context;

    private int id;
    private String fragmentName;

    public ExpenseHolder(@NonNull View view) {
        super(view);

        view.setOnClickListener(ev -> {
            Intent intent = new Intent(context, EditExpenseActivity.class);
            intent.putExtra("Status", "Existing");
            intent.putExtra("Id", id);
            intent.putExtra("FragmentName", fragmentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        iconView = view.findViewById(R.id.logo);
        nameTextComponent = view.findViewById(R.id.nameofexpense);
        timestampTextComponent = view.findViewById(R.id.time_stamp);
        costTextComponent = view.findViewById(R.id.totalCost);
    }

    public void setLogo(int image) {
        iconView.setImageResource(image);
    }

    public void setName(String name) {
        nameTextComponent.setText(name);
    }

    public void setTimeStamp(String timestamp) {
        timestampTextComponent.setText(timestamp);
    }

    public void setCost(double cost) {
        String formatted = Constants.format.format(cost);
        String withPeso = "P".concat(" ").concat(formatted);
        costTextComponent.setText(withPeso);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }
}
