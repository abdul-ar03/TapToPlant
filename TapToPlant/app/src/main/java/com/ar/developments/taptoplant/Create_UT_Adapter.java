package com.ar.developments.taptoplant;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class  Create_UT_Adapter extends ArrayAdapter {
    private Context context;
    private int Hint;
    private ArrayList<UT_class> UT_array = new ArrayList<UT_class>();
    public Create_UT_Adapter(Context context, ArrayList<UT_class>  values,int hint) {
        super(context, 0, values);
        this.context=context;
        this.UT_array=values;
        this.Hint=hint;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "aron_grotesque_bold.ttf");
        View v = convertView;
        if (v == null) {

        } else {
            v = convertView;
        }
        v = Inflater.inflate(R.layout.task_layout, null);
        TextView date_txt=(TextView)v.findViewById(R.id.tdate_txt1);
        TextView loc_txt1=(TextView)v.findViewById(R.id.tloc_txt1);
        TextView desc_txt1=(TextView)v.findViewById(R.id.tstatus_txt1);
        TextView date=(TextView)v.findViewById(R.id.tdate_txt2);
        TextView loc=(TextView)v.findViewById(R.id.tloc_txt2);
        TextView desc=(TextView)v.findViewById(R.id.tstatus_txt2);
        UT_class UT=UT_array.get(position);

        if(Hint==2){
            ImageView img=(ImageView)v.findViewById(R.id.img_dp);
            img.setImageResource(R.drawable.completed_dp);
        }

        date_txt.setTypeface(font);
        loc_txt1.setTypeface(font);
        desc_txt1.setTypeface(font);
        date.setTypeface(font);
        loc.setTypeface(font);
        desc.setTypeface(font);
        Log.d("location", UT.getUT_loc());
        date.setText(UT.getUT_date());
        loc.setText(UT.getUT_loc());
        if(UT.getUT_status()==0){
            String txt = "<font color='#6EC05D'>Submited</font>";
            desc.setText(Html.fromHtml(txt));
        }
        else if(UT.getUT_status()==1){
            String txt = "<font color='#ffa500'>Progress</font>";
            desc.setText(Html.fromHtml(txt));
        }
        else if(UT.getUT_status()==2){
            String txt = "<font color='#ff1919'>Completed</font>";
            desc.setText(Html.fromHtml(txt));
        }

        return v;
    }


}
