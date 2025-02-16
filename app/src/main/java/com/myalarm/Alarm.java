package com.myalarm;

public class Alarm {
    private String time;        // Time of the alarm
    private boolean active;     // If is active at the moment

    public Alarm(String time) {
        this.time = time;
        this.active = true;
    }

    public String getTime() {
        return time;
    }

    public boolean getActive() {
        return active;
    }
}
