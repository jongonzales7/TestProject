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
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTeamActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    DatabaseReference databaseReference;
    EditText eTeamName;
    List<EditText> eTeamMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        linearLayout = (LinearLayout) findViewById(R.id.memberLinearLayout);

        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot connect to Firebase! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
        }

        eTeamName = (EditText) findViewById(R.id.etTeamName);
    }

    public void addMember(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.member, null);
        // Add the new row before the add field button.
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);
    }

    public void deleteMember(View v) {
        linearLayout.removeView((View) v.getParent());
    }

    public void addTeam(View v) {
        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot add team! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
            return;
        }

        String key = databaseReference.child("teams").push().getKey();

        String teamName = eTeamName.getText().toString();

        Team team = new Team(teamName, null, key);
        Map<String, Object> teamValues = team.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/teams/" + key, teamValues);
        databaseReference.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
