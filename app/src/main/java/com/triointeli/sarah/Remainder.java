package com.triointeli.sarah;

/**
 * Created by ritik on 03-02-2018.
 */

public class Remainder {
    private String remainder;
    private String dateTime;
    private boolean done;

    public Remainder(){

    }
    public Remainder(String remainder, String dateTime, boolean done) {
        this.remainder = remainder;
        this.dateTime = dateTime;
        this.done = done;
    }

    public String getRemainder() {
        return remainder;
    }

    public void setRemainder(String remainder) {
        this.remainder = remainder;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
