package edu.dlsu.mobapde.machineproject.recyclerview_common;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class ExpenseModel {

    private String name;
    private int icon;
    private String timestamp;
    private double cost;

    public ExpenseModel(String name, String type, String timestamp, double cost) {
        this.name = name;
        switch (type) {
            case Constants.TYPE_GENERAL:
                icon = R.drawable.general;
                break;
            case Constants.TYPE_FOOD:
                icon = R.drawable.cutlery;
                break;
            case Constants.TYPE_BILL:
                icon = R.drawable.invoice;
                break;
            case Constants.TYPE_DEBT:
                icon = R.drawable.debt;
                break;
            case Constants.TYPE_EDUCATION:
                icon = R.drawable.book;
                break;
        }
        this.timestamp = timestamp;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
