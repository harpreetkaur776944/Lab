package com.example.lab;


import androidx.fragment.app.FragmentActivity;

import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    TextView locate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        locate = findViewById(R.id.textViewAdd);
        String str = "iTestLabs<br>Old Lal Bagh Mohalla<br>Patiala,Punjab,147001<br>Contact No - 9988125047<br>Email-id - itestlabsproject@gmail.com ";
        locate.setText(Html.fromHtml(str).toString());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, (LocationListener) this);
        */
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
        double  lattitude =30.3307836; //address.getLatitude();
        double longitude = 76.3990035; //address.getLongitude();

        LatLng india = new LatLng(lattitude,longitude);
        mMap.addMarker(new MarkerOptions().position(india).title("iTestLabs Old Lal Bagh Mohalla"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(india).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);

        /*
        String loc = "National High School ,Near Books Market Rd, Old Lal Bagh Mohalla, Patiala, Punjab 147001";
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> list = geocoder.getFromLocationName(loc,4);
            Address address = list.get(0);
            double  lattitude =30.3307836; //address.getLatitude();
            double longitude = 76.3990035; //address.getLongitude();
            //String addressLine = address.getAddressLine(0);
            String subLocality = address.getSubLocality();
            LatLng india = new LatLng(lattitude,longitude);
            mMap.addMarker(new MarkerOptions().position(india).title("iTestLabs "+subLocality));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(india));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

         */
    }

}
