package com.gonzales.rodriguez;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText eName, eLocation;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot connect to Firebase! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
        }

        databaseReference = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/").getReference();
        eName = (EditText) findViewById(R.id.etName);
        eLocation = (EditText) findViewById(R.id.etLocation);
        spinner = (Spinner) findViewById(R.id.mySpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.disasters_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void addEvent(View v) {
        if(!isNetworkAvailable()) {
            Toast.makeText(this, "Cannot add event! Please check Wi-Fi connectivity", Toast.LENGTH_LONG).show();
            return;
        }

        String key = databaseReference.child("events").push().getKey();

        String name = eName.getText().toString();
        String location = eLocation.getText().toString();
        String type = spinner.getSelectedItem().toString();

        Event event = new Event(name, location, type, key);
        Map<String, Object> eventValues = event.toMap();

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
