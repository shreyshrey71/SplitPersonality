package com.android.splitpersonality.Splash_Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.android.splitpersonality.Main_Screen.MainScreenActivity;
import com.android.splitpersonality.R;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler;
    Button b1;
    ImageView split,i1,i2,i3,i4;
    Animation for_button, for_i1,for_i2,for_i3,for_i4,for_split;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);
        /*b1 = (Button) findViewById(R.id.button3);
        split = (ImageView) findViewById(R.id.imageView6);
        i1 = (ImageView) findViewById(R.id.imageView2);
        i2 = (ImageView) findViewById(R.id.imageView3);
        i3 = (ImageView) findViewById(R.id.imageView4);
        i4 = (ImageView) findViewById(R.id.imageView5);
        for_button = AnimationUtils.loadAnimation(this,R.anim.for_button);
        b1.setAnimation(for_button);
        for_split = AnimationUtils.loadAnimation(this,R.anim.for_split);
        for_i1 = AnimationUtils.loadAnimation(this,R.anim.for_images);
        split.setAnimation(for_split);
        i1.setAnimation(for_i1);*/
        mHandler=new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(), MainScreenActivity.class);
                startActivity(intent);
            }
        },2000);
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
