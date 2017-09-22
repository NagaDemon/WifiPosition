package com.example.huyhoang.wifiposition.remote.model;

/**
 * Created by ducnd on 9/22/17.
 */

public class WifiInfoModel {
    private boolean isCheck;
    private String name;
    private float level;
    private float frequency;
    private String macAddress;
    private int count;


    public WifiInfoModel(String name, float level, float frequency, String macAddress, int count) {
        this.name = name;
        this.level = level;
        this.frequency = frequency;
        this.macAddress = macAddress;
        this.count = count;
    }

    public WifiInfoModel() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public float getLevel() {
        return level;
    }

    public float getFrequency() {
        return frequency;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
