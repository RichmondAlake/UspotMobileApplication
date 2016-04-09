package com.example.richmond.uspotmobileapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.richmond.uspotmobileapplication.R;
import com.example.richmond.uspotmobileapplication.Spot;
import com.example.richmond.uspotmobileapplication.adapters.AdapterSpotView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPublicSpotsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPublicSpotsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPublicSpotsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button searchPublicSpotBtn;
    private EditText searchPublicSpotEntry;

    //inistialising recycler view
    private RecyclerView publicRecyclerView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RequestQueue requestQueue; //request queue variable for the volley library
    private static final String URL = "http://10.0.3.2/uspotdatabase/getSpotInfo.php";  //192.168.56.1 (vb) (use when testing on emulator)10.0.3.2
    private JsonObjectRequest request;

    private ArrayList<Spot> spotList = new ArrayList<>();
    private AdapterSpotView adapterSpotView;


    public ViewPublicSpotsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPublicSpotsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPublicSpotsFragment newInstance(String param1, String param2) {
        ViewPublicSpotsFragment fragment = new ViewPublicSpotsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        /** sending volley json request to gain all spots **/
        requestQueue = Volley.newRequestQueue(getContext());
        request = new JsonObjectRequest(Request.Method.GET, URL, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                parseJSONResponse(response);
                adapterSpotView.setSpotList(spotList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Can't execute Volley", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // HashMap<String, String> hashMap = new HashMap<>();
                //hashMap.put("email", emailOrUserNameEntry.getText().toString());
                //hashMap.put("password", passwordEntry.getText().toString());
                //hashMap.put("username", emailOrUserNameEntry.getText().toString());

                return null;//hashMap
            }
        };
        requestQueue.add(request);

    }

    private void parseJSONResponse(JSONObject response) {
        if (response == null || response.length() == 0) {
            return;
        }

        try {

            //if (response.has("spotinfo")) {
            StringBuilder data = new StringBuilder();
            JSONArray spotInfoArray = response.getJSONArray("spotinfo");
            for (int i = 0; i < spotInfoArray.length(); i++) {
                JSONObject currentSpot = spotInfoArray.getJSONObject(i);
                String spotName = currentSpot.getString("spotname");
                String spotType = currentSpot.getString("spot_type");
                int spotRating = currentSpot.getInt("spotrating");
                data.append(spotName + spotType + spotRating + "\n");


                //}

                Spot spot = new Spot();
                spot.setSpotType(spotType);
                spot.setSpotName(spotName);
                spot.setSpotRating(spotRating);

                spotList.add(spot);
                //Toast.makeText(getContext(), "Showing Data" + spotList.toString(), Toast.LENGTH_LONG).show();
            }
            Toast.makeText(getContext(), "Showing Data" + spotList.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_view_public_spots, container, false);

        searchPublicSpotEntry = (EditText) relativeLayout.findViewById(R.id.searchPublicSpotEntry);
        searchPublicSpotBtn = (Button) relativeLayout.findViewById(R.id.searchPubicSpotBtn);
        searchPublicSpotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Action implemented here **/
                //preferably call class and method query the list
            }
        });


        /** Initialising recycler view*/
        publicRecyclerView = (RecyclerView) relativeLayout.findViewById(R.id.publicRecyclerView);


        //layout manager
        publicRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //layoutAdapter
        adapterSpotView = new AdapterSpotView(getActivity());
        publicRecyclerView.setAdapter(adapterSpotView);


        return relativeLayout;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
