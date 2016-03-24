package com.example.richmond.uspotmobileapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.richmond.uspotmobileapplication.fragments.MainNavigationFragment;
import com.example.richmond.uspotmobileapplication.R;
import com.example.richmond.uspotmobileapplication.fragments.ViewPublicSpotsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;

public class MainPage extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener, MainNavigationFragment.OnFragmentInteractionListener, ViewPublicSpotsFragment.OnFragmentInteractionListener {



    /** Camera Variables**/
    private String encoded_string; //hold base64 format
    private String imageName; //hold the image name
    private Bitmap bitmap;
    private Uri file_uri; //used to load content of image into bitmap
    private File file;

    /** Goole API CLIENT variable**/
    private GoogleApiClient mGoogleApiClient;
    private String latitude;
    private String longitude;

    /** Navigation Drawer variables**/
    private android.support.v7.widget.Toolbar toolbar;
    private NavigationView drawer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedID;
    private static final String SELECTED_ITEM_ID = "selectedItem_ID";
    private static final String FIRST_TIME = "first_time";
    private boolean firstTimeSeeingTheDrawer = false;

    /** Tablayout variables**/
    private TabLayout appTabLayout;
    private ViewPager appPager;

    private ViewPublicSpotsFragment viewPublicSpotsFragment;
    private MainNavigationFragment mainNavigationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        /** Sliding Tab Layout, inner class adapter below, showing fragments on slide**/
        appPager = (ViewPager) findViewById(R.id.pager);
        appPager.setAdapter( new CustomAdapter(getSupportFragmentManager(), getApplicationContext()));

        appTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        appTabLayout.setupWithViewPager(appPager);

        appTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                appPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                appPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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



        /** Creating an instance of the GoogleAPI Client to access the location service API **/
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }



    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        /**
         if (id == R.id.navigate) {
         startActivity(new Intent(this, SpotProfilePage.class));
         }
         **/

        return super.onOptionsItemSelected(item);
    }


    /**
     * Google MAP API callback methods, onConnected, onConnectionSuspended, onConnectionFailed
     **/
    @Override
    public void onConnected(Bundle bundle) {
        //getting the longitude and latitude of the device
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
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            longitude = String.valueOf(mLastLocation.getLongitude());
            latitude = String.valueOf(mLastLocation.getLatitude());
            System.out.println(longitude + latitude);
            Toast.makeText(MainPage.this, "Longitude is : " + longitude + " and the latitude is : " + latitude, Toast.LENGTH_LONG).show();

        }
    }

    //method to get make location accessible to other classes
    public String getLatitude() {
        return latitude;
    }

    //method to get make location accessible to other classes
    public String getLongitude() {
        return longitude;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    /**
     * Method to allow activity navigation within the drawer
     **/
    private void navigate(int selectedID) {

        Intent intent = null;

        if (selectedID == R.id.nav_camera) {
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, SpotProfilePage.class);
            startActivity(intent);

        }

        if (selectedID == R.id.nav_gallery) {

        }

        if (selectedID == R.id.nav_private) {

        }
        if (selectedID == R.id.nav_public) {
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, ViewAllSpots.class);
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
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SELECTED_ITEM_ID, selectedID);
    }

    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments[] = {"MainNavigationFragment", "ViewPublicSpotsFragment"};
        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0 :
                    return new MainNavigationFragment();
                case 1 :
                    return new ViewPublicSpotsFragment();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }

    /**
     @Override public void onBackPressed() {
     if (drawerLayout.isDrawerOpen(GravityCompat.START)){
     drawerLayout.closeDrawer(GravityCompat.START);
     }else{
     super.onBackPressed();
     }

     }
     **/
}
