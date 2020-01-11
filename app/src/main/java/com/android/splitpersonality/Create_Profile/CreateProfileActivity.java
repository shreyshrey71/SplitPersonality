package com.android.splitpersonality.Create_Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;

import com.android.splitpersonality.R;

public class CreateProfileActivity extends AppCompatActivity {

    Switch bluetooth, airplane;
    boolean b,a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        bluetooth = findViewById(R.id.Bluetooth);
        airplane = findViewById(R.id.airplane_mode);

        if(bluetooth.isChecked())
            b = true;
        if(airplane.isChecked())
            a = true;


    }
}
