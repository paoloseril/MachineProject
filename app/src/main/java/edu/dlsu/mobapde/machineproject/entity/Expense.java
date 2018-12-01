package edu.dlsu.mobapde.machineproject.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int regretLevel;

    private String type;

    private long dateTimeMillis;

    private long vibratorSeconds;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private double cost;

    public Expense(String name, int regretLevel, String type, long dateTimeMillis, double cost) {
        this.name = name;
        this.regretLevel = regretLevel;
        this.type = type;
        this.dateTimeMillis = dateTimeMillis;
        this.cost = cost;
    }

    @Ignore
    public Expense(String name, int regretLevel, String type, long dateTimeMillis, long vibratorSeconds, byte[] image, double cost) {
        this.name = name;
        this.regretLevel = regretLevel;
        this.type = type;
        this.dateTimeMillis = dateTimeMillis;
        this.vibratorSeconds = vibratorSeconds;
        this.image = image;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegretLevel() {
        return regretLevel;
    }

    public void setRegretLevel(int regretLevel) {
        this.regretLevel = regretLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDateTimeMillis() {
        return dateTimeMillis;
    }

    public void setDateTimeMillis(long dateTimeMillis) {
        this.dateTimeMillis = dateTimeMillis;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getVibratorSeconds() {
        return vibratorSeconds;
    }

    public void setVibratorSeconds(long vibratorSeconds) {
        this.vibratorSeconds = vibratorSeconds;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
