package com.traintracker.traintracker.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, IMapFragment {

    Map<String, Marker> markers;
    IMapFragmentPresenter iMapFragmentPresenter;
    private LatLngBounds MOROCCO_ZOOM = new LatLngBounds(
            new LatLng(21, -18), new LatLng(35, 0));
    private LatLngBounds MOROCCO_CAM = new LatLngBounds(
            new LatLng(21, -16), new LatLng(32, -1));
    private RelativeLayout parent;
    private GoogleMap mMap;
    private MapView mapView;
    private boolean mapsSupported = true;

    public static MapFragment newInstance(int page) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getActivity());

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initializeMap();
    }

    private void initializeMap() {
        if (mMap == null && mapsSupported) {
            mapView = (MapView) getActivity().findViewById(R.id.map);
            mapView.getMapAsync(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = (RelativeLayout) inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) parent.findViewById(R.id.map);

        return parent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        initializeMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(MOROCCO_ZOOM, 0));
                mMap.setLatLngBoundsForCameraTarget(MOROCCO_CAM);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) parent.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
        markers = new HashMap<>();
        iMapFragmentPresenter = new MapFragmentPresenter(this);
    }

    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FilterTrainsDialogFragment filterTrainsDialogFragment = FilterTrainsDialogFragment.newInstance(this);
        filterTrainsDialogFragment.show(fm, "fragment_filter_trains");
    }

    // Create a mraker depending on the train's type
    public void setMarker(Train train) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(getLocation(train.getDeparture())).title(train.getName());
        // Changing marker icon
        String trainIcon = train.getType();

        if (trainIcon.equals("Type A")) {
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.train_blue));
        }
        if (trainIcon.equals("Type B")) {
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.train_green));
        }
        if (trainIcon.equals("Type C")) {
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.train_orange));
        }
        if (trainIcon.equals("Type D")) {
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.train_red));
        }
        markers.put(train.getId(), mMap.addMarker(marker));
    }

    public LatLng getLocation(String name) {
        switch (name) {
            case "Marrakech":
                return new LatLng(31.628902, -7.956848);
            case "Rabat":
                return new LatLng(34.047041, -6.858215);
            case "Tan-Tan":
                return new LatLng(28.506184, -11.011047);
            case "Fes":
                return new LatLng(33.914945, -4.858704);
            case "Oujda":
                return new LatLng(34.605025, -1.980286);
            case "Dakhla":
                return new LatLng(23.693656, -15.735168);
            case "Tanger":
                return new LatLng(35.772214, -5.825500);
            default:
                return new LatLng(31.628902, -7.956848);
        }

    }

    public void setMap(HashSet<Train> trains) {
        mMap.clear();
        for (Train tmp : trains) {
            setMarker(tmp);
            moveMarker(markers.get(tmp.getId()), markers.get(tmp.getId()).getPosition(), getLocation(tmp.getDestination()));
        }
    }

    public void setMap(ArrayList<Train> trains) {
        mMap.clear();
        for (Train tmp : trains) {
            setMarker(tmp);
            moveMarker(markers.get(tmp.getId()), markers.get(tmp.getId()).getPosition(), getLocation(tmp.getDestination()));
        }
    }

    public void moveMarker(Marker marker, LatLng startLatLng, LatLng toPosition) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 90000;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }

            }
        });
    }


}


