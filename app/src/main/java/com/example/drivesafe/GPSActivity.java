package com.example.drivesafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class GPSActivity extends AppCompatActivity implements LocationListener {
    private Location lastLocation = null;
    private long lastLocationTime = 0L;
    private Activity mContext = this;
    private LocationManager mLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);
        this.mLocationManager = (LocationManager)this.mContext.getSystemService("location");
        if (ActivityCompat.checkSelfPermission((Context)this.mContext, "android.Manifest.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission((Context)this.mContext, "android.Manifest.permission.ACCESS_COARSE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this.mContext, new String[] { "android.Manifest.permission.ACCESS_FINE_LOCATION" }, 1);
            ActivityCompat.requestPermissions(this.mContext, new String[] { "android.Manifest.permission.ACCESS_COARSE_LOCATION" }, 1);
            return;
        }
        start();
    }

    private void start() {
        if (ActivityCompat.checkSelfPermission((Context)this.mContext, "android.Manifest.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission((Context)this.mContext, "android.Manifest.permission.ACCESS_COARSE_LOCATION") != 0) {
            Toast.makeText((Context)this.mContext, "Permission denied!", 1).show();
            return;
        }
        this.mLocationManager.requestLocationUpdates("gps", 0L, 0.0F, this);
    }

    private void stop() {
        this.mLocationManager.removeUpdates(this);
    }

    public double getLatitude() {
        return !isLocationAvailable() ? 0.0D : this.lastLocation.getLatitude();
    }

    public double getLongitude() {
        return !isLocationAvailable() ? 0.0D : this.lastLocation.getLatitude();
    }

    public float getSpeed() {
        return !isLocationAvailable() ? 0.0F : (this.lastLocation.getSpeed() * 3.6F);
    }

    public boolean isLocationAvailable() {
        if (System.currentTimeMillis() - this.lastLocationTime > 3000L)
            this.lastLocation = null;
        return (this.lastLocation != null);
    }

    public void onLocationChanged(Location paramLocation) {
        this.lastLocation = paramLocation;
        this.lastLocationTime = System.currentTimeMillis();
    }

    protected void onPause() {
        super.onPause();
        stop();
    }

    public void onProviderDisabled(String paramString) {}

    public void onProviderEnabled(String paramString) {}

    public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
        if (paramInt != 1)
            return;
        if (paramArrayOfint.length > 0 && paramArrayOfint[0] == 0) {
            start();
            return;
        }
        Toast.makeText((Context)this.mContext, "Permission denied!", 1).show();
    }

    protected void onResume() {
        super.onResume();
        start();
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
}