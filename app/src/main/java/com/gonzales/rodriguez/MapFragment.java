package com.gonzales.rodriguez;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    FirebaseDatabase db;
    DatabaseReference events;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    ArrayList<Event> eventList;
    Marker marker;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance("https://testproject-65084.firebaseio.com/");
        events = db.getReference("events");
        eventList = new ArrayList<>();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        pd.show();
        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Event e = dataSnapshot1.getValue(Event.class);
                    String[] splited = (e.getDate()).split("\\s+");
                    LatLng location = new LatLng(e.getLatitude(), e.getLongitude());

                    switch(e.getType()) {
                        case "Fire":
                            marker = googleMap.addMarker(new MarkerOptions().position(location).title(e.getName()).snippet("What: " + e.getType() + "\nWhere: " +  e.getLocation() + "\nWhen: " + splited[0])
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
                            marker.setTag(e.getKey());
                            break;
                        case "Typhoon":
                            marker = googleMap.addMarker(new MarkerOptions().position(location).title(e.getName()).snippet("What: " + e.getType() + "\nWhere: " +  e.getLocation() + "\nWhen: " + splited[0])
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.typhoon)));
                            marker.setTag(e.getKey());
                            break;
                        case "Earthquake":
                            marker = googleMap.addMarker(new MarkerOptions().position(location).title(e.getName()).snippet("What: " + e.getType() + "\nWhere: " +  e.getLocation() + "\nWhen: " + splited[0])
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.earthquake)));
                            marker.setTag(e.getKey());
                            break;
                        case "Flood":
                            marker = googleMap.addMarker(new MarkerOptions().position(location).title(e.getName()).snippet("What: " + e.getType() + "\nWhere: " +  e.getLocation() + "\nWhen: " + splited[0])
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.flood)));
                            marker.setTag(e.getKey());
                            break;

                        default:
                            break;
                    }

                    googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        @Override
                        public View getInfoWindow(Marker arg0) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {

                            LinearLayout info = new LinearLayout(getContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            info.addView(title);
                            info.addView(snippet);

                            return info;
                        }
                    });
                    /*marker = googleMap.addMarker(new MarkerOptions().position(location).title(e.getName()).snippet(e.getType() + " in " +  e.getLocation() + "  on " + splited[0])
                                .icon(bitmapDescriptor));
                    marker.setTag(e.getKey());*/

                }

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(getActivity(),ViewEventActivity.class);
                        intent.putExtra("key", (String)marker.getTag());
                        startActivity(intent);


                    }
                });

                pd.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       /* for(int i = 0; i < eventList.size(); i++) {
            Event e = eventList.get(i);
            String[] splited = (e.getDate()).split("\\s+");
            LatLng location=new LatLng(e.getLatitude(), e.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(location).title(e.getName()).snippet(e.getType() + " in " +  e.getLocation() + "  on " + splited[0]));
        }*/


        //googleMap.addMarker(new MarkerOptions().position(new LatLng(14.641070, 121.128780)).title("Nice once").snippet(" Boom  tarat tarat"));

        CameraPosition cp = CameraPosition.builder().target(new LatLng(14.599512, 120.984222)).zoom(5).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

}
