package com.android.splitpersonality.Main_Screen;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.splitpersonality.R;
import com.android.splitpersonality.qrCode;

import java.util.ArrayList;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Profiles> profilelist;

    public ProfileListAdapter(Context mContext, ArrayList<Profiles> profilelist) {
        this.mContext = mContext;
        this.profilelist=profilelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.profile_list,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(profilelist.get(position).getDate());
        holder.time.setText(profilelist.get(position).getTime());
        holder.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(mContext, qrCode.class);
                in.putExtra("date",profilelist.get(position).getDate());
                in.putExtra("time",profilelist.get(position).getTime());
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profilelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView date;
        TextView time;
        Button qrcode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view=itemView;
            date=(TextView) itemView.findViewById(R.id.profile_name);
            time=(TextView) itemView.findViewById(R.id.textV);
            qrcode=(Button)itemView.findViewById(R.id.button2);
        }
    }
}