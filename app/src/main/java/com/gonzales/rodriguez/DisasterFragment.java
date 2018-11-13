package com.gonzales.rodriguez;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DisasterFragment extends Fragment{
    View v;
    private RecyclerView recyclerView;
    private List<Disaster> listDisaster;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_disaster, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.disaster_recyclerview);
        DisasterRecyclerViewAdapter disasterRecyclerViewAdapter = new DisasterRecyclerViewAdapter(getContext(), listDisaster);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(disasterRecyclerViewAdapter);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listDisaster = new ArrayList<>();
        listDisaster.add(new Disaster("Fire"));
        listDisaster.add(new Disaster("Flood"));
        listDisaster.add(new Disaster("Tornado"));
        listDisaster.add(new Disaster("Earthquake"));
        listDisaster.add(new Disaster("Hurricane"));
        listDisaster.add(new Disaster("Tsunami"));

    }
}
