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
        Bundle args = getArguments();

//        AerisEngine.initWithKeys(args.getString(ResultActivity.AERIS_CLIENT_ID),
//                args.getString(ResultActivity.AERIS_CLIENT_SECRET), getActivity());
        AerisEngine.initWithKeys("PsOTNfEJQmERqIBAOlJKQ",
                "wHBP7sQflsQlFEulY8BRPc9GrBQhxFMxT7SiJ7Kf", getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);
        mapView.setOnAerisMapLongClickListener(this);
        mapView.moveToLocation(new LatLng(34.031726, -118.289371), 9);
        mapView.addLayer(AerisTile.RADSAT);

        return view;
    }

    @Override
    public void onMapLongClick(double v, double v1) {

    }
}
