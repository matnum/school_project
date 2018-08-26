package com.example.a305.splashscreen;

import android.Manifest;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class homeActivity extends AppCompatActivity implements OnMapReadyCallback {
    ProgressBar loadingAnimation;
    Marker currentLocationMarker;
    LocationManager mlocationManager;

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;



        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);


        }
        init();
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Roadconditions rc = wirmaData.get(polyline);
                ArrayList<String> colors = new ArrayList();
                colors.add("has no data");
                colors.add("is Dry");
                colors.add("is Moist");
                colors.add("is Wet");
                colors.add("is Slushy");
                colors.add("is Icy");
                colors.add("is Snowy");

                String colorName = colors.get(rc.getProperties().getSensorvalueRange());

                Toast.makeText(getApplicationContext(), "Road " + colorName, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onPolylineClick: dataa");

            }
        });

    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private EditText mSearchText;
    private ImageView mGPS;
    private ImageView mBackbutton;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        mSearchText = (EditText) findViewById(R.id.input_search);
        mGPS = (ImageView) findViewById(R.id.ic_gps);
        loadingAnimation = findViewById(R.id.loadinganimationProgrssBar);
        mBackbutton = (ImageView) findViewById(R.id.ic_settingsi);
        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);





        getLocationPermission();
    }

    private void init() {
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyevent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyevent.getAction() == KeyEvent.ACTION_DOWN
                        || keyevent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });

        mGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        mBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, settingsActivity.class);
                startActivity(intent);
            }
        });
        hideSoftKeyboard();
    }

    private void geoLocate() {
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(homeActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.d(TAG, "geoLocate: IOException " + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final com.google.android.gms.tasks.Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found location!");
                            Location currentLocation = (Location) task.getResult();

                            if (currentLocation != null) {
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                            }
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(homeActivity.this, "Current location is null", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            currentLocationMarker=mMap.addMarker(options);
        }

        hideSoftKeyboard();

    }

    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(homeActivity.this);
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //kaikki jees, kartta esille
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    HashMap<Polyline, Roadconditions> wirmaData = new HashMap();


    public void getRoadconditions() {



        String JSON_URL = "http://wirma.plab.fi/cached_geojson/-1/condition/";

        SharedPreferences sharedpref2 = PreferenceManager.getDefaultSharedPreferences(this);

        int usehours = sharedpref2.getInt("usehdata", 0);

        usehours = usehours * (-1);



            JSON_URL = "http://wirma.plab.fi/cached_geojson/"+usehours+"/condition/";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // NOTE! THIS LINE COMES OUTSIDE THE ONCREATE-METHOD (inside the MainActivity-class)
                        ArrayList<Roadconditions> conditions = new ArrayList();

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();


                        try {
                            // Apigility returns HAL/JSON instead of typical JSON-data. HAL/JSON adds data relation information and metadata to a given JSON data.
                            // these lines dig out the actual JSON data content from the HAL/JSON response
                            JSONObject jObject = new JSONObject(response);


                            // in this case feedback is the name of our service
                            JSONArray jArray = jObject.getJSONArray("features");

                            // place the found JSON data into our ArrayList in object form
                            // GSON here expects our Android project has a class called "Feedback" in it. You can create this class by using the service jsonschema2pojo
                            conditions = gson.fromJson(jArray.toString(), new TypeToken<ArrayList<Roadconditions>>() {
                            }.getType());

                        } catch (JSONException e) {
                            // error in handling the JSON
                        }

                        Log.d(TAG, "onResponse: SAMPSA");

                        for (Roadconditions rc : conditions) {

                            LatLng[] points = new LatLng[rc.getGeometry().getCoordinates().size()];

                            ArrayList<LatLng> pointsList = new ArrayList();

                            for (List<Double> coordinates : rc.getGeometry().getCoordinates()) {
                                LatLng point = new LatLng(coordinates.get(1), coordinates.get(0));
                                pointsList.add(point);
                            }

                            LatLng[] routeData = pointsList.toArray(points);

                            int[] conditionColors = {
                                    Color.TRANSPARENT,
                                    Color.GREEN,
                                    Color.CYAN,
                                    Color.BLUE,
                                    Color.LTGRAY,
                                    Color.RED,
                                    Color.WHITE};

                            int selectedColor = rc.getProperties().getSensorvalueRange();
                            Polyline Line = mMap.addPolyline(new PolylineOptions()
                                    .add(routeData)
                                    .width(10)
                                    .color(conditionColors[selectedColor]));

                            Line.setClickable(true);

                            wirmaData.put(Line, rc);
                        }
                        loadingAnimation.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // probably a connection error
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                // we have to specify a proper header, otherwise Apigility will block our queries!
                // define we are after JSON data!
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        loadingAnimation.setVisibility(View.VISIBLE);
        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    public void getAuroradata() {
        String JSON_URL = "https://api.auroras.live/v1/?type=all&lat=66.497022&long=25.724999&forecast=false&threeday=false&weather=false";

        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(this);

        final boolean use4h = sharedpref.getBoolean("use4haurora", false);

        if(currentLocationMarker != null) {
            double latitude = currentLocationMarker.getPosition().latitude;

            double longitude = currentLocationMarker.getPosition().longitude;
            JSON_URL = "https://api.auroras.live/v1/?type=all&lat="+latitude+"&long="+longitude+"&forecast=false&threeday=false&weather=false";
        }
        else
        {
            try
            {
                if (mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null)
                {
                    double latitude = mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                    double longitude = mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                    JSON_URL = "https://api.auroras.live/v1/?type=all&lat="+latitude+"&long="+longitude+"&forecast=false&threeday=false&weather=false";
                }


                 }
                 catch (SecurityException se)
                 {

                 }
            }



        //;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // NOTE! THIS LINE COMES OUTSIDE THE ONCREATE-METHOD (inside the MainActivity-class)
                        ArrayList<AuroraData> probability = new ArrayList();

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        AuroraData ad = gson.fromJson(response, AuroraData.class);

                        double lat = Double.parseDouble(ad.getProbability().getLat());
                        double lng = Double.parseDouble(ad.getProbability().getLong());


                        LatLng auroraPoint = new LatLng(lat, lng);

                        CircleOptions circleOptions = new CircleOptions();

                        circleOptions.center(auroraPoint);

                        circleOptions.radius(20000);
                        circleOptions.strokeColor(getColor(R.color.colorTransparentOutline));
                        circleOptions.fillColor(getColor(R.color.colorTransparent));

                        circleOptions.strokeWidth(10);
                        MarkerOptions options = new MarkerOptions().position(auroraPoint);
                        Marker newMarker = mMap.addMarker(options);

                        if (use4h == true){

                            newMarker.setTitle(ad.getAce().getKp4hour() + "% Probability within the next 4 hours");
                        }
                        else {
                            newMarker.setTitle(ad.getAce().getKp1hour() + "% Probability within the next 1 hour");
                        }


                        mMap.addCircle(circleOptions);
                        loadingAnimation.setVisibility(View.GONE);
                        Log.d(TAG, "onResponse: SAMPSA");


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // probably a connection error
                Log.d(TAG, "onErrorResponse: ERRORRI");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                // we have to specify a proper header, otherwise Apigility will block our queries!
                // define we are after JSON data!
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                // Here goes the new timeout
                return 5*60*1000;
            }

            @Override
            public int getCurrentRetryCount() {
                // The max number of attempts
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                // Here you could check if the retry count has gotten
                // To the max number, and if so, send a VolleyError msg
                // or something
                //Log.d("err", error.toString());
            }
        });

        loadingAnimation.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showAuroraData(View v)
    {

        mMap.clear();
        if (currentLocationMarker != null) {
            currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocationMarker.getPosition()));
        }
        getAuroradata();
        Log.d(TAG, "showAuroraData: asd");
    }
    public void showRoadData (View v)
    {
        mMap.clear();
        getRoadconditions();
    }
    public void onClick (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}