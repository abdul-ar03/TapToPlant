package com.ar.developments.taptoplant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Result;
import android.widget.LinearLayout;

public class Home_page extends Activity {
    private static boolean network=false;
    private   Typeface font1;
    private Typeface font2;
    private int page_hint=0;
    private String code=null;
    private SQL_db db;
    private String mem_ID="TTP000";
    private Server_http server_db;
    public boolean tapped=false;
    public int tap_count=0;
    public String t_name=null;
    public String t_mail=null;
    public String t_desc=null;
    public Double t_lat=0.0;
    public Double t_lon=0.0;
    public String t_loc="";
    public String t_date="";
    public int t_status=0;
    public Long t_num=null;
    private String ip="";
    public Boolean all_ok=false;
    public AlertDialog alertDialog=null;
    public  SharedPreferences prefs;

    public TextView textview;
    public SwipeRefreshLayout swipeLayout;
    public int register_page=1111;
    public int ngo_master=2222;
    public int my_tasks=3333;
    public int completed_task=4444;
    public int ngo_page=5555;
    public int splash_screen=6666;
    public Boolean server_con=false;
    public Boolean loc_con=false;
    public Boolean wrng=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ip=getString(R.string.ip_address);
        font1= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        db=new SQL_db(this);
        server_db=new Server_http(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            Intent i=new Intent(Home_page.this,Splash_screen.class);
            startActivityForResult(i, splash_screen);
        }

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeLayout.setColorSchemeResources(R.color.brown);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mem_ID=db.get_U_code();
                new http_getUT1().execute();
//                try {
//                    location_check();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                isInternetConnected(Home_page.this);
            }
        });
        swipeLayout.setEnabled(true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                ImageView opt_img1=(ImageView)findViewById(R.id.opt_img1);
                ImageView opt_img2=(ImageView)findViewById(R.id.opt_img2);
                ImageView opt_img3=(ImageView)findViewById(R.id.opt_img3);
                Animation animation = AnimationUtils.loadAnimation(Home_page.this, R.anim.option_poping);
                opt_img1.setAnimation(animation);
                opt_img2.setAnimation(animation);
                opt_img3.setAnimation(animation);
            }

        }, 0, 2000);


        mem_ID=db.get_U_code();
        new http_getUT1().execute();
        login_check();
        font_fun();

        try {
            location_check();
        } catch (IOException e) {
            e.printStackTrace();
        }

        isInternetConnected(this);
    }


    public static boolean isInternetConnected (Context ctx) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them is
        // available or connected then it will return true, otherwise false;
        if (wifi != null) {
            if (wifi.isConnected()) {
                Log.d("connect","wifi");
                network=true;
                return true;
            }
        }
        if (mobile != null) {
            if (mobile.isConnected()) {
                Log.d("connect","wifi");
                network=true;
                return true;
            }
        }

        network=false;
        return false;
    }
    public void tap_fun(View view) throws IOException {
        if(!loc_con){
            location_check();
        }

        if(!server_con)
        {
            Toast.makeText(this,"Server not connected",Toast.LENGTH_SHORT).show();

        }
        else if(!tapped && loc_con){
            tapped=true;
            tap_count=1;
            swipeLayout.setEnabled(false);
            LinearLayout hom_div1=(LinearLayout)findViewById(R.id.hom_div1);
            LinearLayout hom_div2=(LinearLayout)findViewById(R.id.hom_div2);
            LinearLayout top_frame=(LinearLayout)findViewById(R.id.top_frame1);
            LinearLayout people_options=(LinearLayout)findViewById(R.id.ppl_options_layout);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            Animation topUp = AnimationUtils.loadAnimation(this, R.anim.top_up);
            Button tap_again=(Button)findViewById(R.id.gps_button);
            hom_div1.setAnimation(topUp);
            hom_div2.setAnimation(bottomUp);
            hom_div2.setVisibility(View.VISIBLE);
            tap_again.setVisibility(View.VISIBLE);
            top_frame.setVisibility(View.GONE);
            people_options.setVisibility(View.GONE);
            location_check();
            ScrollView sv = (ScrollView)findViewById(R.id.scrollView2);
//            sv.scrollTo(0, sv.getBottom());
            sv.fullScroll(sv.FOCUS_UP);
        }
        else if(tap_count==1){
            Boolean result=check_userFields();
            if(result){
                all_ok=true;
                alert_gps();

            }
        }

    }


    public Boolean check_userFields() throws IOException {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        EditText name_edit1=(EditText)findViewById(R.id.name_edit1);
        EditText mob_edit1=(EditText)findViewById(R.id.mob_edit1);
        EditText mail_edit1=(EditText)findViewById(R.id.mail_edit1);
        EditText desc_edit1=(EditText)findViewById(R.id.desc_edit1);


        TextView txt_req11=(TextView)findViewById(R.id.txt_req11);
        TextView txt_req12=(TextView)findViewById(R.id.txt_req12);
        TextView txt_req13=(TextView)findViewById(R.id.txt_req13);
        txt_req11.setText("*");
        txt_req13.setText("*");
        txt_req12.setVisibility(View.INVISIBLE);


        name_edit1.setBackgroundResource(R.drawable.border_edittxt);
        mob_edit1.setBackgroundResource(R.drawable.border_edittxt);
        mail_edit1.setBackgroundResource(R.drawable.border_edittxt);


        t_name=name_edit1.getText().toString();
        String mob_num_str=mob_edit1.getText().toString();
        String mail=mail_edit1.getText().toString();
        t_desc=desc_edit1.getText().toString();

        if(t_name==null || t_name.length()==0){
            name_edit1.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            txt_req11.setText("* required");
        }



        if(!mail.matches(emailPattern) && mail.length()!=0){
            mail_edit1.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            txt_req12.setVisibility(View.VISIBLE);
            txt_req12.setText("* enter valid mail id");
            t_mail="";
        }
        else{
            t_mail=mail;
        }

        if(mob_num_str==null || mob_num_str.length()==0){
            mob_edit1.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            txt_req13.setText("* required");
            t_num=null;
        }
        else if(mob_num_str.length()>0 && mob_num_str.length()<10){
            mob_edit1.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            txt_req13.setText("* enter valid number");
            t_num=null;
        }
        else{
            t_num=Long.parseLong(mob_num_str);
        }
        if(t_loc.toString().equals(null)){
            location_check();
        }
        if(t_date.isEmpty()){
            DateFormat df = new SimpleDateFormat("MMM d");
            t_date = df.format(Calendar.getInstance().getTime());
            Log.d("aaaaaaaaaaaa",t_date);
        }


        if(!t_name.isEmpty() && t_num!=null && ( (t_mail.isEmpty() && mail.isEmpty()) || (!t_mail.isEmpty() && !mail.isEmpty()) )  ){
            return true;
        }
        return false;

    }


    public void location_check() throws IOException {
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation() && network){
            loc_con=true;
            t_lat=gps.getLatitude();
            t_lon=gps.getLongitude();
            List<Address> addresses=null;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try{
                addresses = geocoder.getFromLocation(t_lat,t_lon, 1);
            }
            catch (IOException e){

            }

             Log.d("lat lon",t_lat+" "+t_lon);// Here 1 represent max location result to returned, by documents it recommended 1 to 5
          if(!addresses.isEmpty()) {
              String place1 = addresses.get(0).getSubLocality();
              String place2 = addresses.get(0).getSubAdminArea();
              String place3 = addresses.get(0).getPostalCode();
              t_loc = place1 + " , " + place2 + "-" + place3;
              Button gps_butn = (Button) findViewById(R.id.gps_button);
              if (!t_loc.isEmpty()) {
                  gps_butn.setText("Location Added");
              }
              textview = (TextView) findViewById(R.id.fieldCountry);
              textview.setText(t_loc);
          }
        }
        else {
            loc_con=false;
        }

        TextView loc_txt=(TextView)findViewById(R.id.locn_txt);
        if (!loc_con){
            loc_txt.setVisibility(View.VISIBLE);
            loc_txt.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.flashing);
            loc_txt.setAnimation(animation);
            
        }
        else{
            loc_txt.setVisibility(View.GONE);

        }
    }

    public void gps_onn_fun(final View view){
        alert_gps();
    }
    public void alert_gps(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Home_page.this, android.R.style.Theme_Holo));

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_add_location, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.alrt1_edit);
        Button button = (Button) v.findViewById(R.id.alrt1_btn);
        TextView textView = (TextView) v.findViewById(R.id.alrt1_txt);
        final TextView req = (TextView) v.findViewById(R.id.alrt1_req);

        editText.setTypeface(font2);
        editText.setText(t_loc);
        button.setTypeface(font2);
        textView.setTypeface(font2);
        req.setTypeface(font2);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc = editText.getText().toString();
                if (loc.isEmpty()) {
                    editText.setBackgroundResource(R.drawable.border_edittxt_mandtory);
                    req.setText("* required");
                } else {
                    t_loc = loc;
                    alertDialog.hide();
                    Button gps_btn=(Button)findViewById(R.id.gps_button);
                    gps_btn.setText("Location Added");

                    if(all_ok){

                        final String alphabet = "0123456789ABCDEFGHIJ";
                        final int N = alphabet.length();

                        Random r = new Random();
                        String t_code = null;
                        t_code="";
                        for (int i = 0; i < 5; i++) {
                             t_code+=alphabet.charAt(r.nextInt(N));
                        }


                        Log.d("lat lon 22222222 ",t_lat+" "+t_lon);
                        UT_class UT=new UT_class(t_name,t_mail,t_num,t_loc,t_lat,t_lon,t_desc,t_date,t_status,t_code);
                        server_db.server_addUT(UT, mem_ID);
                        db.add_UT(UT);
                        tapped_back();
                        all_ok=false;
                    }


                }
            }
        });

    }


    public void awards_fun(View view){
        Intent intent = new Intent(Home_page.this, NGO_Masters.class);
        startActivityForResult(intent,ngo_master);
    }

    public void my_task_fun(View view){
        Intent intent = new Intent(Home_page.this, User_assign_task.class);
        intent.putExtra("server_con", server_con);
        intent.putExtra("from","task");
        startActivityForResult(intent, my_tasks);
    }

    public void completed_task_fun(View view){
        Intent intent = new Intent(Home_page.this, User_completed_task.class);
        intent.putExtra("from","completed");
        startActivityForResult(intent, completed_task);
    }

    public void login_alert(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Home_page.this, android.R.style.Theme_Holo));

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_login2, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.alert_edit);
        Button button = (Button) v.findViewById(R.id.alert_btn);
        TextView textView1 = (TextView) v.findViewById(R.id.alert_txt1);


        editText.setTypeface(font2);
        editText.setText(t_loc);
        button.setTypeface(font2);
        textView1.setTypeface(font2);
        alertDialog = builder.create();
        alertDialog.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=editText.getText().toString();
                server_db=new Server_http(Home_page.this);
                new check_Ucode().execute();



            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView opt_img1=(ImageView)findViewById(R.id.opt_img1);
        ImageView opt_img2=(ImageView)findViewById(R.id.opt_img2);
        ImageView opt_img3=(ImageView)findViewById(R.id.opt_img3);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.option_poping);
        opt_img1.setAnimation(animation);
        opt_img2.setAnimation(animation);
        opt_img3.setAnimation(animation);

        if (requestCode==splash_screen && resultCode==RESULT_OK){
            mem_ID=db.get_U_code();
            TextView txt_memID2=(TextView)findViewById(R.id.txt_memID2);
            txt_memID2.setText(mem_ID);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        if (requestCode==splash_screen && resultCode==RESULT_CANCELED){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
            finish();
        }

        if(requestCode==ngo_page && resultCode==RESULT_CANCELED){
            finish();
        }

        if(requestCode==splash_screen){
            mem_ID=db.get_U_code();
            TextView txt_memID2=(TextView)findViewById(R.id.txt_memID2);
            txt_memID2.setText(mem_ID);
            new http_getUT1().execute();
        }


        if(requestCode==register_page && resultCode==RESULT_CANCELED){
            finish();
        }

    }

    public void font_fun(){

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                Space space=(Space)findViewById(R.id.space1);
                if(isVisible){
                    space.setVisibility(View.VISIBLE);
                }
                else {
                    space.setVisibility(View.GONE);
                }
            }
        });

        TextView txt=(TextView)findViewById(R.id.ttp_txt1);
        txt.setTypeface(font1);
        String cnt1 = "   Tap   ";
        String cnt2 = "<font color='#4C4130'>to</font>";
        String cnt3 = "   Plant";
        txt.setText(Html.fromHtml("&nbsp;"+cnt1 +"&nbsp;"+ cnt2 +"&nbsp;"+ cnt3));


        TextView txt_opt1=(TextView)findViewById(R.id.txt_opt1);
        TextView txt_opt2=(TextView)findViewById(R.id.txt_opt2);
        TextView txt_opt3=(TextView)findViewById(R.id.txt_opt3);
        Button button=(Button)findViewById(R.id.butn1);

        EditText name_edit1=(EditText)findViewById(R.id.name_edit1);
        EditText mob_edit1=(EditText)findViewById(R.id.mob_edit1);
        EditText mail_edit1=(EditText)findViewById(R.id.mail_edit1);
        EditText desc_edit1=(EditText)findViewById(R.id.desc_edit1);
        TextView txt_req11=(TextView)findViewById(R.id.txt_req11);
        TextView txt_req12=(TextView)findViewById(R.id.txt_req12);
        TextView txt_req13=(TextView)findViewById(R.id.txt_req13);
        TextView locn_txt=(TextView)findViewById(R.id.locn_txt);
        Button gps_btn=(Button)findViewById(R.id.gps_button);
        Button ok_btn=(Button)findViewById(R.id.ok_butn);


        ImageView opt_img1=(ImageView)findViewById(R.id.opt_img1);
        ImageView opt_img2=(ImageView)findViewById(R.id.opt_img2);
        ImageView opt_img3=(ImageView)findViewById(R.id.opt_img3);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.option_poping);
        opt_img1.setAnimation(animation);
        opt_img2.setAnimation(animation);
        opt_img3.setAnimation(animation);

        TextView txt_memID1=(TextView)findViewById(R.id.txt_memID1);
        TextView txt_memID2=(TextView)findViewById(R.id.txt_memID2);
        txt_memID1.setTypeface(font2);
        txt_memID2.setTypeface(font2);
        txt_memID2.setText(mem_ID);

        locn_txt.setTypeface(font2);
        button.setTypeface(font2);
        ok_btn.setTypeface(font2);
        txt_opt1.setTypeface(font2);
        txt_opt2.setTypeface(font2);
        txt_opt3.setTypeface(font2);
        name_edit1.setTypeface(font2);
        mob_edit1.setTypeface(font2);
        mail_edit1.setTypeface(font2);
        desc_edit1.setTypeface(font2);
        txt_req11.setTypeface(font2);
        txt_req12.setTypeface(font2);
        txt_req13.setTypeface(font2);
        gps_btn.setTypeface(font2);
    }

    public void register_fun(View view){
        Intent intent = new Intent(Home_page.this, Register_page.class);
        intent.putExtra("server_con",server_con);
        startActivityForResult(intent, register_page);
    }

    public void login_check(){
        int result=db.NGO_check();
        if(result==1){
            Intent intent = new Intent(Home_page.this, NGO_page.class);
            startActivityForResult(intent, ngo_page);
        }

    }

    @Override
    public void onBackPressed() {
        if(page_hint==1){
            setContentView(R.layout.home_page);
            tap_count=0;
            page_hint=0;
            font_fun();
        } else if(tapped){
          tapped_back();
        }

        else {
            finish();
        }
    }

    public void tapped_back(){
        tapped=false;
        swipeLayout.setEnabled(true);
        tap_count=0;
        LinearLayout hom_div1=(LinearLayout)findViewById(R.id.hom_div1);
        LinearLayout hom_div2=(LinearLayout)findViewById(R.id.hom_div2);
        LinearLayout top_frame=(LinearLayout)findViewById(R.id.top_frame1);
        LinearLayout people_options=(LinearLayout)findViewById(R.id.ppl_options_layout);
        Button tap_again=(Button)findViewById(R.id.gps_button);
        Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
        Animation topDown = AnimationUtils.loadAnimation(this, R.anim.top_down);
        hom_div1.setAnimation(topDown);
        hom_div2.setAnimation(bottomDown);
        hom_div2.setVisibility(View.GONE);
        tap_again.setText("Turn on the GPS / Provide location");
        tap_again.setVisibility(View.GONE);
        top_frame.setVisibility(View.VISIBLE);
        people_options.setVisibility(View.VISIBLE);




        EditText name_edit1=(EditText)findViewById(R.id.name_edit1);
        EditText mob_edit1=(EditText)findViewById(R.id.mob_edit1);
        EditText mail_edit1=(EditText)findViewById(R.id.mail_edit1);
        EditText desc_edit1=(EditText)findViewById(R.id.desc_edit1);
        TextView txt_req11=(TextView)findViewById(R.id.txt_req11);
        TextView txt_req12=(TextView)findViewById(R.id.txt_req12);
        TextView txt_req13=(TextView)findViewById(R.id.txt_req13);
        name_edit1.setBackgroundResource(R.drawable.border_edittxt);
        mob_edit1.setBackgroundResource(R.drawable.border_edittxt);
        mail_edit1.setBackgroundResource(R.drawable.border_edittxt);
        desc_edit1.setBackgroundResource(R.drawable.border_edittxt);
        name_edit1.setText("");
        mob_edit1.setText("");
        mail_edit1.setText("");
        desc_edit1.setText("");
        txt_req11.setText("*");
        txt_req13.setText("*");
        txt_req12.setVisibility(View.INVISIBLE);

        t_name=null;
        t_desc=null;
        t_mail=null;
        t_lat=0.0;
        t_lon=0.0;
        t_loc="";
        t_num=null;
    }

    class http_getUT1 extends AsyncTask<Void, Void, Result> {

        @Override
        protected Result doInBackground(Void... params) {
            JSONObject object = null;
            Log.e("log_tag", "http_getUT ");
            try {
                object = new JSONObject("{\"UT_mem\":'"+mem_ID+"'}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=get_UT1");
                String json =object.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");


                HttpResponse httpResponse = httpclient.execute(httpPost);
                String result = EntityUtils.toString(httpResponse.getEntity());
                ArrayList<UT_class> UT_array = new ArrayList<UT_class>();
                JSONArray ja = new JSONArray(result);
                int n = ja.length();
                for (int i = 0; i < n; i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    UT_class UT=new UT_class(0,Integer.parseInt(jo.getString("UT_id")),jo.getString("UT_name"),jo.getString("UT_mail"),Long.parseLong(jo.getString("UT_mob")),jo.getString("UT_loc"),Double.parseDouble(jo.getString("UT_lat")), Double.parseDouble(jo.getString("UT_lon")),jo.getString("UT_desc"),jo.getString("UT_date"),Integer.parseInt(jo.getString("UT_status")),jo.getString("UT_code"));
                    Log.d("refresh",jo.getString("UT_id"));
                    UT_array.add(UT);
                }
                Log.e("log_tag", "connection success ");
                db.refresh_UT(UT_array);
                server_con=true;
                Log.d("conn22",server_con+"");
            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
                server_con=false;

            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            swipeLayout.setRefreshing(false);
        }
    }

    class check_Ucode extends AsyncTask<Void, Void, Result> {
        @Override
        protected Result doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'U_code' : '"+code+"'}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=check_Ucode");
                String json = "";
                json = object.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = new JSONObject(result);
                Boolean rs=obj.getBoolean("U_check");
                if(rs){
                    Log.d("Success", "Try");
                    db.add_U_code(code);
                    mem_ID=code;
                    alertDialog.hide();
                }
                else{
                    alertDialog.hide();
                    wrng=true;
                }

                Log.e("log_tag", "connection success ... " + result);

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if(wrng){
                Toast.makeText(Home_page.this,"Invalid Member ID",Toast.LENGTH_SHORT).show();
                wrng=false;
            }
            else{
                TextView txt_memID2=(TextView)findViewById(R.id.txt_memID2);
                txt_memID2.setText(mem_ID);
                server_db=new Server_http(Home_page.this);
                new http_getUT1().execute();
            }

        }
    }





}
