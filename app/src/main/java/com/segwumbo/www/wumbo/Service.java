package com.segwumbo.www.wumbo;

public class Service {

    private String id;
    private String name;
    private double hourlyRate;
    private double rating;
    private boolean beenRatedAtLeastOnce, alreadyRated;

    public Service() {
        // Default constructor required for calls to DataSnapshot.getValue(Service.class)
    }

    public Service(String id, String name, double hourlyRate){

        setId(id);
        setName(name);
        setHourlyRate(hourlyRate);
        setRating(0);
        setBeenRatedAtLeastOnce(false);
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
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

    public void setRating(double rating){ this.rating= rating; }

    public double getRating(){
        return this.rating;
    }

    public boolean isRated(){
        return this.alreadyRated;
    }

    public boolean setRated(boolean rated){
        return this.alreadyRated = rated;
    }

    public void setBeenRatedAtLeastOnce(boolean hasBeenRated){ this.beenRatedAtLeastOnce = hasBeenRated; }

    public boolean getBeenRatedAtLeastOnce(){
        return this.beenRatedAtLeastOnce;
    }
}
