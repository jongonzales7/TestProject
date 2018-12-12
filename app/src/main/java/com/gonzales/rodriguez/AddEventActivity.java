package com.gonzales.rodriguez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference events, teams;
    EditText eName, eLocation, eLat, eLong, eAction, eVehicle;
    Spinner spinner, teamSpinner;
    ArrayList<String> teamNames;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot connect to Firebase! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
        }

        db = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/");
        events = db.getReference("events");
        teams = db.getReference("teams");

        teamNames = new ArrayList<String>();

        sp = getSharedPreferences("event", Context.MODE_PRIVATE);
        ed = sp.edit();

        eName = (EditText) findViewById(R.id.etName);
        eLocation = (EditText) findViewById(R.id.etLocation);
        eLat = (EditText) findViewById(R.id.etLat);
        eLong = (EditText) findViewById(R.id.etLong);
        eAction = (EditText) findViewById(R.id.etAction);
        eVehicle = (EditText) findViewById(R.id.etVehicle);
        spinner = (Spinner) findViewById(R.id.mySpinner);
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);

        eLocation.setEnabled(false);
        eLat.setEnabled(false);
        eLong.setEnabled(false);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.disasters_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

       /* ArrayAdapter<String> team_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teamNames);
        team_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(team_adapter);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        teams.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamNames.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Team t = dataSnapshot1.getValue(Team.class);
                    teamNames.add(t.getName());

                }
                ArrayAdapter<String> team_adapter = new ArrayAdapter<String>(AddEventActivity.this, android.R.layout.simple_spinner_item, teamNames);
                team_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                teamSpinner.setAdapter(team_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        eName.setText(sp.getString("name", ""));
        eLocation.setText(sp.getString("location", ""));
        eAction.setText(sp.getString("action", ""));
        eVehicle.setText(sp.getString("vehicle", ""));
        spinner.setSelection(sp.getInt("disaster", 0));
        teamSpinner.setSelection(sp.getInt("team", 0));
        eLat.setText(sp.getString("latitude", ""));
        eLong.setText(sp.getString("longitude", ""));

        eName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed.putString("name", s.toString()).commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       /* eLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed.putString("location", s.toString()).commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        eAction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed.putString("action", s.toString()).commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eVehicle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ed.putString("vehicle", s.toString()).commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                ed.putString("disaster", parentView.getItemAtPosition(position).toString()).commit();
                ed.putInt("disaster", position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                ed.putString("team", parentView.getItemAtPosition(position).toString()).commit();
                ed.putInt("team", position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.edit().clear().commit();
    }

    public void insertEvent(View v) {
        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot add event! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
            return;
        }


        String name = eName.getText().toString().trim();
        String location = eLocation.getText().toString().trim();
        Double lati, longi;
        String type = spinner.getSelectedItem().toString().trim();
        String team = teamSpinner.getSelectedItem().toString().trim();
        String action = eAction.getText().toString().trim();
        String vehicle = eVehicle.getText().toString().trim();

        if((eLat.getText().toString().trim()).length() <= 0 || (eLong.getText().toString().trim()).length() <= 0){
            Toast.makeText(this, "All fields are required. Please input missing fields...", Toast.LENGTH_LONG).show();
            return;
        }else{
            try{
                lati = Double.parseDouble(eLat.getText().toString().trim());
                longi = Double.parseDouble(eLong.getText().toString().trim());
            }catch (Exception ex){
                Toast.makeText(this, "Please input correct latitude and longitude...", Toast.LENGTH_LONG).show();
                return;
            }

        }

        if(name.length() <= 0 || location.length() <= 0 || action.length() <= 0 || vehicle.length() <= 0) {
            Toast.makeText(this, "All fields are required. Please input missing fields...", Toast.LENGTH_LONG).show();
            return;
        }

        String key = events.push().getKey();

        Event event = new Event(name, location, type, key, team, lati, longi, action, vehicle);
        events.child(key).setValue(event,  new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(AddEventActivity.this, "Event not added!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddEventActivity.this, "Event successfully added!", Toast.LENGTH_LONG).show();
                    eName.setText("");
                    eLocation.setText("");
                    eLat.setText("");
                    eLong.setText("");
                    eAction.setText("");
                    eVehicle.setText("");
                    sp.edit().clear().commit();
                }
            }
        });


       /* Map<String, Object> eventValues = event.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + key, eventValues);
        databaseReference.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(AddEventActivity.this, "Yey, event successfully added!", Toast.LENGTH_LONG).show();
            eName.setText("");
            eLocation.setText("");
        }
    })
            .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(AddEventActivity.this, "Ooops, event not added!", Toast.LENGTH_LONG).show();
        }
    });*/

    }

    public void selectLocation(View v) {
        Intent i = new Intent(this, SelectEventLocation.class);
        startActivity(i);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
