package com.soba.soba.sandbox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.tiles.AerisTile;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends MapViewFragment implements OnAerisMapLongClickListener {


    public MapFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        double lat = args.getDouble(MapActivity.lat);
        double lon = args.getDouble(MapActivity.lon);

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);
        mapView.setOnAerisMapLongClickListener(this);
        mapView.moveToLocation(new LatLng(lat, lon), 9);
        mapView.addLayer(AerisTile.RADSAT);

        return view;
    }

    @Override
    public void onMapLongClick(double v, double v1) {

    }
}
