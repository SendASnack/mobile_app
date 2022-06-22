package com.example.sendasnack.ui.map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendasnack.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GalleryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_gallery, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap = googleMap;
                LatLng latLng = new LatLng(40.638295566664766, -8.651389232635692);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title("Deliver");
                googleMap.addMarker(markerOptions.position(latLng));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                googleMap.animateCamera(cameraUpdate);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);
            }
        });
        // Return view
        return view;
    }
}