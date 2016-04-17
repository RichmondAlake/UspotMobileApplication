package com.example.richmond.uspotmobileapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.richmond.uspotmobileapplication.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    /**initialising components **/
    private EditText emailOrUserNameEntry; //entry for username or password
    private EditText passwordEntry; //entry for password
    private Button loginButton; // login button
    private Button registerButton; //register button
    private RequestQueue requestQueue; //request queue variable for the volley library
    private static final String URL = "http://10.0.3.2/uspotdatabase/User_control.php";  //192.168.56.1 (vb) (use when testing on emulator)10.0.3.2
    private StringRequest request;


    /** Facebook API Login**/
    //constructing callback manager
    private CallbackManager mCallbackManager;
    /** Handling all possible outcome that occur during login in with facebook **/
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            /** Access token contains current token granted to connect to facebook server **/
            AccessToken accessToken = loginResult.getAccessToken();
            /** Accessing profile of logged in individual **/
            Profile profile = Profile.getCurrentProfile();

            if(profile != null){
                //if profile is not null go to current activity
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                /** To get profile name use (profile.getName() method), assign this to a variable and pass it to the next Activity**/
                /** Look at Facebook reference and get methods to use with profile, ie: profile picture etc **/
            }
        }
        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Adding facebook sdk initialising class **/
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.loginpage);


        /**Initialising facebook login button **/
        LoginButton facebookLoginButton = (LoginButton) findViewById(R.id.login_button);
        /** Requesting permission for user friends **/
        facebookLoginButton.setReadPermissions("user_friends");
        /** Initialising callback manager **/
        mCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(mCallbackManager, mCallback);


        /** assigning variable to app components**/
        emailOrUserNameEntry = (EditText) findViewById(R.id.emailOrUsername);
        passwordEntry = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registerpage.class);
                startActivity(i);
            }
        });

        /** initialise request queue with volley request queue method **/
        requestQueue = Volley.newRequestQueue(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                           // Toast.makeText(getApplicationContext(), "Button performing a function", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                               // Toast.makeText(getApplicationContext(), "SUCCESS" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainPage.class));
                            } else{
                                Toast.makeText(getApplicationContext(), "error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(LoginPage.this, response.substring(0,500 )+"", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Can't execute Volley", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("email", emailOrUserNameEntry.getText().toString());
                        hashMap.put("password", passwordEntry.getText().toString());
                        hashMap.put("username", emailOrUserNameEntry.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(request);

                /** retrying request in case of time out**/
                request.setRetryPolicy(new DefaultRetryPolicy(
                        5000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
    @Override
    protected void onRestart() {
        super.onRestart();

    }
    **/
}
