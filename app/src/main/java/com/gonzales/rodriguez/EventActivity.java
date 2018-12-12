package com.gonzales.rodriguez;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Event> events;
    EventRecyclerViewAdapter eventRecyclerViewAdapter;
    TextView tvName, tvLocation, tvDate;
    String disasterName;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");

        disasterName = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(disasterName);

        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot connect to Firebase! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.event_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/").getReference().child("events");
//        Query sortEventsByDisaster = databaseReference.orderByChild("type").equalTo(disasterName);

        pd.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events = new ArrayList<Event>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Event p = dataSnapshot1.getValue(Event.class);
                    if(p.getType().equals(disasterName)) {
                        events.add(p);
                    }

                }
                eventRecyclerViewAdapter = new EventRecyclerViewAdapter(EventActivity.this,events);
                recyclerView.setAdapter(eventRecyclerViewAdapter);

                pd.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EventActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

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
