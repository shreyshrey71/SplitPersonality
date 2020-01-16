package com.android.splitpersonality.Main_Screen;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.splitpersonality.Create_Profile.CreateProfileActivity;
import com.android.splitpersonality.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenActivity extends AppCompatActivity {
    @BindView(R.id.qrscan)
    FloatingActionButton qrscan;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profile_recycler)
    RecyclerView profileRecycler;
    ArrayList<Profiles> list;
    ProfileListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        list=prof();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),CreateProfileActivity.class);
                startActivity(in);
            }
        });
        qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainScreenActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan QR code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        adapter=new ProfileListAdapter(MainScreenActivity.this,list);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        profileRecycler.setLayoutManager(mLayoutManager);
        profileRecycler.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    list.add(new Profiles(obj.getString("Date"),obj.getString("time")));
                    // adapter=new ProfileListAdapter(MainScreenActivity.this,list);
                    adapter.notifyItemInserted(list.size() - 1);
                    // adapter=new ProfileListAdapter(MainScreenActivity.this,list);
                    //profileRecycler.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private ArrayList<Profiles>  prof(){
        list=new ArrayList<>();

        list.add(new Profiles("12/12/2019","12:30pm"));
        list.add(new Profiles("11/12/2029","11:20am"));
        list.add(new Profiles("11/1/2017","3:00pm"));
        list.add(new Profiles("12/12/2019","12:30pm"));
        list.add(new Profiles("11/12/2029","11:20am"));
        //list.add(new Profiles("11/1/2017","3:00pm"));
        return list;
    }

}

