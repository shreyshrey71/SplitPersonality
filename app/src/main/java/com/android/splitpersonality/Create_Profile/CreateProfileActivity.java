package com.android.splitpersonality.Create_Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.splitpersonality.R;
import com.android.splitpersonality.SplitTransition;

public class CreateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplitTransition.prepareAnimation(this);
        setContentView(R.layout.activity_create_profile);

        SplitTransition.animate(this, 500);
    }
}
