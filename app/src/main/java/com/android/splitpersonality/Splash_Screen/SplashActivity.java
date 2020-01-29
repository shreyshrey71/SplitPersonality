package com.android.splitpersonality.Splash_Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.android.splitpersonality.Main_Screen.MainScreenActivity;
import com.android.splitpersonality.R;


public class SplashActivity extends AppCompatActivity {

    Handler mHandler;
    com.android.splitpersonality.WavesView top,bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        top = findViewById(R.id.topwave);
        bottom = findViewById(R.id.bottomwave);
       // Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        Animation transD = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translatedown);
        transD.setFillAfter(true);
        bottom.startAnimation(transD);

       /* final typewriter ty=(typewriter)findViewById(R.id.type);
        ty.setText("");
        ty.setCharacterDelay(100);
        ty.animateText("SPLIT");*/
        mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(intent);
            }
        },1500);
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
