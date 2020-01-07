package com.android.splitpersonality.Main_Screen;

public class Profiles { private String date;
    private String time;

    public Profiles(String date,String time) {
        this.date = date;
        this.time=time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
