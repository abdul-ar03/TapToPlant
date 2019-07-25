package com.ar.developments.taptoplant;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.xml.transform.Result;

/**
 * Created by Admin on 3/8/2017.
 */
public class Splash_screen extends FragmentActivity {
    private   Typeface font1;
    private Typeface font2;
    private String code;
    private String ip=null;
    private SQL_db db;
    private Server_http server_db;
    private Boolean check=false;
    private Boolean con=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        font1 = Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        ip=getString(R.string.ip_address);
        db = new SQL_db(this);
        server_db = new Server_http(this);
        font();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout layout1 = (LinearLayout) findViewById(R.id.frag1);
                        LinearLayout layout2 = (LinearLayout) findViewById(R.id.frag2);
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 5000);
    }

    public void login_fun(View view){
        EditText editText=(EditText)findViewById(R.id.s_txt_4);
        code=editText.getText().toString();
        new check_Ucode().execute();
    }

    public void create_new_fun(View view){
        new create_Ucode().execute();
    }

    public void font() {
       EditText editText=(EditText)findViewById(R.id.s_txt_4);
        TextView textView2=(TextView)findViewById(R.id.s_txt_2);
        TextView textView3=(TextView)findViewById(R.id.s_txt_3);
        TextView textView4=(TextView)findViewById(R.id.s_txt_5);
        Button button=(Button)findViewById(R.id.s_btn);

        editText.setTypeface(font2);
        textView2.setTypeface(font2);
        textView4.setTypeface(font2);
        textView3.setTypeface(font2);
        button.setTypeface(font2);


        TextView txt=(TextView)findViewById(R.id.s_txt_1);
        txt.setTypeface(font1);
        String cnt1 = "   Tap   ";
        String cnt2 = "<font color='#4C4130'>to</font>";
        String cnt3 = "   Plant";
        txt.setText(Html.fromHtml("&nbsp;" + cnt1 + "&nbsp;" + cnt2 + "&nbsp;" + cnt3));
    }



//         <---- run your one time code here
//        Log.d("One Time......", "one timeeeeee.....");
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Splash_screen.this, android.R.style.Theme_Holo));
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        View v = inflater.inflate(R.layout.alert_member_login, null);
//        builder.setView(v);
//
//        final EditText editText = (EditText) v.findViewById(R.id.alert_edit);
//        Button button = (Button) v.findViewById(R.id.alert_btn);
//        TextView textView1 = (TextView) v.findViewById(R.id.alert_txt1);
//        final TextView textView2 = (TextView) v.findViewById(R.id.alert_txt2);
//
//        editText.setTypeface(font2);
//        button.setTypeface(font2);
//        textView1.setTypeface(font2);
//        textView2.setTypeface(font2);
//
//        textView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
            finish();
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
                    check=true;
                    con=true;

                }
                else{
                    con=true;
                    check=false;
                    Log.d("Invalid","Try");
                }

                Log.e("log_tag", "connection success ... "+result);

            } catch (Exception e) {
                con=false;
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            if (!con){
                Toast.makeText(Splash_screen.this, "Connection Error ..!", Toast.LENGTH_SHORT).show();
            }
            else if(!check){
                Toast.makeText(Splash_screen.this, "Invalid Member ID", Toast.LENGTH_SHORT).show();
            }
            else{
                setResult(Activity.RESULT_OK);
                finish();
            }

        }
    }


    class create_Ucode extends AsyncTask<Void, Void, Result> {
        @Override
        protected Result doInBackground(Void... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=new_mem");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = new JSONObject(result);
                String code=obj.getString("U_code");
                db.add_U_code(code);
                con=true;

                Log.e("log_tag", "connection success ... "+code);

            } catch (Exception e) {
                con=false;
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if (!con){
                Toast.makeText(Splash_screen.this, "Connection Error ..!", Toast.LENGTH_SHORT).show();
            }
            else{
                setResult(Activity.RESULT_OK);
                finish();
            }

        }
    }
}
