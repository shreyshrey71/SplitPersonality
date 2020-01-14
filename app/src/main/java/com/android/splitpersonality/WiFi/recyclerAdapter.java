package com.android.splitpersonality.WiFi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.splitpersonality.R;

import java.util.List;

public class recyclerAdapter extends ArrayAdapter<String> {

    Activity act;
    List<String> wifinames,bssid;
    private LayoutInflater inflater;

    public recyclerAdapter(Activity a, List<String> wifiName,  List<String> bssid) {
        super(a, R.layout.list_style, wifiName);
        act = a;
        inflater = LayoutInflater.from(act);
        this.wifinames = wifiName;
        this.bssid=bssid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_style, parent, false);
        TextView wifiProvider = convertView.findViewById(R.id.wifiname);
        TextView bssid1 = convertView.findViewById(R.id.bssid);
        wifiProvider.setText(wifinames.get(position));
        bssid1.setText(bssid.get(position));
        return convertView;
    }
}
