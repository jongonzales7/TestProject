package com.gonzales.rodriguez;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    TeamRecyclerViewAdapter teamRecyclerViewAdapter;
    private List<Team> listTeam;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.team_recyclerview);
        teamRecyclerViewAdapter = new TeamRecyclerViewAdapter(getContext(), listTeam);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(teamRecyclerViewAdapter);



        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTeam = new ArrayList<>();
        String[] teams = getResources().getStringArray(R.array.teams_array);
        for(int i = 0; i < teams.length; i++) {
            listTeam.add(new Team(teams[i], null, null));
        }

//        databaseReference = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/").getReference().child("teams");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listTeam = new ArrayList<Team>();
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    Team p = dataSnapshot1.getValue(Team.class);
//                    listTeam.add(p);
//
//
//                }
//                teamRecyclerViewAdapter = new TeamRecyclerViewAdapter(getContext(),listTeam);
//                recyclerView.setAdapter(teamRecyclerViewAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
