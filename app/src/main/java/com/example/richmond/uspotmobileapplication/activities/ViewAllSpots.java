package com.example.richmond.uspotmobileapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.richmond.uspotmobileapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewAllSpots extends AppCompatActivity {

    private Button showAllSpotBtn;
    private TextView displayAllSpot;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private Request request;
    private String URL = "http://10.0.3.2/uspotdatabase/getSpotInfo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_spots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**initialsiseing component **/
        showAllSpotBtn = (Button) findViewById(R.id.viewAllSpotsBtn);
        displayAllSpot = (TextView) findViewById(R.id.showAllSpots);
        requestQueue = Volley.newRequestQueue(this);

        showAllSpotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("spotinfo");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject spot = jsonArray.getJSONObject(i);

                                String spotName = spot.getString("spotname");

                                displayAllSpot.append(spotName);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                requestQueue.add(request);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
