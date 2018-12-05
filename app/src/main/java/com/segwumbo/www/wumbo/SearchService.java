package com.segwumbo.www.wumbo;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SearchService extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private DatabaseReference databaseUsers;
    private DatabaseReference databaseServices;
    private Button type,time,rating;
    private static RecyclerView rvServicesAvailable;
    public static ArrayList<UserAccount> allUserAccounts = new ArrayList<UserAccount>();
    private int tempHour,tempMin;
    private ArrayList<TimeAvailable> times = new ArrayList<>();
    private ArrayList<Service> availableServices;
    private ArrayList<Service> allServices = new ArrayList<>();
    private TextView text;
    @Override
    protected void onStart() {

        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(allUserAccounts != null) {

                    // getting all user account data from firebase
                    for(DataSnapshot userAccountSnapshot: dataSnapshot.getChildren()){
                        UserAccount account = userAccountSnapshot.getValue(UserAccount.class);
                        allUserAccounts.add(account);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        ServiceAdapter sAdapter = new ServiceAdapter(availableServices, new ClickListener() {
            @Override
            public void onPositionClicked(int position) { }
        }, 6);
        rvServicesAvailable.setAdapter(sAdapter);
        rvServicesAvailable.setLayoutManager(new LinearLayoutManager(this));
        databaseServices.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(allServices != null) {

                    // getting all user account data from firebase
                    for(DataSnapshot userAccountSnapshot: dataSnapshot.getChildren()){
                        Service account = userAccountSnapshot.getValue(Service.class);
                        allServices.add(account);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        /*
        Set<UserAccount> hs = new HashSet<>();
        hs.addAll(allUserAccounts);
        allUserAccounts.clear();
        allUserAccounts.addAll(hs);*/
    }
    @Override
    protected void onResume() {
        super.onResume();
        ServiceAdapter sAdapter = new ServiceAdapter(availableServices, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
            }
        }, 6);
        rvServicesAvailable.setAdapter(sAdapter);
        rvServicesAvailable.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        databaseUsers.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allUserAccounts.clear();

                // getting all user account data from firebase
                for(DataSnapshot userAccountSnapshot: dataSnapshot.getChildren()){
                    UserAccount account = userAccountSnapshot.getValue(UserAccount.class);
                    allUserAccounts.add(account);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        ServiceAdapter sAdapter = new ServiceAdapter(availableServices, new ClickListener() {
            @Override
            public void onPositionClicked(int position) { }
        }, 6);
        rvServicesAvailable.setAdapter(sAdapter);
        rvServicesAvailable.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // gets the reference of the database
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        type = findViewById(R.id.type);
        time = findViewById(R.id.time);
        rating = findViewById(R.id.rating);
        text = findViewById(R.id.text);
        text.setText("");
        rvServicesAvailable = findViewById(R.id.rv);
        availableServices =  new ArrayList<>();
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timeP = new TimePickerDialog(SearchService.this, SearchService.this, 0,0, true);
                timeP.setTitle("Start Time");
                timeP.show();
            }
        });
        ServiceAdapter sAdapter = new ServiceAdapter(availableServices, new ClickListener() {
            @Override
            public void onPositionClicked(int position) { }
        }, 6);
        rvServicesAvailable.setAdapter(sAdapter);
    }
    public void set(){
        getServicesOnTime(tempHour,tempMin);
        int s=availableServices.size();
        for (int i=1;i<availableServices.size();i++){
            if (availableServices.get(0).getName().equals(availableServices.get(i).getName())){
                s=i;
                break;
            }
        }
        String oof ="";
        for (int i=0;i<s;i++){
            oof += (availableServices.get(i).getName()+"\n");
        }
        text.setText(oof);

        ArrayList<Service> a = (ArrayList<Service>) availableServices.clone();
        availableServices.clear();
        for (int i=0;i<s;i++){
            availableServices.add(a.get(i));
        }
        onResume();
        //startActivity(new Intent(getIntent()));
    }
    private void getServicesOnTime(int sHour,int sMin){
        text.append(sHour+":"+sMin+"\n");
        availableServices.clear();
        times.clear();
        for (int i=0; i<allUserAccounts.size();i++){
            if (allUserAccounts.get(i).getRole().equals(("Service Provider").toLowerCase().trim())){
                if (allUserAccounts.get(i).getProfile() != null && allUserAccounts.get(i).getProfile().sDays !=null){
                    //text.append(allUserAccounts.get(i).getUsername()+"\n");
                    times = allUserAccounts.get(i).getProfile().sDays;
                    for (int j = 0;j<times.size(); j++){
                        if (times.get(j).startHour>sHour && sHour<times.get(j).endHour){
                            try{
                                addServiceAvailable(allUserAccounts.get(i).getProfile().getServicesOffered());
                            }
                            catch (Exception e){}
                        }
                        else if (times.get(j).startHour==sHour){
                            if (times.get(j).startMin<sMin){
                                try{
                                    addServiceAvailable(allUserAccounts.get(i).getProfile().getServicesOffered());
                                }
                                catch (Exception e){}
                            }
                        }
                        else if (times.get(j).endHour == sHour){
                            if (times.get(j).endMin>sMin){
                                try{
                                    addServiceAvailable(allUserAccounts.get(i).getProfile().getServicesOffered());
                                }
                                catch (Exception e){}
                            }
                        }
                    }
                }
            }
        }
    }
    private void addServiceAvailable(ArrayList<Service> servicesOffered){
        for (int i=0; i<servicesOffered.size(); i++){
            if (!availableServices.contains(servicesOffered.get(i))){
                availableServices.add(servicesOffered.get(i));
            }
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tempHour = hourOfDay;
        tempMin = minute;
        set();
    }
    public void onRate(View v){
        /*text.setText("");
        availableServices.clear();
        ArrayList<Service> rate = (ArrayList<Service>) allServices.clone();
        availableServices.add(rate.get(0));
        for (int i = 1; i<rate.size();i++){
            for (int j = 0; j<availableServices.size();j++){
                if (rate.get(i).getRating() > availableServices.get(i).getRating()){

                }
            }
        }*/
        onResume();
    }
}
