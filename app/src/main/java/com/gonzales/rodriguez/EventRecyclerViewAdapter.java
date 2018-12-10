package com.gonzales.rodriguez;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {
    Context mContext;
    List<Event> mData;

    // data is passed into the constructor
    public EventRecyclerViewAdapter(Context context, List<Event> data) {
        this.mContext = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_event,parent,false);
        final EventRecyclerViewAdapter.ViewHolder vHolder = new EventRecyclerViewAdapter.ViewHolder(v);
        vHolder.item_event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ViewEventActivity.class);
                i.putExtra("key", mData.get(vHolder.getAdapterPosition()).getKey());
                mContext.startActivity(i);
            }
        });
        return vHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        holder.location.setText(mData.get(position).getLocation());
        String[] splited = (mData.get(position).getDate()).split("\\s+");
        holder.date.setText(splited[0]);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView location;
        private TextView date;
        private LinearLayout item_event;

        public ViewHolder(View itemView) {
            super(itemView);
            item_event = (LinearLayout) itemView.findViewById(R.id.item_event);
            name = itemView.findViewById(R.id.eventTitleText);
            location = itemView.findViewById(R.id.eventLocation);
            date = itemView.findViewById(R.id.eventDate);
        }

    }

}
