package com.segwumbo.www.wumbo;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;

public class UpdateTime extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    ArrayList<TimeAvailable> times = new ArrayList<TimeAvailable>();
    int tempHour,tempMin,lastLoc;
    int btnid [] = {R.id.b0,R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.b10,R.id.b11,R.id.b12,R.id.b13};
    int txtid [] = {R.id.t0,R.id.t1,R.id.t2,R.id.t3,R.id.t4,R.id.t5,R.id.t6,R.id.t7,R.id.t8,R.id.t9,R.id.t10,R.id.t11,R.id.t12,R.id.t13};
    TextView [] txt = new TextView [txtid.length];
    String [] text = new String[txtid.length];
    Button [] btn = new Button [btnid.length];
    Button Update;
    int [] daysID = {R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday,R.id.sunday};
    Switch [] days = new Switch[daysID.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        Bundle bundle = getIntent().getExtras();
        Update = (Button) findViewById(R.id.update);
        for (int i = 0; i<daysID.length;i++){
            days[i] = (Switch) findViewById(daysID[i]);
        }
        for (int i = 0; i<btn.length;i++){
            btn[i]= (Button) findViewById(btnid[i]);
            txt[i] = (TextView) findViewById(txtid[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timeP = new TimePickerDialog(UpdateTime.this, UpdateTime.this, 0,0, true);
                    timeP.show();
                    set(v);
                }
            });
        }
        Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i<days.length;i++){
                    if (days[i].isChecked() &&  (Integer.parseInt(text[i*2])!=Integer.parseInt(text[(i*2)+1]))){
                        times.add(new TimeAvailable(days[i].getText().toString(), Integer.parseInt(text[i*2]), Integer.parseInt(text[(i*2)+1])));
                    }
                }
                finish();
            }
        });
    }
    public void set(View v){
        for (int i = 0; i<btnid.length; i++){
            if (v.getId() == btnid[i]){
                text[i] = tempHour+":"+tempMin;
                lastLoc = i;
            }
        }
        //startActivity(getIntent());
        for (int i = 0; i<text.length; i++){
            if (text[i]!=(null)){
                txt[i].setText(text[i]);
            }
        }
    }
    public void set(){
        text[lastLoc]= tempHour+":"+tempMin;
        txt[lastLoc].setText(text[lastLoc]);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tempHour = hourOfDay;
        tempMin = minute;
        set();
    }
}
