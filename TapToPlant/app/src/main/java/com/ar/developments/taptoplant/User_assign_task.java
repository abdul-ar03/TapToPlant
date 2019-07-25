package com.ar.developments.taptoplant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
public class User_assign_task extends Activity {
    private Typeface font1;
    private Typeface font2;
    private Boolean network=false;
    private Boolean long_click=false;
    private SQL_db db;
    private Server_http server_db;
    private int task_open=1111;
    private int task_modify=2222;
    private Boolean server_con=false;
    private ArrayList<UT_class> UT_array = new ArrayList<UT_class>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);
        db=new SQL_db(this);
        server_db=new Server_http(this);
        server_con = getIntent().getBooleanExtra("server_con", false);

        font1= Typeface.createFromAsset(getAssets(), "Wonderbar Demo.otf");
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
        sub_title.setTypeface(font2);
        TextView no_tsk=(TextView)findViewById(R.id.txt_no_task);
        no_tsk.setTypeface(font2);

    }

    public void adapter(){
        create_Adapter();
        final ListView listView=(ListView)findViewById(R.id.task_listView);
        final TextView no_tsk=(TextView)findViewById(R.id.txt_no_task);
        listView.setSelector( R.drawable.listselector);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!long_click) {
                    final UT_class UT = UT_array.get(position);
                    Intent intent = new Intent(User_assign_task.this, User_open_task.class);
                    int id_no = UT.getUT_id();
                    UT_class UT_1 = db.get_detail_UT(id_no);
                    Log.d("id", UT_1.getUT_id() + "");
                    Log.d("name", UT_1.getUT_name() + "");
                    Log.d("loc", UT_1.getUT_loc() + "");
                    Log.d("mob", UT_1.getUT_mob() + "");
                    intent.putExtra("from","assign");
                    intent.putExtra("UT_obj", UT_1);
                    startActivityForResult(intent, task_open);
                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (server_con) {
                    long_click = true;
                    final UT_class UT = UT_array.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(User_assign_task.this, android.R.style.Theme_Holo));
                    LayoutInflater inflater = User_assign_task.this.getLayoutInflater();
                    View v = inflater.inflate(R.layout.alert_task_option, null);
                    builder.setView(v);


                    TextView open = (TextView) v.findViewById(R.id.e_open);
                    TextView modify = (TextView) v.findViewById(R.id.e_modify);
                    TextView delete = (TextView) v.findViewById(R.id.e_delete);
                    open.setTypeface(font2);
                    modify.setTypeface(font2);
                    delete.setTypeface(font2);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long_click = false;
                            int id = UT.getUT_id();
                            int s_id = UT.getS_UT_id();
                            Log.d("delete", s_id + "get");
                            server_db.server_deleteUT1(s_id);
                            db.remove_task(id);
                            alertDialog.hide();
                            create_Adapter();

                        }
                    });

                    open.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long_click = false;
                            Intent intent = new Intent(User_assign_task.this, User_open_task.class);
                            int id = UT.getUT_id();
                            UT_class UT = db.get_detail_UT(id);
                            intent.putExtra("from","assign");
                            intent.putExtra("UT_obj", UT);
                            startActivityForResult(intent, task_open);
                            alertDialog.hide();
                        }
                    });

                    modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long_click = false;
                            Intent intent = new Intent(User_assign_task.this, User_modify_task.class);
                            int id = UT.getUT_id();
                            UT_class UT = db.get_detail_UT(id);
                            intent.putExtra("UT_obj", UT);
                            startActivityForResult(intent, task_modify);
                            alertDialog.hide();
                        }
                    });

                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            long_click = false;
                        }

                    });

                }
                return false;
            }
        });

    }

    public void create_Adapter(){
        UT_array.clear();
        UT_array=db.get_assignUT();
        final ListView listView=(ListView)findViewById(R.id.task_listView);
        final TextView no_tsk=(TextView)findViewById(R.id.txt_no_task);
        if(UT_array.size()==0){
            listView.setVisibility(View.GONE);
            no_tsk.setVisibility(View.VISIBLE);
        }
        else{
            listView.setVisibility(View.VISIBLE);
            no_tsk.setVisibility(View.GONE);
            final Create_UT_Adapter adapter = new Create_UT_Adapter(User_assign_task.this,UT_array,1);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == task_modify && resultCode == RESULT_OK) {
          create_Adapter();
        }
    }
}
