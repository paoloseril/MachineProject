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

    private int satisfaction;

    private String type;

    private long dateTimeMillis;

    private boolean past;

    private long vibratorSeconds;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private double cost;

    private int jobId;

    public Expense(String name, int regretLevel, String type, long dateTimeMillis, double cost) {
        this.name = name;
        this.regretLevel = regretLevel;
        switch (regretLevel) {
            case 0: {
                satisfaction = 100;
                break;
            }
            case 1: {
                satisfaction = 75;
                break;
            }
            case 2: {
                satisfaction = 50;
                break;
            }
            case 3: {
                satisfaction = 25;
                break;
            }
            case 4: {
                satisfaction = 0;
                break;
            }
        }
        this.type = type;
        this.dateTimeMillis = dateTimeMillis;
        this.cost = cost;
    }

    @Ignore
    public Expense(String name, int regretLevel, String type, int satisfaction, boolean past, long dateTimeMillis, long vibratorSeconds, byte[] image, double cost, int jobId) {
        this.name = name;
        this.regretLevel = regretLevel;
        this.type = type;
        this.dateTimeMillis = dateTimeMillis;
        this.vibratorSeconds = vibratorSeconds;
        this.image = image;
        this.cost = cost;
        this.jobId = jobId;
        this.past = past;
        this.satisfaction = satisfaction;
    }

    public int getSatisfaction() {
        return satisfaction;
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
        switch (regretLevel) {
            case 0: {
                setSatisfaction(100);
                break;
            }
            case 1: {
                setSatisfaction(75);
                break;
            }
            case 2: {
                setSatisfaction(50);
                break;
            }
            case 3: {
                setSatisfaction(25);
                break;
            }
            case 4: {
                setSatisfaction(0);
                break;
            }
        }
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean getPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public long getDateTimeMillis() {
        return dateTimeMillis;
    }

    public void setDateTimeMillis(long dateTimeMillis) {
        this.dateTimeMillis = dateTimeMillis;
    }
}
