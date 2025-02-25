package com.chill.modoapp;

public class Pill {

    public String pillName;
    public int secondsInterval;
    public String instruction;
    public int quantity;
    public String schedule;

    public Pill(String pillName, int secondsInterval, String instruction, int quantity, String time) {
        this.pillName = pillName;
        this.secondsInterval = secondsInterval;
        this.instruction = instruction;
        this.quantity = quantity;
        this.schedule = time;
    }
}
