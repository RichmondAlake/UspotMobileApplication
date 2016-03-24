package com.example.richmond.uspotmobileapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.richmond.uspotmobileapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpotProfilePage extends AppCompatActivity {


    private EditText spotName;
    private EditText spotType;
    private EditText spotTags;
    private EditText spotComments;
    private EditText spotRatings;

    private Button camera1;
    private Button camera2;
    private String encoded_string;
    private String image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;

    private Button submitSpot;

    private CheckBox privateStatus; //checkboxes to make the image taken a public one
    private CheckBox publicStatus; //checkboxes to make the image taken a private one
    private int spotStatus; // used to set a status, either private (0) or public (1)

    private StringRequest request;
    private RequestQueue requestQueue;
    private String URL = "http://10.0.3.2/uspotdatabase/spotprofile_control.php";

    MainPage mainpageObject = new MainPage();
    String latitude = mainpageObject.getLatitude();
    String longitude = mainpageObject.getLongitude();



    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_profile_page);

        /** Toolbar settings**/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spotName = (EditText) findViewById(R.id.spotName);
        spotType = (EditText) findViewById(R.id.spotType);
        spotTags = (EditText) findViewById(R.id.spotTags);
        spotRatings = (EditText) findViewById(R.id.spotRating);
        spotComments = (EditText) findViewById(R.id.spotComments);

        /** Calling camera control method for camera buttons **/
        camera1 = (Button) findViewById(R.id.camera1);
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, 10);
            }
        });

        /** Checkboxes (Private and public) **/
        publicStatus = (CheckBox) findViewById(R.id.checkBoxPublic);
        publicStatus.setOnCheckedChangeListener(listener);
        privateStatus = (CheckBox) findViewById(R.id.checkBoxPrivate);
        privateStatus.setOnCheckedChangeListener(listener);

        requestQueue = Volley.newRequestQueue(this);
        /**Submit spot button action **/
        submitSpot = (Button) findViewById(R.id.submitSpotButton);
        submitSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Method entered", Toast.LENGTH_LONG).show();

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                //what to do when a new spot profile as been successfully created
                                Toast.makeText(getApplicationContext(), "Spot as been created!" + jsonObject.getString("success"), Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Spot not created" + jsonObject.getString("error"), Toast.LENGTH_LONG).show();
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
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("spot_name", spotName.getText().toString());
                        hashMap.put("spot_rating", spotRatings.getText().toString());
                        hashMap.put("spot_type", spotType.getText().toString());
                        hashMap.put("spot_tags", spotTags.getText().toString());
                        hashMap.put("spot_comments", spotComments.getText().toString());
                        hashMap.put("spot_status", String.valueOf(spotStatus));
                        //hashMap.put("spot_longitude", "two"); //might not work as expected
                        //hashMap.put("spot_latitude", "22"); // might not work as expected
                        hashMap.put("encoded_string", encoded_string);
                        hashMap.put("image_name", image_name);


                        return hashMap;
                    }
                };
                requestQueue.add(request);

            }

        });


    }

    /** Setting checked states **/ /** Checked boxes have to mandatory, or set to public by default **/
    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        switch (buttonView.getId()){
                            case R.id.checkBoxPublic:
                                publicStatus.setChecked(true);
                                privateStatus.setChecked(false);
                                spotStatus = 1;
                                break;
                            case R.id.checkBoxPrivate:
                                publicStatus.setChecked(false);
                                privateStatus.setChecked(true);
                                spotStatus = 0;
                            break;
                        }
                    }
                }
            };

    /**  Getting File URI**/
    private void getFileUri() {
        image_name = "testing123.jpg"; //change later to date
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + image_name);
        file_uri = Uri.fromFile(file);

    }

    /** **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK){
            new Encode_image().execute();
        }
    }

    private class Encode_image extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array,0);
            return null;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);


        }
        return super.onOptionsItemSelected(item);
    }
}
