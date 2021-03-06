package edu.dlsu.mobapde.machineproject.recyclerview_common;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class ExpenseModel {

    private int id;
    private String name;
    private int icon;
    private String timestamp;
    private double cost;

    public ExpenseModel(int id, String name, String type, String timestamp, double cost) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getCost() {
        return cost;
    }
}
