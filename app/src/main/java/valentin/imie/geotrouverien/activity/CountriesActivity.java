package valentin.imie.geotrouverien.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import valentin.imie.geotrouverien.listener.MyWindowListener;
import valentin.imie.geotrouverien.R;
import valentin.imie.geotrouverien.adapter.CountryAdapter;
import valentin.imie.geotrouverien.async.GetCountries;
import valentin.imie.geotrouverien.async.GetLocalisation;
import valentin.imie.geotrouverien.async.GetPopulation;
import valentin.imie.geotrouverien.map.InfoPays;
import valentin.imie.geotrouverien.model.Pays;
import valentin.imie.geotrouverien.tools.MyTools;

public class CountriesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CountryAdapter countryAdapter;
    private Pays[] pays;
    private GoogleMap googleMap;
    private FusedLocationProviderClient bigBrother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MyTools.REQUEST_CODE);
        } else {
            initMap();
        }

        GetCountries gc = new GetCountries(this);
        gc.execute(MyTools.ADRESSE_API + "/countries");

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
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(CountriesActivity.this);
                }
            }
        });

    }

    public Pays[] getPays() {
        return pays;
    }

    public void setPays(Pays[] pays) {
        this.pays = pays;
    }

    public void onCountriesLoaded() {
        countryAdapter = new CountryAdapter(this, R.layout.pays_item, pays);
        ListView listPays = (ListView) findViewById(R.id.listPays);
        listPays.setAdapter(countryAdapter);

        listPays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GetPopulation gp = new GetPopulation(CountriesActivity.this, pays[i]);
                gp.execute(MyTools.ADRESSE_API + "/population/" + pays[i].getNom() + "/today-and-tomorrow/");

                GetLocalisation gl = new GetLocalisation(CountriesActivity.this, pays[i]);
                String apiKey = getResources().getString(R.string.google_maps_key);
                gl.execute(MyTools.LOCALISATION_API + "?key=" + apiKey + "&address=" + pays[i].getNom());
            }
        });
    }

    public void onLocalisationLoaded(Pays p) {
        if (googleMap == null) {
            Toast.makeText(this, "Erreur : pas de google map", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.e("TOTO", p.getPop()[0].getDate());
        Log.e("TOTO", String.valueOf(p.getPop()[0].getNumber()));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(p.getPos());

        Marker m = googleMap.addMarker(markerOptions);
        m.setTag(p);
        m.showInfoWindow();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p.getPos(), 2.0f));
        googleMap.setInfoWindowAdapter(new InfoPays(this));
        googleMap.setOnInfoWindowClickListener(new MyWindowListener(this));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

}
