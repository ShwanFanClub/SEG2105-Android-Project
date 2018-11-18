package com.segwumbo.www.wumbo;

import java.util.ArrayList;

public class ServiceProvider extends UserAccount {

    private String address;
    private String phoneNumber;
    private String companyName;
    private boolean licensed;
    private String description;

    protected ArrayList<Service> servicesOffered;

    public ServiceProvider(){}

    public ServiceProvider(String id, String email, String username, String password, String role){
        super(id, email, username, password, role);

        this.address = "";
        this.phoneNumber = "";
        this.companyName = "";
        this.licensed = false;
        this.description = "";
        this.servicesOffered = new ArrayList<>();
    }

    public ServiceProvider(String id, String email, String username, String password, String role,
                           String address, String phoneNumber, String companyName, boolean licensed, String description){
        super(id, email, username, password, role);

        this.address = address;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.licensed = licensed;
        this.description = description;

    }


    public void addService(Service s) { servicesOffered.add(s); }
    public void removeService (int i) { servicesOffered.remove(i); }
    public void removeService (Service s) { servicesOffered.remove(s); }

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


}
