package com.example.zooparks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.zooparks.adapter.ParkRecyclerViewAdapter;
import com.example.zooparks.data.AsyncResponse;
import com.example.zooparks.data.Repository;
import com.example.zooparks.model.Park;
import com.example.zooparks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.zooparks.databinding.ActivityMapsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkViewModel = new ViewModelProvider(this)
                .get(ParkViewModel.class);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedfragment = null;
            int id= item.getItemId();
            if(id==R.id.maps_nav_button){
            //   show mapview
                mMap.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map,mapFragment).commit();

                mapFragment.getMapAsync(this);
                return true;

            }
           else if(id==R.id.parks_nav_button){
        selectedfragment=new ParksFragment();
            }
           getSupportFragmentManager().beginTransaction().replace(R.id.map,selectedfragment).commit();

           mapFragment.getMapAsync(this);
            return true;
        });
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
         mMap.setOnInfoWindowClickListener(this);
parkList= new ArrayList<>();

        Repository.getParks(parks -> {
      for(Park park:parks){
       parkList=parks;
          LatLng sydney =new LatLng(Double.parseDouble(park.getLatitude()),Double.parseDouble(park.getLongitude()));
          MarkerOptions markerOptions=new MarkerOptions().position(sydney).title(park.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).snippet(park.getStates());
          Marker marker=mMap.addMarker(markerOptions);
          marker.setTag(park);

          mMap.addMarker(new MarkerOptions().position(sydney).title(park.getFullName()));
          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,5));
      }
      parkViewModel.setSelectedParks(parkList);
        });
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
parkViewModel.setSelectedPark((Park) marker.getTag());
getSupportFragmentManager().beginTransaction().replace(R.id.map,DetailsFragment.newInstance()).commit();
    }
}