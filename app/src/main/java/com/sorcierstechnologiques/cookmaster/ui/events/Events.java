package com.sorcierstechnologiques.cookmaster.ui.events;

public class Events {

    private String name,start_date,start_time,end_time,address;

    public Events(String name, String start_date, String start_time, String end_time, String address) {
        this.name = name;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getAddress() {
        return address;
    }
}
