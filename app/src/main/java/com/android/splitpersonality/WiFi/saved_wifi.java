package com.android.splitpersonality.WiFi;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.AlphabeticIndex;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.splitpersonality.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class saved_wifi extends Fragment{
    private static final String TAG = "";
    private PageViewModel pageViewModel;
    public saved_wifi() {}
    recyclerAdapter adapter;
    ArrayList provider,bss;
    SharedPreferences sharedPref;String toastString;

    public static saved_wifi newInstance() {
        return new saved_wifi();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of((Fragment) this).get(PageViewModel.class);
        pageViewModel.setIndex(TAG);
        provider = new ArrayList<>(); bss = new ArrayList<>();
        SharedPreferences prefs = getActivity().getSharedPreferences("wifi_saved", Context.MODE_PRIVATE);
        String storedHashMapString = prefs.getString("bssids", "oopsDintWork");
        Log.e("abcdc1",storedHashMapString);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Pair<String,Boolean>>>(){}.getType();
        Gson gson = new Gson();
        HashMap<String, Pair<String,Boolean>> testHashMap2 =new HashMap<String, Pair<String,Boolean>>();
        testHashMap2=  gson.fromJson(storedHashMapString, type);
        Boolean failed=false;
        //use values
        try
        {
            for(Map.Entry<String, Pair<String,Boolean>> entry: testHashMap2.entrySet()) {
                provider.add(entry.getKey() + " : " + entry.getValue().first);
                bss.add(entry.getKey() + " : " + entry.getValue().first);
                Log.e("abhi",entry.getKey() + " : " + entry.getValue().first);
            }
        }
        catch (IllegalStateException | JsonSyntaxException exception) {
            failed = true;
            //...
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_wifi, container, false);
        final ListView listView = root.findViewById(R.id.list);
        final TextView text2 = root.findViewById(R.id.text2);
        final Button save = root.findViewById(R.id.button);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                text2.setText("Choose SSID's to save so as profiles trigger when device connects to these WAPs!");
                adapter=new recyclerAdapter(getActivity(),provider,bss);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<listView.getCount();i++)
                {
                    View vi = listView.getAdapter().getView(i,null,null);
                    TextView et =  vi.findViewById(R.id.bssid);
                    CheckBox check = vi.findViewById(R.id.checkBox);
                    if(check.isChecked() && et!=null)
                    {
                        Log.e("d"+i,et.getText().toString());
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("wifi_info",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(et.getText().toString(), true);
                        editor.apply();
                    }
                }
            }
        });
        return root;
    }
}
