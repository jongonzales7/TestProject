package com.gonzales.rodriguez;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewEventActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String key;
    TextView name, location, date, team, latLong, action, vehicle;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (TextView) findViewById(R.id.tvEventName);
        location = (TextView) findViewById(R.id.tvLocation);
        date = (TextView) findViewById(R.id.tvDate);
        team = (TextView) findViewById(R.id.tvTeam);
        latLong = (TextView) findViewById(R.id.tvLatLong);
        action = (TextView) findViewById(R.id.tvAction);
        vehicle = (TextView) findViewById(R.id.tvVehicle);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");


        key = getIntent().getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/").getReference().child("events");

        pd.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Event p = dataSnapshot1.getValue(Event.class);
                    if(p.getKey().equals(key)) {
                        getSupportActionBar().setTitle(p.getName());
                        name.setText(p.getName());
                        location.setText(p.getLocation());
                        String[] splited = (p.getDate()).split("\\s+");
                        date.setText(splited[0]);
                        team.setText(p.getTeam());
                        latLong.setText(p.getLatitude() + " / " + p.getLongitude());
                        action.setText(p.getAction());
                        vehicle.setText(p.getVehicle());

                    }

                }
                pd.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
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
