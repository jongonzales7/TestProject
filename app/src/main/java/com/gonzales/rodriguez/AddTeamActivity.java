package com.gonzales.rodriguez;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTeamActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference teams, root;
    EditText eTeamName, eMember1, eMember2, eMember3;
    ArrayList<Team> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot connect to Firebase! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
        }

        db = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/");
        teams = db.getReference("teams");
        root = db.getReference();

        eTeamName = (EditText) findViewById(R.id.etTeamName);
        eMember1 = (EditText) findViewById(R.id.etMember1);
        eMember2 = (EditText) findViewById(R.id.etMember2);
        eMember3 = (EditText) findViewById(R.id.etMember3);

        teamList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        teams.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Team t = dataSnapshot1.getValue(Team.class);
                    teamList.add(t);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void insertTeam(View v) {
        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot add team! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
            return;
        }


        String name = eTeamName.getText().toString();
        String mem1 = eMember1.getText().toString();
        String mem2 = eMember2.getText().toString();
        String mem3 = eMember3.getText().toString();

        if(name.length() <= 0 ||  mem1.length() <= 0 ||  mem2.length() <= 0 ||  mem3.length() <= 0){
            Toast.makeText(this, "All fields are required. Please input missing fields...", Toast.LENGTH_LONG).show();
            return;
        }

        for (int x = 0; x < teamList.size(); x++){
            if((teamList.get(x).getName()).equals(name)){
                Toast.makeText(this, "Record already exists...", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String key = teams.push().getKey();

        ArrayList<String> members = new ArrayList<>();
        members.add(mem1);
        members.add(mem2);
        members.add(mem3);

        Team team = new Team(name, members, key);
        teams.child(key).setValue(team, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(AddTeamActivity.this, "Error adding team! " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddTeamActivity.this, "Team successfully added!", Toast.LENGTH_LONG).show();
                    eTeamName.setText("");
                    eMember1.setText("");
                    eMember2.setText("");
                    eMember3.setText("");
                }
            }
        });
       /* Map<String, Object> teamValues = team.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/teams/" + key, teamValues);
        root.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddTeamActivity.this, "Yey, team successfully added!", Toast.LENGTH_LONG).show();
                eTeamName.setText("");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTeamActivity.this, "Ooops, team not added!", Toast.LENGTH_LONG).show();
                    }
                });*/
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
