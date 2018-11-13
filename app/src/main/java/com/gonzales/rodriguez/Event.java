package com.gonzales.rodriguez;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Event {
    public String name;
    public String location;
    public String date;
    public String type;

    public Event() {

    }

    public Event(String name, String location, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.name = name;
        this.location = location;
        this.date = sdf.format(new Date());
        this.type = type;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("location", location);
        result.put("date", date);
        result.put("type", type);
        return result;
    }
}
