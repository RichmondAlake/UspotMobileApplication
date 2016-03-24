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
import com.example.richmond.uspotmobileapplication.activities.LoginPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registerpage extends AppCompatActivity {

    private String URL = "http://10.0.3.2/uspotdatabase/Registeration_control.php";
    private RequestQueue requestQueue;
    private StringRequest request;
    private EditText username;
    private EditText password;
    private EditText email;
    private EditText sport;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        sport = (EditText) findViewById(R.id.selectedSport);
        registerButton = (Button) findViewById(R.id.signupButton);

        requestQueue = Volley.newRequestQueue(this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){

                                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                                Toast.makeText(getApplicationContext(), "Account has been created log in here!" + jsonObject.getString("success"), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Account not created " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                        hashMap.put("email", email.getText().toString());
                        hashMap.put("password", password.getText().toString());
                        hashMap.put("username", username.getText().toString());
                        hashMap.put("sport", sport.getText().toString());

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
        getMenuInflater().inflate(R.menu.menu_registerpage, menu);
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
}
