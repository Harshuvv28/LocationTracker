package com.text.textr01.locationtracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.text.textr01.locationtracker.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    DatabaseReference dataref;
    String d1, d2;
    double l1, l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        dataref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1:snapshot.getChildren()       )
                {
                    String d3 = (String) snapshot1.child("name").getValue();
                    for (DataSnapshot snap : snapshot1.getChildren())
                    {
                        d1 = (String) snap.child("lat").getValue();
                        d2 = (String) snap.child("long").getValue();

                        if (d1 != null) {
                            l1 = Double.valueOf(d1);
                           //Toast.makeText(MapsActivity.this, String.valueOf(l1), Toast.LENGTH_SHORT).show();
                        }
                        if (d2 != null) {
                            l2 = Double.valueOf(d2);
                            //Toast.makeText(MapsActivity.this, String.valueOf(l2), Toast.LENGTH_SHORT).show();
                        }
                        addmarker(l1,l2,d3);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }
    public void addmarker(double l1, double l2, String d3)
    {
        LatLng sydney = new LatLng(this.l1, this.l2);
        mMap.addMarker(new MarkerOptions().position(sydney).title(d3));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

