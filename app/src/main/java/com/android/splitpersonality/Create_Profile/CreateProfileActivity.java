package com.android.splitpersonality.Create_Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.android.splitpersonality.R;

import butterknife.BindView;

public class CreateProfileActivity extends AppCompatActivity {
    @BindView(R.id.save)
    Button save;
    Switch bluetooth,airplane;
    @BindView(R.id.time_picker)
    TimePicker tp;
    boolean blue,air;
    int profile;
    RadioButton general,mute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        bluetooth =findViewById(R.id.bluetooth);
        airplane = findViewById(R.id.airplane);

        general = findViewById(R.id.general);
        mute = findViewById(R.id.mute);

        blue = false;
        air = false;
        profile = 1;



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bluetooth.isActivated())
                    blue = true;
                if(airplane.isActivated())
                    air = true;
                if(general.isSelected())
                    profile = 1;
                if(mute.isSelected())
                    profile = 0;



            }
        });
    }
}
