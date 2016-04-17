package com.example.richmond.uspotmobileapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.richmond.uspotmobileapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SingleSpotView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    TextView spotNameHolder;
    TextView spotCommentHolder;
    TextView spotRatingHolder;
    TextView spotTagsHolder;
    TextView spotStatusHolder;
    TextView spotLongitudeHolder;
    TextView spotLatitudeHolder;

    /** Navigation Drawer variables**/
    private android.support.v7.widget.Toolbar toolbar;
    private NavigationView drawer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedID;
    private static final String SELECTED_ITEM_ID = "selectedItem_ID";
    private static final String FIRST_TIME = "first_time";
    private boolean firstTimeSeeingTheDrawer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_spot_view);

        /** Toolbar initialisation **/
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /** Drawer layout fragment **/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer = (NavigationView) findViewById(R.id.navigationDrawer);
        drawer.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        // selectedID = savedInstanceState == null ? R.id.nav_camera : savedInstanceState.getInt(SELECTED_ITEM_ID); //line causes the spotprofile page to start first as nav_camera is true
        navigate(selectedID);

        /** Implementing Google Maps
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         **/


        //xml components to variables
        spotNameHolder = (TextView) findViewById(R.id.spotNameHolder);
        spotCommentHolder = (TextView) findViewById(R.id.spotCommentHolder);
        spotRatingHolder = (TextView) findViewById(R.id.spotRatingHolder);
        spotTagsHolder = (TextView) findViewById(R.id.spotTagHolder);
        spotStatusHolder = (TextView) findViewById(R.id.spotStatusHolder);
        spotLongitudeHolder = (TextView) findViewById(R.id.spotLongHolder);
        spotLatitudeHolder = (TextView) findViewById(R.id.spotLatHolder);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //getting data from variables
        //String spotNameFromAdapter = getIntent().getExtras().getString("spotName");
        //spotNameHolder.setText(spotNameFromAdapter);


    }

    /**
     * Method to allow activity navigation within the drawer
     **/
    private void navigate(int selectedID) {

        Intent intent = null;

        if (selectedID == R.id.nav_camera) {
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, CreateSpotPage.class);
            startActivity(intent);

        }

        if (selectedID == R.id.nav_gallery) {

        }

        if (selectedID == R.id.nav_private) {

        }
        if (selectedID == R.id.nav_public) {
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, ViewAllPublicSpot.class);
            startActivity(intent);
        }
        if (selectedID == R.id.nav_send) {

        }
        if (selectedID == R.id.nav_share) {

        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        selectedID = item.getItemId();

        navigate(selectedID);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }

    public class MapFragment extends FragmentActivity{

    }
}
