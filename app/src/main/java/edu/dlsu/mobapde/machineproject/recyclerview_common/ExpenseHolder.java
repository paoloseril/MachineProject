package edu.dlsu.mobapde.machineproject.recyclerview_common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.activity.EditExpenseActivity;

public class ExpenseHolder extends RecyclerView.ViewHolder {

    private ImageView iconView;
    private int id;
    private TextView nameTextComponent, timestampTextComponent;
    private TextView costTextComponent;
    private Context context;

    public ExpenseHolder(@NonNull View view) {
        super(view);

        LinearLayout ll = view.findViewById(R.id.entry);

        ll.setOnClickListener(ev -> {
            Intent intent = new Intent(context, EditExpenseActivity.class);
            intent.putExtra("Status", "Existing");
            intent.putExtra("Id", id);
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
        String withPeso = R.string.peso_sign + " " + String.valueOf(cost);
        costTextComponent.setText(withPeso);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setId(int id) {
        this.id = id;
    }
}
