package com.android.splitpersonality.Create_Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.splitpersonality.R;
import com.android.splitpersonality.SplitTransition;

import java.util.Calendar;

public class CreateProfileActivity extends AppCompatActivity {
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplitTransition.prepareAnimation(CreateProfileActivity.this);
        setContentView(R.layout.activity_create_profile);
      //
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        findViewById(R.id.buttonAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We need a calendar object to get the specified time in millis
                //as the alarm manager method takes time in millis to setup the alarm
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(), timePicker.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                }


                startAlert(calendar.getTimeInMillis());
            }
        });
   SplitTransition.animate(this, 500);

    }

    public void startAlert(long time){
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarm to repeat everyday
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,24*60*1000*60, pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();
    }
}
