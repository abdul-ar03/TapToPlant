package com.ar.developments.taptoplant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 2/22/2017.
 */
public class Create_NT_Adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<UT_class> NT_array = new ArrayList<UT_class>();
    public Create_NT_Adapter(Context context, ArrayList<UT_class>  values) {
        super(context, 0, values);
        this.context=context;
        this.NT_array=values;
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
        v = Inflater.inflate(R.layout.task_layout2, null);
        TextView name_txt=(TextView)v.findViewById(R.id.tdate_txt1);
        TextView loc_txt=(TextView)v.findViewById(R.id.tloc_txt1);
        TextView mob_txt=(TextView)v.findViewById(R.id.tstatus_txt1);
        TextView name=(TextView)v.findViewById(R.id.tdate_txt2);
        TextView loc=(TextView)v.findViewById(R.id.tloc_txt2);
        TextView mob_no=(TextView)v.findViewById(R.id.tstatus_txt2);
        UT_class NT=NT_array.get(position);

        name_txt.setTypeface(font);
        mob_txt.setTypeface(font);
        loc_txt.setTypeface(font);
        name.setTypeface(font);
        loc.setTypeface(font);
        mob_no.setTypeface(font);

        Log.d("contentttt", NT.getUT_name() + "  " + NT.getUT_mob() + "  " + NT.getUT_loc() + "");
        name.setText(NT.getUT_name());
        loc.setText(NT.getUT_loc());
        mob_no.setText(NT.getUT_mob()+"");
        return v;
    }


}
