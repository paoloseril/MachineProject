package edu.dlsu.mobapde.machineproject.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int regretLevel;

    private String type;

    private String dateTime;

    private boolean past;

    private long vibratorSeconds;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private double cost;

    private int jobId;

    public Expense(String name, int regretLevel, String type, String dateTime, double cost) {
        this.name = name;
        this.regretLevel = regretLevel;
        this.type = type;
        this.dateTime = dateTime;
        this.cost = cost;
    }

    @Ignore
    public Expense(String name, int regretLevel, String type, boolean past, String dateTime, long vibratorSeconds, byte[] image, double cost, int jobId) {
        this.name = name;
        this.regretLevel = regretLevel;
        this.type = type;
        this.dateTime = dateTime;
        this.vibratorSeconds = vibratorSeconds;
        this.image = image;
        this.cost = cost;
        this.jobId = jobId;
        this.past = past;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Ignore
    @NonNull
    @Override
    public String toString() {
        return "Name: ".concat(name).concat("\n").concat("Regret Level: ").concat(String.valueOf(regretLevel))
                .concat("\n").concat(String.valueOf(dateTime));
    }

    public boolean getPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }
}
