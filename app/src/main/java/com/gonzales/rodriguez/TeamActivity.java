package com.gonzales.rodriguez;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference teams, members;
    String name;
    TextView tName, tMember1, tMember2, tMember3;
    ArrayList<String> teamMembers;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");

        db = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/");
        teams = db.getReference("teams");

        tName = (TextView) findViewById(R.id.tvEventName);
        tMember1 = (TextView) findViewById(R.id.tvMember1);
        tMember2 = (TextView) findViewById(R.id.tvMember2);
        tMember3 = (TextView) findViewById(R.id.tvMember3);

        name = getIntent().getStringExtra("name");
        teamMembers = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pd.show();
        teams.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Team t = dataSnapshot1.getValue(Team.class);
                    if(t.getName().equals(name)){
                        getSupportActionBar().setTitle(t.getName());
                        tName.setText(t.getName());
                        teamMembers = t.getMembers();
                        tMember1.setText(teamMembers.get(0));
                        tMember2.setText(teamMembers.get(1));
                        tMember3.setText(teamMembers.get(2));

//                        for(DataSnapshot ds : dataSnapshot.child(t.getKey()).child("members").getChildren()){
//                            tMember1.setText(ds.getValue(String.class));
//                        }

                    }
                }

                pd.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //Code for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
