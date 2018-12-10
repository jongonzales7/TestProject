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
    public String key;
    public String team;
    public Double latitude;
    public Double longitude;
    public String action;
    public String vehicle;


    public Event() {

    }

    public Event(String name, String location, String type, String key, String team, Double latitude, Double longitude, String action, String vehicle) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        this.name = name;
        this.location = location;
        this.date = sdf.format(new Date());
        this.type = type;
        this.key = key;
        this.team = team;
        this.latitude = latitude;
        this.longitude = longitude;
        this.action = action;
        this.vehicle = vehicle;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("name", name);
        result.put("location", location);
        result.put("date", date);
        result.put("type", type);
        result.put("team", team);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("action", action);
        result.put("vehicle", vehicle);
        return result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
