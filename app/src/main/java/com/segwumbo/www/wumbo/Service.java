package com.segwumbo.www.wumbo;

public class Service {

    private String id;
    private String name;
    private double hourlyRate;

    public Service() {
        // Default constructor required for calls to DataSnapshot.getValue(Service.class)
    }

    public Service(String id, String name, double hourlyRate){
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String hourlyRate){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setHourlyRate(double hourlyRate){
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate(){
        return this.hourlyRate;
    }
}
