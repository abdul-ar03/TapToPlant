package com.ar.developments.taptoplant;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 1/31/2017.
 */
public class NGO_Masters extends Activity  {
    private   Typeface font1;
    private Typeface font2;
    private Boolean network=false;
    private SQL_db db;
    String[] master_array=new String[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winners_page);
        db=new SQL_db(this);

//        master_array= new String[]{"Abdul", "chennai", "Rahman", "OOTY",null,null};
//        db.putNGO_master(master_array);
//
//        master_array=db.getNGO_Masters();

        font1= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");


        update_database();
        font_fun();

    }

    public void font_fun(){
        TextView txt=(TextView)findViewById(R.id.ttp_txt3);
        txt.setTypeface(font1);
        String cnt1 = "   Tap   ";
        String cnt2 = "<font color='#665e51'>to</font>";
        String cnt3 = "   Plant";
        txt.setText(Html.fromHtml("&nbsp;" + cnt1 + "&nbsp;" + cnt2 + "&nbsp;" + cnt3));

        TextView txt_win1=(TextView)findViewById(R.id.txt_win1);
        TextView txt_win11=(TextView)findViewById(R.id.txt_win11);
        TextView txt_win2=(TextView)findViewById(R.id.txt_win2);
        TextView txt_win22=(TextView)findViewById(R.id.txt_win22);
        TextView txt_win3=(TextView)findViewById(R.id.txt_win3);
        TextView txt_win33=(TextView)findViewById(R.id.txt_win33);
        TextView txt_title_win=(TextView)findViewById(R.id.txt_title_win);

        txt_win1.setTypeface(font2);
        txt_win11.setTypeface(font2);
        txt_win2.setTypeface(font2);
        txt_win22.setTypeface(font2);
        txt_win3.setTypeface(font2);
        txt_win33.setTypeface(font2);
        txt_title_win.setTypeface(font2);

//        txt_win1.setText(master_array[0]);
//        txt_win11.setText(master_array[1]);
//        txt_win2.setText(master_array[2]);
//        txt_win22.setText(master_array[3]);
//        txt_win3.setText(master_array[4]);
//        txt_win33.setText(master_array[5]);
    }

    public void update_database(){
        if(network){
//        master_array= new String[]{"Abdul", "chennai", "Rahman", "OOTY",null,null};
//        db.putNGO_master(master_array);
        }
        else{

        }
    }

}
