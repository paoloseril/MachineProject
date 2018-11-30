package edu.dlsu.mobapde.machineproject.recyclerview_common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.dlsu.mobapde.machineproject.R;

public class ExpenseHolder extends RecyclerView.ViewHolder {

    private ImageView iconView;
    private TextView nameTextComponent, timestampTextComponent;
    private TextView costTextComponent;

    public ExpenseHolder(@NonNull View view) {
        super(view);

        LinearLayout ll = view.findViewById(R.id.entry);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
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
        // add ... if space is not enough


        nameTextComponent.setText(name);
    }

    public void setTimeStamp(String timestamp) {
        timestampTextComponent.setText(timestamp);
    }

    public void setCost(double cost) {
        String withPeso = R.string.peso_sign + " " + String.valueOf(cost);
        costTextComponent.setText(withPeso);
    }
}
