package com.example.richmond.uspotmobileapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.richmond.uspotmobileapplication.R;

public class SingleSpotView extends AppCompatActivity {

    TextView spotNameHolder;
    TextView spotCommentHolder;
    TextView spotRatingHolder;
    TextView spotTagsHolder;
    TextView spotStatusHolder;
    TextView spotLongitudeHolder;
    TextView spotLatitudeHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_spot_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

}
