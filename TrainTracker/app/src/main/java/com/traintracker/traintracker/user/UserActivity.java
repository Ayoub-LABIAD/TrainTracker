package com.traintracker.traintracker.user;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.map.IMapFragment;
import com.traintracker.traintracker.map.MapFragment;

public class UserActivity extends AppCompatActivity {
    IMapFragment iMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        MapFragment MapFrag = new MapFragment().newInstance(1);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, MapFrag);
        iMapFragment = MapFrag;
        ft.commit();

    }
}
