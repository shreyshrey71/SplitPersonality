package com.android.splitpersonality.WiFi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.android.splitpersonality.R;
import com.google.gson.Gson;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class all_wifi extends Fragment{
    private static final String TAG = "";
    private PageViewModel pageViewModel;
    public all_wifi() {}
    recyclerAdapter adapter;
    ArrayList provider,bss;
    SharedPreferences sharedPref;

    public static all_wifi newInstance() {
        return new all_wifi();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of((Fragment) this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
        provider=new ArrayList<>();
        bss=new ArrayList<>();
        sharedPref = getActivity().getSharedPreferences("wifi_info",Context.MODE_PRIVATE);
        String defaultValue = "none";
        provider = new ArrayList<String>(Arrays.asList(sharedPref.getString("wifis", defaultValue).split(",")));
        bss = new ArrayList<String>(Arrays.asList(sharedPref.getString("bssid", defaultValue).split(",")));
        SharedPreferences settings = getActivity().getSharedPreferences("checks", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_wifi, container, false);
//        final TextView textView = root.findViewById(R.id.text);
        final ListView listView = root.findViewById(R.id.list);
        final TextView text2 = root.findViewById(R.id.text2);
        final Button save = root.findViewById(R.id.button);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                text2.setText("Choose SSID's to save so as profiles trigger when device connects to these WAPs!");
                adapter=new recyclerAdapter(getActivity(),provider,bss);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Pair<String,Boolean>> map = new HashMap<String, Pair<String,Boolean>>();
                for(int i=0;i<listView.getCount();i++)
                {
                    View vi = listView.getAdapter().getView(i,null,null);
                    TextView name =  vi.findViewById(R.id.wifiname);
                    TextView bssid = vi.findViewById(R.id.bssid);
                    CheckBox check = vi.findViewById(R.id.checkBox);
                    if(check.isChecked())
                    {
                        Log.e("d"+i,name.getText().toString());
                        map.put(bssid.getText().toString(),Pair.create(name.getText().toString(),true));
                    }
                }
                Gson gson = new Gson();
                String hashMapString = gson.toJson(map);
                SharedPreferences prefs = getActivity().getSharedPreferences("wifi_saved", Context.MODE_PRIVATE);
                prefs.edit().putString("bssids", hashMapString).apply();

                SharedPreferences prefs1 = getActivity().getSharedPreferences("wifi_saved", Context.MODE_PRIVATE);
                String storedHashMapString = prefs1.getString("bssids", "oopsDintWork");
                Log.e("abcdc1",storedHashMapString);
            }
        });
        return root;
    }
}