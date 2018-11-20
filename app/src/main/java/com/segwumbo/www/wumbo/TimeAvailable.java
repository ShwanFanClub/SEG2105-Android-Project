package com.segwumbo.www.wumbo;

public class TimeAvailable {
    String day;
    int startHour,startMin,endHour,endMin;
    public TimeAvailable(String day, int startHour, int startMin, int endHour, int endMin){
        this.day = day;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }
}
