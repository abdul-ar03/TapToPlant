package com.ar.developments.taptoplant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Admin on 2/12/2017.
 */
public class User_modify_task  extends Activity {
    private UT_class UT=null;
    private SQL_db db;
    private Server_http server_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_modify_layout);
        db=new SQL_db(this);
        server_db=new Server_http(this);
        Typeface font2= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        Typeface font1 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        UT = (UT_class) getIntent().getSerializableExtra("UT_obj");
        Log.d("utttttt 11111", UT.getUT_id() + "");
        TextView tm_name1 = (TextView) findViewById(R.id.tm_name1);
        EditText tm_name2 = (EditText) findViewById(R.id.tm_name2);
        TextView tm_mail1 = (TextView) findViewById(R.id.tm_mail1);
        EditText tm_mail2 = (EditText) findViewById(R.id.tm_mail2);
        TextView tm_mob1 = (TextView) findViewById(R.id.tm_mob1);
        EditText tm_mob2 = (EditText) findViewById(R.id.tm_mob2);
        TextView tm_loc1 = (TextView) findViewById(R.id.tm_loc1);
        EditText tm_loc2 = (EditText) findViewById(R.id.tm_loc2);
        TextView tm_desc1 = (TextView) findViewById(R.id.tm_desc1);
        EditText tm_desc2 = (EditText) findViewById(R.id.tm_desc2);
        Button summit = (Button) findViewById(R.id.tsk_submit);
        ScrollView scrollView=(ScrollView)findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(false);

        TextView task_txt = (TextView) findViewById(R.id.task_txt);
        task_txt.setTypeface(font2);
        String cnt1 = "Task";
        String cnt2 = "<font color='#665e51'>Modify</font>";
        task_txt.setText(Html.fromHtml(cnt1 + "&nbsp;"+ cnt2));


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
        summit.setTypeface(font1);

        tm_name2.setText(UT.getUT_name());
        tm_loc2.setText(UT.getUT_loc());
        tm_mob2.setText(""+UT.getUT_mob());
        tm_desc2.setText(UT.getUT_desc());
        tm_mail2.setText(UT.getUT_mail());

    }

    public void submit_fun(View view){
        EditText tm_name = (EditText) findViewById(R.id.tm_name2);
        EditText tm_mail = (EditText) findViewById(R.id.tm_mail2);
        EditText tm_mob = (EditText) findViewById(R.id.tm_mob2);
        EditText tm_loc = (EditText) findViewById(R.id.tm_loc2);
        EditText tm_desc = (EditText) findViewById(R.id.tm_desc2);
        tm_name.setBackgroundResource(R.drawable.border_edittxt2);
        tm_mail.setBackgroundResource(R.drawable.border_edittxt2);
        tm_mob.setBackgroundResource(R.drawable.border_edittxt2);
        tm_loc.setBackgroundResource(R.drawable.border_edittxt2);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String name;
        String mail = "";
        String desc="";
        String loc;
        String str_mob;
        Long mob;

        name=tm_name.getText().toString();
        String t_mail=tm_mail.getText().toString();
        loc=tm_loc.getText().toString();
        desc=tm_desc.getText().toString();
        str_mob=tm_mob.getText().toString();

        if(name.isEmpty()){
            tm_name.setBackgroundResource(R.drawable.border_edittxt_mandtory2);
        }



        if(!t_mail.matches(emailPattern) && t_mail.length()!=0){
            tm_mail.setBackgroundResource(R.drawable.border_edittxt_mandtory2);
            mail="";
        }
        else{
            mail=t_mail;
        }

        if(str_mob.isEmpty() || str_mob.length()<10){
            tm_mob.setBackgroundResource(R.drawable.border_edittxt_mandtory2);
            mob=null;
        }
        else{
            mob=Long.parseLong(str_mob);
        }

        if(loc.isEmpty()){
            tm_loc.setBackgroundResource(R.drawable.border_edittxt_mandtory2);
        }




        if(!name.isEmpty() && mob!=null && !loc.isEmpty() &&( (mail.isEmpty() && t_mail.isEmpty()) || (!mail.isEmpty() && !t_mail.isEmpty()) )  ){
            UT.setUT_name(name);
            UT.setUT_loc(loc);
            UT.setUT_desc(desc);
            UT.setUT_mob(mob);
            UT.setUT_mail(mail);
            server_db.server_modifyUT1(UT);
            db.modify_UT(UT);

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            Log.d("okkkkkkkk", "olllllllllll");
        }
        else{
            Log.d("okkkkkkkk", "nooooooooooooo");
        }
    }
}
