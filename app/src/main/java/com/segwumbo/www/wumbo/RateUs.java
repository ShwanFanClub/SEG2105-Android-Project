package com.segwumbo.www.wumbo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        final RatingBar stars = (RatingBar) findViewById(R.id.ratingBar);
        Button submitRate = (Button) findViewById(R.id.submit);

    }
    public void OnSubmitButtonClick(View view){
        Toast.makeText(view.getContext(), "Thanks for Rating!",Toast.LENGTH_SHORT).show();
        finish();
    }
}
