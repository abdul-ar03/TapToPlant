package com.ar.developments.taptoplant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 2/1/2017.
 */
public class User_completed_task  extends Activity {
    private Typeface font1;
    private Typeface font2;
    private Boolean network = false;
    private SQL_db db;
    private Boolean long_click=false;
    private ArrayList<UT_class> UT_array = new ArrayList<UT_class>();
    private int task_open=1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);
        db=new SQL_db(this);

        font1 = Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
        font2 = Typeface.createFromAsset(getAssets(), "aron_grotesque_bold.ttf");
        font_fun();
        adapter();
    }

    public void font_fun(){
        TextView title=(TextView)findViewById(R.id.ttp_txt4);
        title.setTypeface(font1);
        String cnt1 = "   Tap   ";
        String cnt2 = "<font color='#665e51'>to</font>";
        String cnt3 = "   Plant";
        title.setText(Html.fromHtml("&nbsp;" + cnt1 + "&nbsp;" + cnt2 + "&nbsp;" + cnt3));

        TextView sub_title=(TextView)findViewById(R.id.assign_txt1);
        sub_title.setText("Completed Task");
        sub_title.setTypeface(font2);
        TextView no_tsk=(TextView)findViewById(R.id.txt_no_task);
        no_tsk.setTypeface(font2);
    }

    public void adapter() {
        create_Adapter();
        final ListView listView = (ListView) findViewById(R.id.task_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!long_click) {
                    final UT_class UT = UT_array.get(position);
                    Intent intent = new Intent(User_completed_task.this, User_open_task.class);
                    int id_no = UT.getUT_id();
                    UT_class UT_1 = db.get_detail_UT(id_no);
                    Log.d("id", UT_1.getUT_id() + "");
                    Log.d("name", UT_1.getUT_name() + "");
                    Log.d("loc", UT_1.getUT_loc() + "");
                    Log.d("mob", UT_1.getUT_mob() + "");
                    intent.putExtra("UT_obj", UT_1);
                    intent.putExtra("from","completed");
                    startActivityForResult(intent, task_open);
                }
            }
        });
    }

    public void create_Adapter(){
        UT_array.clear();
        UT_array=db.get_completedUT();
        Log.d("locccccc-------",""+UT_array.size());
        for(int i=0;i<UT_array.size();i++){
            UT_class UT= UT_array.get(i);
            Log.d("locccccc",""+UT.getUT_loc());
        }
        final ListView listView=(ListView)findViewById(R.id.task_listView);
        final TextView no_tsk=(TextView)findViewById(R.id.txt_no_task);
        if(UT_array.size()==0){
            listView.setVisibility(View.GONE);
            no_tsk.setVisibility(View.VISIBLE);
        }
        else{
            listView.setVisibility(View.VISIBLE);
            no_tsk.setVisibility(View.GONE);
            final Create_UT_Adapter adapter = new Create_UT_Adapter(User_completed_task.this,UT_array,2);
            listView.setAdapter(adapter);
        }
    }

}
