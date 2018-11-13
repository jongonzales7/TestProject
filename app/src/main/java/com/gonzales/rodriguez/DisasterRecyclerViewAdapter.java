package com.gonzales.rodriguez;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DisasterRecyclerViewAdapter extends RecyclerView.Adapter<DisasterRecyclerViewAdapter.MyViewHolder>{

    Context mContext;
    List<Disaster> mData;

    public DisasterRecyclerViewAdapter(Context mContext, List<Disaster> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_disaster,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.item_disaster.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, EventActivity.class);
                i.putExtra("name", mData.get(vHolder.getAdapterPosition()).getName());
                mContext.startActivity(i);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(mData.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_disaster;
        private TextView tv_name;

        public MyViewHolder (View itemView){
            super(itemView);
            item_disaster = (LinearLayout)itemView.findViewById(R.id.item_disaster);
            tv_name =  (TextView) itemView.findViewById(R.id.disasterText);
        }
    }

}
