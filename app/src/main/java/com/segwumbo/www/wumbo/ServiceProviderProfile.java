package com.segwumbo.www.wumbo;

import java.util.ArrayList;

public class ServiceProviderProfile {

    private String id;
    private String userName;
    private String address;
    private String phoneNumber;
    private String companyName;
    private boolean licensed;
    private String description;
    private String servicesOfferedString; // all services' keys as one long string separtated by one space

    private ArrayList<Service> servicesOffered;

    public ServiceProviderProfile(){}

    public ServiceProviderProfile(String id, String userName, String address, String phoneNumber, String companyName,
                                  boolean licensed, String description){
        this.id = id;
        this.userName = userName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.licensed = licensed;
        this.description = description;
    }

    public void addService(Service s) { servicesOffered.add(s); }
    public void removeService (int i) { servicesOffered.remove(i); }
    public void removeService (Service s) { servicesOffered.remove(s); }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setUser(String user) {
        this.userName = user;
    }
    public String getUser() {
        return userName;
    }

    public ArrayList<Service> getServicesOffered() {
        return servicesOffered;
    }
    public Service getService(int i){ return servicesOffered.get(i); }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }
    public boolean isLicensed() {
        return licensed;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public String getServicesOfferedString() {
        return servicesOfferedString;
    }
    public void setServicesOfferedString(String servicesOfferedString) {
        this.servicesOfferedString = servicesOfferedString;
    }
}
