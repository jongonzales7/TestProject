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

import java.util.List;

public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    List<Team> mData;

    public TeamRecyclerViewAdapter(Context mContext, List<Team> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_team,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.item_team.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TeamActivity.class);
                i.putExtra("name", mData.get(vHolder.getAdapterPosition()).getName());
                mContext.startActivity(i);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.teamText.setText(mData.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_team;
        private TextView teamText;

        public MyViewHolder (View itemView){
            super(itemView);
            item_team = (LinearLayout)itemView.findViewById(R.id.item_team);
            teamText =  (TextView) itemView.findViewById(R.id.teamText);
        }
    }
}
