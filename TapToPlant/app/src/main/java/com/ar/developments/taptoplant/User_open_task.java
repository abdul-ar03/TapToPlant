package com.ar.developments.taptoplant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Admin on 2/12/2017.
 */
public class User_open_task extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_open_layout);
        Typeface font2= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        Typeface font1= Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        UT_class UT= (UT_class) getIntent().getSerializableExtra("UT_obj");
        String from=getIntent().getStringExtra("from");
        if(from.equals("completed")){
            LinearLayout status=(LinearLayout)findViewById(R.id.status_div);
            status.setVisibility(View.GONE);
            LinearLayout code=(LinearLayout)findViewById(R.id.code_div);
            code.setVisibility(View.INVISIBLE);
        }
        TextView tm_name1=(TextView)findViewById(R.id.tm_name1);
        TextView tm_name2=(TextView)findViewById(R.id.tm_name2);
        TextView tm_mail1=(TextView)findViewById(R.id.tm_mail1);
        TextView tm_mail2=(TextView)findViewById(R.id.tm_mail2);
        TextView tm_mob1=(TextView)findViewById(R.id.tm_mob1);
        TextView tm_mob2=(TextView)findViewById(R.id.tm_mob2);
        TextView tm_status1=(TextView)findViewById(R.id.tm_status1);
        TextView tm_status2=(TextView)findViewById(R.id.tm_status2);
        TextView tm_date1=(TextView)findViewById(R.id.tm_date1);
        TextView tm_date2=(TextView)findViewById(R.id.tm_date2);
        TextView tm_code1=(TextView)findViewById(R.id.tm_code1);
        TextView tm_code2=(TextView)findViewById(R.id.tm_code2);
        TextView tm_loc1=(TextView)findViewById(R.id.tm_loc1);
        TextView tm_loc2=(TextView)findViewById(R.id.tm_loc2);
        TextView tm_desc1=(TextView)findViewById(R.id.tm_desc1);
        TextView tm_desc2=(TextView)findViewById(R.id.tm_desc2);


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
        tm_status1.setTypeface(font1);
        tm_status2.setTypeface(font1);
        tm_date1.setTypeface(font1);
        tm_date2.setTypeface(font1);
        tm_code1.setTypeface(font1);
        tm_code2.setTypeface(font1);
        tm_loc1.setTypeface(font1);
        tm_loc2.setTypeface(font1);
        tm_desc1.setTypeface(font1);
        tm_desc2.setTypeface(font1);

        tm_name2.setText(UT.getUT_name());
        tm_mob2.setText(""+UT.getUT_mob());
        tm_code2.setText(UT.getUT_code());

        String status = null;
        if(UT.getUT_status()==0){
            status= "<font color='#6EC05D'>Submited</font>";
        }
        else if(UT.getUT_status()==1){
            status = "<font color='#ffa500'>Progress</font>";
        }
        else if(UT.getUT_status()==2){
            status = "<font color='#ff1919'>Offline</font>";
        }
        tm_status2.setText(Html.fromHtml(status));

        Log.d("check", "" + UT.getUT_lat() + "  " + UT.getUT_lon());
        if(UT.getUT_lat()!=0.0 && UT.getUT_lon()!=0.0){
            String content="<font color='#6EC05D'>"+UT.getUT_loc()+"</font>";
            tm_loc2.setText(Html.fromHtml(content));
        }
        else{
            tm_loc2.setText(UT.getUT_loc());
        }

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


        tm_date2.setText(UT.getUT_date());



        if(UT.getUT_lat()!=0.0 && UT.getUT_lon()!=0.0)
        {
            ImageView map_icn=(ImageView)findViewById(R.id.map_icn);
            map_icn.setVisibility(View.VISIBLE);
        }
    }

    public void map_activity(View view){
        UT_class UT= (UT_class) getIntent().getSerializableExtra("UT_obj");
        Intent intent = new Intent(User_open_task.this, Map_Activity.class);
        intent.putExtra("lat", UT.getUT_lat());
        intent.putExtra("lon", UT.getUT_lon());
        intent.putExtra("loc", UT.getUT_loc());
        intent.putExtra("from","user");
        Log.d("lat long ....  ", UT.getUT_lat() + "  ....   " + UT.getUT_lon());
        startActivityForResult(intent, 1111);
    }
}
