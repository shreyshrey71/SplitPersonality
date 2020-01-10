package com.android.splitpersonality.WiFi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.splitpersonality.R;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WifiActivity extends AppCompatActivity {

    WifiManager manager;
    WifiReceiver receiver;
    LocationManager locationManager;
    TextView text;
    List<ScanResult> list;
    ArrayList<String> provider,x,y;
    recyclerAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        manager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        provider = new ArrayList<>();
        x = new ArrayList<>();
        y = new ArrayList<>();
        listView = findViewById(R.id.list);
        text=findViewById(R.id.text);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location();
    }

    public void location()
    {
        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            new AlertDialog.Builder(this)
                    .setTitle("Location")
                    .setMessage("Please enable location")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }}
                    )
                    .show();
        }
    }

    //start scan
    public void scanning()
    {
        receiver = new WifiReceiver();
        registerReceiver(receiver, new IntentFilter(manager.SCAN_RESULTS_AVAILABLE_ACTION));
        manager.startScan();
    }
    //display keywords based on DBm strength
    public String returnlevel(int DBm)
    {
        if(DBm >= -50) return DBm+" dBm > Excellent";
        else if (DBm < -50 && DBm >= -60) return DBm+" dBm > Good";
        else if (DBm < -60 && DBm >= -70) return DBm+" dBm > Fair";
        else return DBm+" dBm > Poor";
    }
    // comparator for sort
    public static Comparator<ScanResult> comp = new Comparator<ScanResult>() {
        @Override
        public int compare(ScanResult a, ScanResult b) {
            return (Integer.compare(b.level, a.level));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                   Log.e("wifi","on");
                    if (ContextCompat.checkSelfPermission(WifiActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        location();
                        Toast.makeText(WifiActivity.this,"Please give Location Permission to app",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        scanning();
                        text.setVisibility(View.GONE);
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.e("wifi","off");
                    listView.setAdapter(null);
                    text.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    class WifiReceiver extends BroadcastReceiver {

        public void onReceive(Context c, Intent intent) {
            list = manager.getScanResults();
            Log.e("list",list.toString());
            Collections.sort(list, comp);
            // refresh list
            if(!provider.isEmpty()) provider.clear();
            if(!x.isEmpty()) x.clear();
            if(!y.isEmpty()) y.clear();

            String providerName;

            // print details
            for (int i = 0; i < list.size(); i++) {
                providerName = " "+(list.get(i).SSID)+" ("+(list.get(i).BSSID)+" )\n Frequency: "+(list.get(i).frequency)+" MHz\n Strength: "+returnlevel(list.get(i).level);
                provider.add(providerName);
                x.add((list.get(i).SSID));
                y.add(Integer.toString(list.get(i).level));
                Log.i("pn",providerName);
            }
            adapter = new recyclerAdapter(WifiActivity.this, provider);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//            location();
        }
    }

}
