package com.ar.developments.taptoplant;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2017.
 */
public class Server_http  extends ContextWrapper {
    public UT_class Ut=new UT_class(null,null,null,null,0.0,0.0,null,null,0,null);
    public String mem_id;
    public Boolean return_result=false;
    public SQL_db db;
    private String ip=null;
    public int s_id;
    public int ngo_id;
    public int ut_id;
    public Server_http(Context context) {
        super(context);
        db=new SQL_db(this);
        ip=getString(R.string.ip_address);
    }

    public void server_addUT(UT_class UT, String mem_ID){
        Ut=UT;
        mem_id=mem_ID;
        new http_addUT().execute();
    }
    public void server_deleteUT1(int S_id){
        s_id=S_id;
        new http_delete_UT1().execute();
    }
    public void server_modifyUT1(UT_class UT){
        Ut=UT;
        new http_modify_UT1().execute();
    }




    class http_addUT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject object = null;
            try {
                double lat = Ut.getUT_lat();
                int lat1 = (int) lat;
                double lat2 = (double) ((lat - lat1));

                double lon = Ut.getUT_lon();
                int lon1 = (int) lon;
                double lon2 = (double) ((lon - lon1));
                Log.d("..... kkk..... ",lat1+"   "+lat2);
                object = new JSONObject("{'UT_mem' : '"+mem_id+"', 'UT_name' : '"+Ut.getUT_name()+"', 'UT_mail':'"+Ut.getUT_mail()+"', 'UT_mob':"+Ut.getUT_mob()+", 'UT_loc':'"+Ut.getUT_loc()+"', 'UT_lat':"+lat1+", 'UT_lat1':"+lat2+", 'UT_lon':"+lon1+",'UT_lon1':"+lon2+",  'UT_desc':'"+Ut.getUT_desc()+"', 'UT_status':"+Ut.getUT_status()+", 'UT_date':'"+Ut.getUT_date()+"', 'UT_code':'"+Ut.getUT_code()+"'}");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=add_UT");
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

                Log.e("log_tag", "connection success ... "+result);

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }
    }

    class http_delete_UT1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'UT_id' : "+s_id+"}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=delete_UT1");
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

                Log.e("log_tag", "connection success ... "+result);

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }
    }

    class http_modify_UT1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'UT_mem' : '"+mem_id+"','S_UT_id' : "+Ut.getS_UT_id()+", 'UT_name' : '"+Ut.getUT_name()+"', 'UT_mail':'"+Ut.getUT_mail()+"', 'UT_mob':"+Ut.getUT_mob()+", 'UT_loc':'"+Ut.getUT_loc()+"', 'UT_desc':'"+Ut.getUT_desc()+"'}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://"+ip+":8080/tap_to_plant/ttp?action=modify_UT1");
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

                Log.e("log_tag", "connection success ... "+result);

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
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
