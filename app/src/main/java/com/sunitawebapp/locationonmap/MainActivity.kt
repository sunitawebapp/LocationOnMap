package com.sunitawebapp.locationonmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var smf =getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this);
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map;

        // Add a marker in Location and move the camera
        val loc = LatLng(22.6230272, 88.4441088)
        mMap.addMarker(
            MarkerOptions()
                .position(loc)
                .title("Marker in Location")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }
}