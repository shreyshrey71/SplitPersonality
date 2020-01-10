package com.android.splitpersonality.Splash_Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.splitpersonality.Main_Screen.MainScreenActivity;
import com.android.splitpersonality.R;
import com.android.splitpersonality.typewriter;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        final typewriter ty=(typewriter)findViewById(R.id.type);
        ty.setText("");
        ty.setCharacterDelay(100);
        ty.animateText("SPLIT");
        mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(intent);
            }
        },900);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
