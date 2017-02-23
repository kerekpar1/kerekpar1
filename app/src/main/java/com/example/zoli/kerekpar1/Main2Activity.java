package com.example.zoli.kerekpar1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    private TextView gpsTextView;
    private TextView spdTextView;
    private TextView tTextView;

    private LocationManager lm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gpsTextView =  (TextView)findViewById(R.id.gps_textView);
        spdTextView =  (TextView)findViewById(R.id.spd_textView);
        tTextView =  (TextView)findViewById(R.id.time_textView);

        lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }

        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);
    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location l) {
            Date date = new Date(l.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tTextView.setText(sdf.format(date));

            spdTextView.setText(Float.toString(l.getSpeed()*3.6f));

            String r = String.format("lat:%f lon:%f alt:%f acc:%f", l.getLatitude(), l.getLongitude(), l.getAltitude(), l.getAccuracy());
            gpsTextView.setText(r);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    public void gpsButtonClicked(View view){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(l != null){
                Date date = new Date(l.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tTextView.setText(sdf.format(date));

                spdTextView.setText(Float.toString(l.getSpeed()*3.6f));

                String r = String.format("lat:%f lon:%f alt:%f acc:%f", l.getLatitude(), l.getLongitude(), l.getAltitude(), l.getAccuracy());
                gpsTextView.setText(r);
            }

            else
                gpsTextView.setText("nincs gps");
        }
        else{
            gpsTextView.setText("nincs gps jog");
        }
    }
}
