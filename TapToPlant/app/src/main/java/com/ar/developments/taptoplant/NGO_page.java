package com.ar.developments.taptoplant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;

import javax.xml.transform.Result;
public class NGO_page extends Activity {
    private SQL_db db;
    private   Typeface font1;
    private Typeface font2;
    private int Ngo_id;
    private String ip=null;
    public Boolean server_con=false;
    public int task_open=1111;
    public View view;
    public SwipeRefreshLayout swipeLayout;
    private ArrayList<UT_class> NT_array = new ArrayList<UT_class>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_page);
        ip=getString(R.string.ip_address);
        font1= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        db=new SQL_db(this);
        Ngo_id=db.get_Ngo_id();
        Log.d("Ngo_id", "" + Ngo_id);
        font_fun();



        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeLayout.setColorSchemeResources(R.color.brown);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new http_getNT().execute();
            }
        });
        swipeLayout.setEnabled(true);

        new http_getNT().execute();

    }




    public void logout_fun(View view){
        db.Logout_NGO();
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void font_fun(){
        TextView txt=(TextView)findViewById(R.id.dwr_txt1);
        txt.setTypeface(font1);
        String cnt1 = "   Tap   ";
        String cnt2 = "<font color='#4C4130'>to</font>";
        String cnt3 = "   Plant";
        txt.setText(Html.fromHtml("&nbsp;" + cnt1 + "&nbsp;" + cnt2 + "&nbsp;" + cnt3));

        TextView dwr_txt2=(TextView)findViewById(R.id.dwr_txt2);
        TextView dwr_txt3=(TextView)findViewById(R.id.dwr_txt3);
        TextView dwr_txt4=(TextView)findViewById(R.id.dwr_txt4);
        TextView dwr_txt5=(TextView)findViewById(R.id.dwr_txt5);
        TextView txt_ham1=(TextView)findViewById(R.id.txt_ham1);
        TextView txt_ham2=(TextView)findViewById(R.id.txt_ham2);
        TextView ab_title=(TextView)findViewById(R.id.ab_title);
        TextView logout_txt=(TextView)findViewById(R.id.logout_txt);
        TextView refresh=(TextView)findViewById(R.id.refresh);

        TextView txt_title_win=(TextView)findViewById(R.id.award_txt);
        TextView win_txt1=(TextView)findViewById(R.id.win_txt1);
        TextView win_txt11=(TextView)findViewById(R.id.win_txt11);
        TextView win_txt2=(TextView)findViewById(R.id.win_txt2);
        TextView win_txt22=(TextView)findViewById(R.id.win_txt22);
        TextView win_txt3=(TextView)findViewById(R.id.win_txt3);
        TextView win_txt33=(TextView)findViewById(R.id.win_txt33);
        TextView empty_task=(TextView)findViewById(R.id.empty_task);

        refresh.setTypeface(font2);
        empty_task.setTypeface(font2);
        txt_title_win.setTypeface(font2);
        ab_title.setTypeface(font2);
        win_txt1.setTypeface(font2);
        win_txt11.setTypeface(font2);
        win_txt2.setTypeface(font2);
        win_txt22.setTypeface(font2);
        win_txt3.setTypeface(font2);
        win_txt33.setTypeface(font2);
        txt_ham1.setTypeface(font2);
        txt_ham2.setTypeface(font2);




        dwr_txt2.setTypeface(font2);
        dwr_txt3.setTypeface(font2);
        dwr_txt4.setTypeface(font2);
        dwr_txt5.setTypeface(font2);
        logout_txt.setTypeface(font2);
    }

    private void fun_all_task() {
        TextView ab_title=(TextView)findViewById(R.id.ab_title);
        ab_title.setText("All Tasks");
        ham_return();
        NT_array.clear();
        NT_array=db.get_all_NT();
        LinearLayout list_layout=(LinearLayout)findViewById(R.id.list_layout);
        final ListView listView=(ListView)findViewById(R.id.ngo_listview);
        LinearLayout empty_layout=(LinearLayout)findViewById(R.id.empty_layout);
        if(NT_array.size()==0){
            list_layout.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        }
        else{
            list_layout.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
            final Create_NT_Adapter adapter = new Create_NT_Adapter(NGO_page.this,NT_array);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(NGO_page.this, Ngo_open_task.class);
                    UT_class UT = NT_array.get(position);
                    intent.putExtra("Ngo_id", Ngo_id);
                    intent.putExtra("UT_obj", UT);
                    intent.putExtra("Hint",1);
                    startActivityForResult(intent, task_open);
                }
            });
        }
    }

    public void fun_my_task(){
        TextView ab_title=(TextView)findViewById(R.id.ab_title);
        ab_title.setText("My Task");
        ham_return();
        NT_array.clear();
        Log.d("settt", Ngo_id + ":");
        NT_array= db.get_my_NT(Ngo_id);

        LinearLayout list_layout=(LinearLayout)findViewById(R.id.list_layout);
        final ListView listView=(ListView)findViewById(R.id.ngo_listview);
        LinearLayout empty_layout=(LinearLayout)findViewById(R.id.empty_layout);
        if(NT_array.size()==0){
            list_layout.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        }
        else{
            list_layout.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
            final Create_NT_Adapter adapter = new Create_NT_Adapter(NGO_page.this,NT_array);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(NGO_page.this, Ngo_open_task.class);
                    UT_class UT = NT_array.get(position);
                    intent.putExtra("UT_obj", UT);
                    intent.putExtra("Hint",2);
                    startActivityForResult(intent, task_open);
                }
            });
        }
    }

    public void fun_completed_task(){
        TextView ab_title=(TextView)findViewById(R.id.ab_title);
        ab_title.setText("Completed Task");
        ham_return();
        NT_array.clear();
        Log.d("settt", Ngo_id + ":");
        NT_array= db.get_completed_NT(Ngo_id);

        LinearLayout list_layout=(LinearLayout)findViewById(R.id.list_layout);
        final ListView listView=(ListView)findViewById(R.id.ngo_listview);
        LinearLayout empty_layout=(LinearLayout)findViewById(R.id.empty_layout);
        if(NT_array.size()==0){
            list_layout.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        }
        else{
            list_layout.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
            final Create_NT_Adapter adapter = new Create_NT_Adapter(NGO_page.this,NT_array);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(NGO_page.this, Ngo_open_task.class);
                    UT_class UT = NT_array.get(position);
                    intent.putExtra("UT_obj", UT);
                    intent.putExtra("Hint",3);
                    startActivityForResult(intent, task_open);
                }
            });
        }
    }
    public void all_task_fun(View view){
        fun_all_task();
    }
    public void my_task_fun(View view){
        fun_my_task();
    }
    public void completed_task_fun(View view){
        fun_completed_task();
    }

    public void ngo_master_fun(View view){
        TextView ab_title=(TextView)findViewById(R.id.ab_title);
        ab_title.setText("NGO Masters");
        ham_return();
        LinearLayout award_layout=(LinearLayout)findViewById(R.id.award_layout);
        award_layout.setVisibility(View.VISIBLE);

    }
    public void ham_return(){
        DrawerLayout mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        LinearLayout list_layout=(LinearLayout)findViewById(R.id.list_layout);
        LinearLayout empty_layout=(LinearLayout)findViewById(R.id.empty_layout);
        LinearLayout award_layout=(LinearLayout)findViewById(R.id.award_layout);
        list_layout.setVisibility(View.GONE);
        empty_layout.setVisibility(View.GONE);
        award_layout.setVisibility(View.GONE);

    }
    public void ham_fun(View view){
        DrawerLayout mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }


    public void refresh_fun(View view){
        new http_getNT().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==task_open) {
            int n = data.getIntExtra("return_hint", 1);
            if (n == 1)
            {
                fun_all_task();
            }
            else if (n == 2) {
                fun_my_task();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }

        class http_getNT extends AsyncTask<Void, Void, Result> {
            @Override
            protected Result doInBackground(Void... params) {
                JSONObject object = null;
                NT_array.clear();
                try {
                    object = new JSONObject("{\"Ngo_id\":" + Ngo_id + "}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://" + ip + ":8080/tap_to_plant/ttp?action=get_NT");
                    String json = "";
                    json = object.toString();
                    StringEntity se = new StringEntity(json);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");

                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    String result = EntityUtils.toString(httpResponse.getEntity());
                    JSONArray ja = new JSONArray(result);
                    int n = ja.length();
                    for (int i = 0; i < n; i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        UT_class NT=new UT_class(0,Integer.parseInt(jo.getString("UT_id")),jo.getString("UT_name"),jo.getString("UT_mail"),Long.parseLong(jo.getString("UT_mob")),jo.getString("UT_loc"),Double.parseDouble(jo.getString("UT_lat")), Double.parseDouble(jo.getString("UT_lon")),jo.getString("UT_desc"),Integer.parseInt(jo.getString("Ngo_id")),Integer.parseInt(jo.getString("UT_status")));
                        Log.d("refresh", jo.getString("UT_id"));
                        NT_array.add(NT);
                    }
                    Log.e("log_tag", "connection success ");
                    server_con=true;
                    Log.d("conn22", server_con + "");
                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());
                    server_con=false;

                    Log.e("log_tag", "connection success ... " );

                }
                return null;
            }



            @Override
            protected void onPostExecute(Result result) {
                DrawerLayout mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(Gravity.LEFT);
                db.refresh_NT(NT_array);
                fun_all_task();

            }
        }



}
