package com.ar.developments.taptoplant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.transform.Result;

/**
 * Created by Admin on 3/10/2017.
 */
public class Ngo_open_task extends Activity {
    private UT_class UT;
    private int ut_id;
    public SQL_db db;
    private int Ngo_id;
    private int Hint;
    private int status;
    private Typeface font1;
    private Typeface font2;
    private String code;
    public Boolean hint;
    private  AlertDialog alertDialog=null;
    private String ip=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_task_open_layout);
        ip=getString(R.string.ip_address);

        font2 = Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        font1 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        UT = (UT_class) getIntent().getSerializableExtra("UT_obj");
        Ngo_id=getIntent().getIntExtra("Ngo_id",0);
        Hint=getIntent().getIntExtra("Hint",1);
        TextView tm_name1 = (TextView) findViewById(R.id.tm_name1);
        TextView tm_name2 = (TextView) findViewById(R.id.tm_name2);
        TextView tm_mail1 = (TextView) findViewById(R.id.tm_mail1);
        TextView tm_mail2 = (TextView) findViewById(R.id.tm_mail2);
        TextView tm_mob1 = (TextView) findViewById(R.id.tm_mob1);
        TextView tm_mob2 = (TextView) findViewById(R.id.tm_mob2);
        TextView tm_loc1 = (TextView) findViewById(R.id.tm_loc1);
        TextView tm_loc2 = (TextView) findViewById(R.id.tm_loc2);
        TextView tm_desc1 = (TextView) findViewById(R.id.tm_desc1);
        TextView tm_desc2 = (TextView) findViewById(R.id.tm_desc2);


        if (Hint==1){
            LinearLayout layout=(LinearLayout)findViewById(R.id.bottom1);
            layout.setVisibility(View.VISIBLE);
            Button button = (Button) findViewById(R.id.button);
            button.setTypeface(font1);
        }
        else if(Hint==2){
            LinearLayout layout=(LinearLayout)findViewById(R.id.bottom2);
            layout.setVisibility(View.VISIBLE);
            Button button = (Button) findViewById(R.id.button2);
            button.setTypeface(font1);
            Button button2 = (Button) findViewById(R.id.button3);
            button2.setTypeface(font1);
        }
        else{
            LinearLayout layout=(LinearLayout)findViewById(R.id.bottom3);
            layout.setVisibility(View.VISIBLE);
        }



        TextView task_txt = (TextView) findViewById(R.id.task_txt);
        task_txt.setTypeface(font2);
        String cnt1 = "Task";
        String cnt2 = "<font color='#665e51'>Details</font>";
        task_txt.setText(Html.fromHtml(cnt1 + "&nbsp;" + cnt2));


        tm_name1.setTypeface(font1);
        tm_name2.setTypeface(font1);
        tm_mail1.setTypeface(font1);
        tm_mail2.setTypeface(font1);
        tm_mob1.setTypeface(font1);
        tm_mob2.setTypeface(font1);
        tm_loc1.setTypeface(font1);
        tm_loc2.setTypeface(font1);
        tm_desc1.setTypeface(font1);
        tm_desc2.setTypeface(font1);

        tm_name2.setText(UT.getUT_name());
        tm_mob2.setText("" + UT.getUT_mob());
        tm_loc2.setText(UT.getUT_loc());

        if(UT.getUT_desc().isEmpty()){
            tm_desc2.setText("- - -");
        }
        else{
            tm_desc2.setText(UT.getUT_desc());
        }

        if(UT.getUT_mail().isEmpty()){
            tm_mail2.setText("- - -");
        }
        else{
            tm_mail2.setText(UT.getUT_mail());
        }


    }

    public void take_task_fun(View view){
        ut_id=UT.getS_UT_id();
        status=1;
        new http_takeNT().execute();

    }

    public void map_activity(View view){
        UT_class UT= (UT_class) getIntent().getSerializableExtra("UT_obj");
        Intent intent = new Intent(Ngo_open_task.this, Map_Activity.class);
        intent.putExtra("lat", UT.getUT_lat());
        intent.putExtra("lon", UT.getUT_lon());
        intent.putExtra("loc", UT.getUT_loc());
        intent.putExtra("from","ngo");
        Log.d("lat long ....  ", UT.getUT_lat() + "  ....   " + UT.getUT_lon());
        startActivityForResult(intent, 1111);
    }

    public void quit_task_fun(View view){
        ut_id=UT.getS_UT_id();
        Ngo_id=0;
        status=0;
        new http_takeNT().execute();

    }
    public void insert_code_fun(View view){
        ut_id=UT.getS_UT_id();
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(Ngo_open_task.this, android.R.style.Theme_Holo));

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.alert_ttp_code, null);
        builder.setView(v);

        final EditText editText = (EditText) v.findViewById(R.id.alert_edit);
        Button button = (Button) v.findViewById(R.id.alert_btn);
        TextView textView1 = (TextView) v.findViewById(R.id.alert_txt1);


        editText.setTypeface(font1);
        button.setTypeface(font1);
        textView1.setTypeface(font1);
        alertDialog = builder.create();
        alertDialog.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = editText.getText().toString();
                new http_insert_Code().execute();
            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("return_hint",Hint);
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }

    class http_takeNT extends AsyncTask<Void, Void, Result> {
        @Override
        protected Result doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'UT_id' : "+ut_id+",'Ngo_id':"+Ngo_id+",'status':"+status+"}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=take_NT");
                String json = "";
                json = object.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                InputStream inputStream = httpResponse.getEntity().getContent();
                String result = "";
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";
                Log.e("log_tag", "connection success ... " + result + Ngo_id + "  "+ ut_id);



            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            db=new SQL_db(Ngo_open_task.this);
            db.task_update(Ngo_id, ut_id,status);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("return_hint",Hint);
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();

        }
    }

    class http_insert_Code extends AsyncTask<Void, Void, Result> {
        @Override
        protected Result doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'UT_id' : "+ut_id+",'TTPcode':"+code+"}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=insert_code");
                String json = "";
                json = object.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = new JSONObject(result);
                hint=obj.getBoolean("resp");
                Log.e("log_tag", "connection success ... " + result + Ngo_id + "  "+ ut_id);



            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if(hint){
                db=new SQL_db(Ngo_open_task.this);
                db.task_update(Ngo_id, ut_id,2);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("return_hint",Hint);
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
            Log.d("Wrong Code", "xxxxx xxxxx xxxx xxxxx");


        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        String result = null;
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();

        return result;
    }

}
