package com.ar.developments.taptoplant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 6/15/2017.
 */
public class Map_Activity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    Double lat;
    Double lon;
    String loc;
    String from;
    public Boolean loc_con=false;
    public Double t_lat=0.0;
    public Double t_lon=0.0;
    private   Typeface font1;
    private Typeface font2;
    private GoogleMap mMap;
            ArrayList<LatLng> MarkerPoints;
            GoogleApiClient mGoogleApiClient;
            Location mLastLocation;
            Marker mCurrLocationMarker;
            LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.map_layout);
            font1= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
            font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
            lat=getIntent().getDoubleExtra("lat", 0);
            lon=getIntent().getDoubleExtra("lon", 0);
            loc=getIntent().getStringExtra("loc");
            from=getIntent().getStringExtra("from");
            initial_fun();
            try {
                location_check();
            } catch (IOException e) {
                e.printStackTrace();
            }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
            }
            // Initializing
            MarkerPoints = new ArrayList<>();

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            }

    private void initial_fun() {
        TextView txt=(TextView)findViewById(R.id.loc_txt);
        txt.setTypeface(font1);
        String cnt1 = "   Location   ";
        String cnt2 = "<font color='#4C4130'>Map</font>";
        txt.setText(Html.fromHtml("&nbsp;" + cnt1 + "&nbsp;" + cnt2));

        Button button=(Button)findViewById(R.id.dir_btn);
        TextView km=(TextView)findViewById(R.id.dist_txt);
        TextView hr=(TextView)findViewById(R.id.dur_txt);
        TextView txt1=(TextView)findViewById(R.id.dist_txt1);
        TextView txt2=(TextView)findViewById(R.id.dur_txt1);
        TextView turn_txt=(TextView)findViewById(R.id.turn_txt);
        Button turn_btn=(Button)findViewById(R.id.turn_btn);
        turn_txt.setTypeface(font2);
        turn_btn.setTypeface(font2);
        button.setTypeface(font2);
        txt1.setTypeface(font2);
        txt2.setTypeface(font2);
        km.setTypeface(font2);
        hr.setTypeface(font2);

        Log.d("from", "from   " + from);
        if(from.equals("user")){
            button.setVisibility(View.GONE);
        }
    }

    public void location_check() throws IOException {
        GPSTracker gps = new GPSTracker(this);
        TextView turn_txt=(TextView)findViewById(R.id.turn_txt);
        Button turn_btn=(Button)findViewById(R.id.turn_btn);

        if (gps.canGetLocation()) {
            loc_con = true;
            t_lat = gps.getLatitude();
            t_lon = gps.getLongitude();
            turn_txt.setVisibility(View.GONE);
            turn_btn.setVisibility(View.GONE);
        }
        else {
            loc_con=false;
            turn_txt.setVisibility(View.VISIBLE);
            turn_btn.setVisibility(View.VISIBLE);
        }
    }

    public void refresh(View v) throws IOException {
        location_check();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Initializing
        MarkerPoints = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(loc_con) {
            mMap = googleMap;
            Log.d("lat long ....  ", lat + "  ....   " + lon);

            //Initialize Google Play Services
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
            LatLng ori = new LatLng(lat, lon);
            MarkerPoints.add(ori);
            MarkerOptions options = new MarkerOptions();
            options.position(ori);
            options.title("Work place");
            options.snippet(loc);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_icn));
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ori));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            Button button = (Button) findViewById(R.id.dir_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LinearLayout dir_layout = (LinearLayout) findViewById(R.id.dir_layout);
                    LinearLayout dis_layout = (LinearLayout) findViewById(R.id.dis_layout);
                    dir_layout.setVisibility(View.GONE);
                    dis_layout.setVisibility(View.VISIBLE);

                    LatLng des = new LatLng(t_lat, t_lon);
                    MarkerPoints.add(des);
                    MarkerOptions options2 = new MarkerOptions();
                    options2.position(des);
                    options2.title("My place");
                    options2.snippet(loc);
                    options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                    mMap.addMarker(options2);


                    Location l1 = new Location("One");
                    l1.setLatitude(lat);
                    l1.setLongitude(lon);

                    Location l2 = new Location("Two");
                    l2.setLatitude(t_lat);
                    l2.setLongitude(t_lon);

                    float distance = l1.distanceTo(l2);
                    String dist = distance + " M";

                    if (distance > 1000.0f) {
                        distance = distance / 1000.0f;
                        dist = distance + " KM";
                    }
                    Log.d("Distance", "" + dist);

                    if (MarkerPoints.size() >= 2) {
                        LatLng origin = MarkerPoints.get(0);
                        LatLng dest = MarkerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getUrl(origin, dest);
                        Log.d("onMapClick", url.toString());
                        FetchUrl FetchUrl = new FetchUrl();

                        // Start downloading json data from Google Directions API
                        FetchUrl.execute(url);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    }
                }
            });
        }

    }


    private String getUrl(LatLng origin, LatLng dest) {
            Log.d("get url","URL");
            // Origin of route
            String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

            // Destination of route
            String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = str_origin + "&" + str_dest + "&" + sensor;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


            return url;
            }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
            Log.d("download url","DD URL");
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
            sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

            } catch (Exception e) {
            Log.d("Exception", e.toString());
            } finally {
            iStream.close();
            urlConnection.disconnect();
            }
            return data;
            }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            Log.d("fetch url", "FF URL");

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            String distance = "";
            String duration = "";
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if(j==0){    // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.DKGRAY);

                TextView km=(TextView)findViewById(R.id.dist_txt);
                TextView hr=(TextView)findViewById(R.id.dur_txt);
                km.setText(distance);
                hr.setText(duration);

                Log.d("...................   ","Distance:"+distance + ", Duration:"+duration);
                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

        @Override
        public void onConnected(Bundle bundle) {

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
            }

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onLocationChanged(Location location) {

//            mLastLocation = location;
//            if (mCurrLocationMarker != null) {
//                mCurrLocationMarker.remove();
//            }
//
//            //Place current location marker
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title("Current Position");
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//            mCurrLocationMarker = mMap.addMarker(markerOptions);
//
//            //move map camera
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
//            //stop location updates
//            if (mGoogleApiClient != null) {
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
//            }

        }

       
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
        public boolean checkLocationPermission(){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Asking user if explanation is needed
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    //Prompt the user once explanation has been shown
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);


                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted. Do the
                        // contacts-related task you need to do.
                        if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient();
                            }
                            mMap.setMyLocationEnabled(true);
                        }

                    } else {

                        // Permission denied, Disable the functionality that depends on this permission.
                        Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                // other 'case' lines to check for other permissions this app might request.
                // You can add here other case statements according to your requirement.
            }
        }
}
