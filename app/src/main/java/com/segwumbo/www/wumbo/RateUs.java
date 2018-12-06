package com.segwumbo.www.wumbo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RateUs extends AppCompatActivity {

    private DatabaseReference databaseServices;
    private String serviceKey, serviceName;
    private Service serviceToRate;
    private RatingBar rating;
    private boolean hasBeenRatedAtLeastOnce;
    static float updatedRating;

    @Override
    protected void onStart() {
        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

                for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                    Service temp = serviceSnapshot.getValue(Service.class);
                    if(serviceName.equals(temp.getName())){
                        serviceKey = temp.getId();
                        serviceToRate = temp;
                        hasBeenRatedAtLeastOnce = temp.getBeenRatedAtLeastOnce();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        Bundle bundle = getIntent().getExtras();
        serviceName = bundle.getString("service name");
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        rating = findViewById(R.id.ratingBar);
    }
    public void OnSubmitButtonClick(View view){

        float ratingFromUser = rating.getRating();

        if(ServiceProfile.disabled == false){
            if(hasBeenRatedAtLeastOnce){
                updatedRating = calculateUpdatedRating(ratingFromUser, serviceToRate.getRating());
            }
            else{
                updatedRating = ratingFromUser;
                databaseServices.child(serviceKey).child("beenRatedAtLeastOnce").setValue(true);
            }

            databaseServices.child(serviceKey).child("rating").setValue(updatedRating);
            Toast.makeText(view.getContext(), "Thanks for Rating!",Toast.LENGTH_SHORT).show();
            finish();
        }

        ServiceProfile.disabled = true;
    }

    public static float calculateUpdatedRating(double ratingFromUser, double currentRating){

        return (float) (((ratingFromUser / 5) + (currentRating / 5)) / 2) * 5;
    }
}
