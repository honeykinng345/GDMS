package com.GDMS.Utilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.GDMS.activities.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class Utility {
  public  static List<Address> addresses;
   public static Geocoder geocoder;

    private static final int PERMISSION_ID = 44;
    public  static  double distanxceMilesValue=00.1;
    public static double lat2 = 100.1;
    public static double lon2 = 100.1;
    public static String completeAddress;
    public static FusedLocationProviderClient mFusedLocationClient;

    public static void launchActivity(Activity activity, Class to, boolean shouldFinish) {
        Intent intent = new Intent(activity, to);
        if (shouldFinish) {
            activity.startActivity(intent);
            activity.finish();
        } else {
            activity.startActivity(intent);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showSoftKeyboard(View view, Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hideSoftKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint("MissingPermission")
    public static void getLastLocation(Context context) {
        geocoder = new Geocoder(context);
        if (checkPermissions(context)) {

            // check if location is enabled
            if (isLocationEnabled(context)) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData(context);
                        } else {

                            lat2 = location.getLatitude();
                            lon2 = location.getLongitude();
                          /*  Toast.makeText(context, ""+lat2, Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, ""+lon2, Toast.LENGTH_SHORT).show();*/
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
                                completeAddress = addresses.get(0).getAddressLine(0);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            /*latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");*/
                        }
                    }
                });

            } else {
                Toast.makeText(context, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        } else {
            requestPermissions(context);
        }
    }

    @SuppressLint("MissingPermission")
    private static void requestNewLocationData(Context c) {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(c);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    public static LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            Log.d("Tag", "" + mLastLocation.getLatitude());
            lat2 = mLastLocation.getLatitude();
            lon2 = mLastLocation.getLongitude();


           /* latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");*/
        }
    };


    public static void requestPermissions(Context context) {

        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private static boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    //this funcationisn use get user lat and loung from Api Call and use in Maniactivity
    public static void getUserlatLoung(Double lat1, Double lon1) {

        distance(lat1, lon1,lat2,lon2);

    }
    //this funcation is get get distance in Miles b/w two locatiom
    private static double distance(Double lat1, Double lon1, double lat2, double lon2) {

            int Radius = 6371;// radius of earth in Km

            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                    * Math.sin(dLon / 2);
            double c = 2 * Math.asin(Math.sqrt(a));
            double valueResult = Radius * c;
            double km = valueResult / 1;
            DecimalFormat newFormat = new DecimalFormat("####");
            int kmInDec = Integer.valueOf(newFormat.format(km));
            double meter = valueResult % 1000;
            int meterInDec = Integer.valueOf(newFormat.format(meter));
            Log.d("Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec);
       distanxceMilesValue = meterInDec;
            return Radius * c;
        }


/*
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static double milesToMeters(double miles) {
        return miles / 0.0006213711;
    }
*/


}
