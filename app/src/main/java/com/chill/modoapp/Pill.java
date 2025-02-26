package com.chill.modoapp;

import android.util.Log;

public class Pill {

    public String pillName;
    public int minutesInterval;
    public String instruction;
    public int quantity;
    public String schedule;
    public PillEventListener eventListener;

    public int currentStatus = Status.upcoming;
    public int lastStatus;

    private Thread pillThread;

    private long secondsUntilDone = 0;

    public interface Status {
        int upcoming = 0;
        int ready = 1;
        int success = 2;
        int late = 3;
        int missed = 4;
    }

    public Pill(String pillName, int minutesInterval, String instruction, int quantity, String time) {
        this.pillName = pillName;
        this.minutesInterval = minutesInterval;
        this.instruction = instruction;
        this.quantity = quantity;
        this.schedule = time;
    }

    public void startTimer() {
        if(pillThread == null || pillThread.getState() == Thread.State.TERMINATED) {
            pillThread = new Thread(new PillRunner());
            pillThread.start();
        } else {
            Log.d("Pill", "startTimer: thread wonky?");
        }
    }


    public String getTimeDetails() {

        if(secondsUntilDone > 60) {
            long minutes = secondsUntilDone/60;
            if(minutes > 60) {
                long hours = minutes/60;
                if(hours == 1) {
                    return hours + " hour left";
                }
                return hours + " hours left";
            } else {
                if (minutes == 1) {
                    return minutes + " minute left";
                }
                return minutes + " minutes left";
            }
        } else {
            if(secondsUntilDone == 1) {
                return secondsUntilDone + " second left";
            }
            return secondsUntilDone + " seconds left";
        }
    }


    public class PillRunner implements Runnable {

        @Override
        public void run() {

            // Set the total run time in minutes
            //int runTimeMinutes = thePill.minutesInterval; // change this value as needed
            long runTimeMillis = (long) minutesInterval * 60 * 1000; // convert minutes to milliseconds

            // Calculate start and end time
            long startTime = System.currentTimeMillis();
            long endTime = startTime + runTimeMillis;

            // Loop until the current time exceeds the end time
            long currentTime = System.currentTimeMillis();
            while (currentTime < endTime) {

                long diff = (endTime - currentTime);
                diff = diff / 1000;
                secondsUntilDone = diff;

                // Create an event every second
                createEvent("Progress: " + diff);

                // Sleep for 1 second (1000 milliseconds)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Loop was interrupted");
                    break;
                }
                currentTime = System.currentTimeMillis();
            }

            // Create a final event after loop ends
            createEvent("Done");
        }

        private void createEvent(String s) {
            if(eventListener != null) {
                eventListener.event(s);
            }
        }
    }

}
