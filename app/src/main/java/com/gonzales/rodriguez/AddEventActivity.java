package com.gonzales.rodriguez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText name, location, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/").getReference();
        name = (EditText) findViewById(R.id.etName);
        location = (EditText) findViewById(R.id.etLocation);
        type = (EditText) findViewById(R.id.etType);

    }

    public void addEvent(View v) {
        String key = databaseReference.child("events").push().getKey();
        Event event  = new Event(name.getText().toString(), location.getText().toString(), type.getText().toString());
        Map<String, Object> eventValues = event.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + key, eventValues);
        databaseReference.updateChildren(childUpdates);

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
}
