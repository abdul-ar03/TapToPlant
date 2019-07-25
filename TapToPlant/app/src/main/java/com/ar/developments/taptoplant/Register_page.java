package com.ar.developments.taptoplant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
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

import javax.xml.transform.Result;

/**
 * Created by Admin on 1/16/2017.
 */
public class Register_page extends Activity {
    public int hint_no = 0;
    private SQL_db db;
    private Boolean server_con=false;
    private String ip=null;
    private NGO_class ngo;
    private int N_id;
    private Boolean register_check = false;
    private int login_intent = 3333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        ip=getString(R.string.ip_address);
        server_con=getIntent().getBooleanExtra("server_con",false);
        db = new SQL_db(this);
        font_setting();

         KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                Space space2=(Space)findViewById(R.id.space2);
                Space space1=(Space)findViewById(R.id.space1);
                if(isVisible){
                    space1.setVisibility(View.VISIBLE);
                    space2.setVisibility(View.VISIBLE);
                }
                else {
                    space1.setVisibility(View.GONE);
                    space2.setVisibility(View.GONE);
                }
            }
        });


    }

    public void login_tab(View view) {
        LinearLayout register = (LinearLayout) findViewById(R.id.register_layout);
        LinearLayout login = (LinearLayout) findViewById(R.id.login_layout);
        Button register_btn = (Button) findViewById(R.id.register_btn);
        Button login_btn = (Button) findViewById(R.id.login_btn);


        register.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
        register_btn.setBackgroundResource(R.drawable.border_button_unselect);
        view.setBackgroundResource(R.drawable.border_button);
        register_btn.setTextColor(getResources().getColor(R.color.brown));
        login_btn.setTextColor(getResources().getColor(R.color.white));

    }


    public void register_tab(View view) {
        LinearLayout register = (LinearLayout) findViewById(R.id.register_layout);
        LinearLayout login = (LinearLayout) findViewById(R.id.login_layout);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button register_btn = (Button) findViewById(R.id.register_btn);

        register.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        login_btn.setBackgroundResource(R.drawable.border_button_unselect);
        view.setBackgroundResource(R.drawable.border_button);
        login_btn.setTextColor(getResources().getColor(R.color.brown));
        register_btn.setTextColor(getResources().getColor(R.color.white));
    }

    public void font_setting() {
        Typeface font1 = Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");



        Button login_btn1 = (Button) findViewById(R.id.login_btn);
        Button login_btn2 = (Button) findViewById(R.id.login_btn2);
        Button register_btn1 = (Button) findViewById(R.id.register_btn);
        Button register_btn2 = (Button) findViewById(R.id.register_btn3);
        EditText mob_edit2 = (EditText) findViewById(R.id.mob_edit2);
        EditText pass_edit2 = (EditText) findViewById(R.id.pass_edit2);
        EditText name_edit3 = (EditText) findViewById(R.id.name_edit3);
        EditText mob_edit3 = (EditText) findViewById(R.id.mob_edit3);
        EditText mail_edit3 = (EditText) findViewById(R.id.mail_edit3);
        EditText pass_edit3 = (EditText) findViewById(R.id.pass_edit3);

        TextView txt_req21 = (TextView) findViewById(R.id.txt_req21);
        TextView txt_req22 = (TextView) findViewById(R.id.txt_req22);
        TextView txt_req23 = (TextView) findViewById(R.id.txt_req23);
        TextView txt_til1 = (TextView) findViewById(R.id.txt_til1);
        TextView txt_til2 = (TextView) findViewById(R.id.txt_til2);

        txt_til1.setTypeface(font2);
        txt_til2.setTypeface(font2);
        login_btn1.setTypeface(font2);
        login_btn2.setTypeface(font2);
        register_btn1.setTypeface(font2);
        register_btn2.setTypeface(font2);
        mob_edit2.setTypeface(font2);
        pass_edit2.setTypeface(font2);
        name_edit3.setTypeface(font2);
        mob_edit3.setTypeface(font2);
        mail_edit3.setTypeface(font2);
        pass_edit3.setTypeface(font2);
        txt_req21.setTypeface(font2);
        txt_req22.setTypeface(font2);
        txt_req23.setTypeface(font2);


    }

    public void login_fun(View view) {
        if(server_con) {

            EditText mob_edit2 = (EditText) findViewById(R.id.mob_edit2);
            EditText pass_edit2 = (EditText) findViewById(R.id.pass_edit2);
            mob_edit2.setBackgroundResource(R.drawable.border_edittxt);
            pass_edit2.setBackgroundResource(R.drawable.border_edittxt);

            TextView txt_req21 = (TextView) findViewById(R.id.txt_req21);
            TextView txt_req22 = (TextView) findViewById(R.id.txt_req22);
            TextView txt_req23 = (TextView) findViewById(R.id.txt_req23);

            txt_req21.setVisibility(View.INVISIBLE);
            txt_req22.setVisibility(View.INVISIBLE);
            txt_req23.setVisibility(View.INVISIBLE);

            String mob_num_str = mob_edit2.getText().toString();
            String passwrd = pass_edit2.getText().toString();
            long mob_num;


            if (mob_num_str == null || mob_num_str.length() == 0 || mob_num_str.length() < 10) {
                mob_edit2.setBackgroundResource(R.drawable.border_edittxt_mandtory);
                txt_req21.setVisibility(View.VISIBLE);
            } else if (mob_num_str.length() >= 10) {
                mob_num = Long.parseLong(mob_num_str);
            }


            if (passwrd == null || passwrd.length() == 0) {
                pass_edit2.setBackgroundResource(R.drawable.border_edittxt_mandtory);
                txt_req22.setVisibility(View.VISIBLE);
            } else {

            }

            if (mob_num_str.length() != 0 && passwrd.length() != 0) {
                mob_num = Long.parseLong(mob_num_str);
                ngo = new NGO_class(0, null, null, 0L, null);
                ngo.setN_mob(mob_num);
                ngo.setN_password(passwrd);

                new http_NGO_login().execute();

            }
        }
        else{
            Toast.makeText(this,"Server not connected",Toast.LENGTH_SHORT).show();
        }


    }

    public void register_fun(View view) {
        if(server_con) {

            EditText name_edit3 = (EditText) findViewById(R.id.name_edit3);
            EditText mob_edit3 = (EditText) findViewById(R.id.mob_edit3);
            EditText mail_edit3 = (EditText) findViewById(R.id.mail_edit3);
            EditText pass_edit3 = (EditText) findViewById(R.id.pass_edit3);

            name_edit3.setBackgroundResource(R.drawable.border_edittxt);
            mob_edit3.setBackgroundResource(R.drawable.border_edittxt);
            mail_edit3.setBackgroundResource(R.drawable.border_edittxt);
            pass_edit3.setBackgroundResource(R.drawable.border_edittxt);

            String name = name_edit3.getText().toString();
            String mail = mail_edit3.getText().toString();
            String passwrd = pass_edit3.getText().toString();
            String num_str = mob_edit3.getText().toString();

            if (name == null || name.length() == 0) {
                name_edit3.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            }

            if (mail == null || mail.length() == 0) {
                mail_edit3.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            }

            if (num_str == null || num_str.length() == 0) {
                mob_edit3.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            }

            if (passwrd == null || passwrd.length() == 0) {
                pass_edit3.setBackgroundResource(R.drawable.border_edittxt_mandtory);
            }

            if (name.length() != 0 && mail.length() != 0 && num_str.length() != 0 && passwrd.length() != 0) {
                long mob = Long.parseLong(num_str);
                ngo = new NGO_class(0, name, mail, mob, passwrd);
                new http_NGO_register().execute();
            }
        }
        else{
            Toast.makeText(this,"Server not connected",Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == login_intent && resultCode == RESULT_OK) {
            Log.d("ok", "login");
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
        if (requestCode == login_intent && resultCode == RESULT_CANCELED) {
            Log.d("cancel", "login");
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    class http_NGO_register extends AsyncTask<Void, Void, Result> {
        @Override
        protected Result doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'N_name' : '" + ngo.getN_name() + "' , 'N_mob' : " + ngo.getN_mob() + " , 'N_mail' : '" + ngo.getN_mail() + "' , 'N_pass' : '" + ngo.getN_password() + "'}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://" + ip + ":8080/tap_to_plant/ttp?action=ngo_register");
                String json = "";
                json = object.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = new JSONObject(result);
                Boolean rs = obj.getBoolean("resp");
                N_id=obj.getInt("N_id");
                if (rs) {
                    Log.d("Success", "Try");
                    register_check = true;
                } else {
                    register_check = false;
                }

                Log.e("log_tag", "connection success ... " + result);

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if (register_check) {
                ngo.setN_id(N_id);
                db.Register_NGO(ngo);
                Intent intent = new Intent(Register_page.this, NGO_page.class);
                startActivityForResult(intent, login_intent);
            } else {
                Toast.makeText(Register_page.this, "Already have an account", Toast.LENGTH_SHORT).show();
                register_check = true;
            }
        }
    }

    class http_NGO_login extends AsyncTask<Void, Void, Result> {
        @Override
        protected Result doInBackground(Void... params) {
            JSONObject object = null;
            try {
                object = new JSONObject("{'N_mob' : " + ngo.getN_mob() + " ,'N_pass' : '" + ngo.getN_password() + "'}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://" + ip + ":8080/tap_to_plant/ttp?action=ngo_login");
                String json = "";
                json = object.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = new JSONObject(result);
                Boolean rs = obj.getBoolean("resp");
                if (rs) {
                    ngo.setN_id(obj.getInt("N_id"));
                    ngo.setN_name(obj.getString("N_name"));
                    ngo.setN_mail(obj.getString("N_mail"));
                    ngo.setN_password(obj.getString("N_pass"));
                    ngo.setN_mob(obj.getLong("N_mob"));
                    Log.d("Success", "Try");
                    register_check = true;
                } else {
                    register_check = false;
                }

                Log.e("log_tag", "connection success ... " + result);

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());


            }
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            if (register_check) {
                db.Register_NGO(ngo);
                Intent intent = new Intent(Register_page.this, NGO_page.class);
                startActivityForResult(intent, login_intent);
            } else {
                Toast.makeText(Register_page.this, "Invalid Account", Toast.LENGTH_SHORT).show();
                register_check = true;
            }

        }


    }
}