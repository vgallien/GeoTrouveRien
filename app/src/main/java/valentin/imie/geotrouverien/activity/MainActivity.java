package valentin.imie.geotrouverien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import valentin.imie.geotrouverien.async.GetCountries;
import valentin.imie.geotrouverien.map.InfoWindow;
import valentin.imie.geotrouverien.tools.MyTools;
import valentin.imie.geotrouverien.model.Pays;
import valentin.imie.geotrouverien.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient bigBrother;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MyTools.REQUEST_CODE);
        } else {
            initMap();
        }

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CountriesActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MyTools.REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMap();
                } else {
                    Toast.makeText(this, "PAS AUTORISÉE", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void initMap() {
        bigBrother = LocationServices.getFusedLocationProviderClient(this);
        bigBrother.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    loc = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MainActivity.this);
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng imieLatLng = new LatLng(49.203133,-0.333796);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(imieLatLng);
        markerOptions.title("IMIE");
        markerOptions.snippet("IMIE DE CAEN");

        Marker m = googleMap.addMarker(markerOptions);
        m.setTag(new Integer(R.drawable.logo_imie));
        m.showInfoWindow();

        LatLng chu = new LatLng(49.2053247,-0.3663844);
        MarkerOptions markerChu = new MarkerOptions();
        markerChu.position(chu);
        markerChu.title("CHU");
        markerChu.snippet("Vous ne viendrez pas chez nous par hasard");

        Marker m2 = googleMap.addMarker(markerChu);
        m2.showInfoWindow();
        m2.setTag(new Integer(R.drawable.logo_chu));

        LatLng whiteHouse = new LatLng(38.8988968,-77.0533047);
        MarkerOptions markerWhiteHouse = new MarkerOptions();
        markerWhiteHouse.position(whiteHouse);
        markerWhiteHouse.title("The White House");
        markerWhiteHouse.snippet("The White House");

        Marker mWhiteHouse = googleMap.addMarker(markerWhiteHouse);
        mWhiteHouse.showInfoWindow();
        mWhiteHouse.setTag(new Integer(R.drawable.trump));

        MarkerOptions myLoc = createMyLocation(loc);
        Marker mMoi = googleMap.addMarker(myLoc);
        mMoi.showInfoWindow();
        mMoi.setTag(new Integer(R.drawable.david));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(imieLatLng, 14.0f));
        googleMap.setInfoWindowAdapter(new InfoWindow(this));

    }

    public MarkerOptions createMyLocation(Location location) {
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myLatLng);
        markerOptions.title("MOI");
        markerOptions.snippet("Altitude : " + location.getAltitude() + "m");
        return markerOptions;
    }
}
