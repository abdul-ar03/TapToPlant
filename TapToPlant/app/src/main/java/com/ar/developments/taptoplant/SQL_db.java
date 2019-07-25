package com.ar.developments.taptoplant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Admin on 1/31/2017.
 */
public class SQL_db extends SQLiteOpenHelper {




    public SQL_db(Context context) {
        super(context,"Tap to Plant", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String User = "Create table User (U_id INTEGER PRIMARY KEY, U_code TEXT)";
        db.execSQL(User);

        String Masters = "Create table Masters (M_id INTEGER PRIMARY KEY AUTOINCREMENT ,M_name TEXT,M_place TEXT)";
        db.execSQL(Masters);

        String NGO_user="Create table NGO_user (N_id INTEGER PRIMARY KEY, N_name TEXT, N_mail TEXT , N_mob LONG , N_pass TEXT ) ";
        db.execSQL(NGO_user);

        String User_task="Create table User_task (UT_id INTEGER PRIMARY KEY AUTOINCREMENT,S_UT_id INTEGER, UT_name TEXT, UT_mail TEXT, UT_mob LONG,UT_loc TEXT, UT_lat DOUBLE, UT_lon DOUBLE , UT_desc TEXT, UT_date TEXT, UT_status int, UT_code TEXT, UT_done BOOLEAN DEFAULT 0)";
        db.execSQL(User_task);

        String Ngo_task="Create table Ngo_task (NT_id INTEGER PRIMARY KEY AUTOINCREMENT,S_NT_id INTEGER, NT_name TEXT, NT_mail TEXT, NT_mob LONG,NT_loc TEXT, NT_lat DOUBLE, NT_lon DOUBLE , NT_desc TEXT, Ngo_id int , NT_status int)";
        db.execSQL(Ngo_task);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Masters");
        db.execSQL("DROP TABLE IF EXISTS NGO_user");
        db.execSQL("DROP TABLE IF EXISTS User_task");
        db.execSQL("DROP TABLE IF EXISTS Ngo_task");

    }

    public void add_U_code(String U_code){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql1 = "Delete from User";
        db.execSQL(sql1);
Log.d("here comming", "here" + U_code);
        SQLiteDatabase db2 = this.getWritableDatabase();
        String sql = "insert into User(U_code) values('"+U_code+"')";
        db2.execSQL(sql);
    }

    public String get_U_code(){
        String code=null;
        String  selectQuery = "select * from User ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!= null){
            cursor.moveToFirst();
            if((cursor.moveToFirst()) || cursor.getCount() !=0){
                code=cursor.getString(1);
                Log.d("code",code);
            }


        }
        else{
            Log.d("code else", code);
        }
        return code;
    }



    public void add_UT(UT_class Ut){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql2 = "insert into User_task (UT_name,UT_mail,UT_mob,UT_loc,UT_lat,UT_lon,UT_desc,UT_date,UT_status,UT_code) values('"+Ut.getUT_name()+"','"+Ut.getUT_mail()+"',"+Ut.getUT_mob()+",'"+Ut.getUT_loc()+"',"+Ut.getUT_lat()+","+Ut.getUT_lon()+",'"+Ut.getUT_desc()+"','"+ Ut.getUT_date()+"',"+Ut.getUT_status()+" ,'"+Ut.getUT_code()+"')";
        db.execSQL(sql2);
    }

    public void modify_UT(UT_class Ut){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE User_task SET UT_name = '"+Ut.getUT_name()+"', UT_mail = '"+Ut.getUT_mail()+"', UT_mob = "+Ut.getUT_mob()+", UT_loc = '"+Ut.getUT_loc()+"' , UT_desc ='"+Ut.getUT_desc()+"' where UT_id ="+Ut.getUT_id();
        db.execSQL(sql);
    }

    public void refresh_UT(ArrayList <UT_class> UT_array){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from User_task";
        db.execSQL(sql);

        SQLiteDatabase db2 = this.getWritableDatabase();
        for(int i=0;i<UT_array.size();i++){
            UT_class Ut=UT_array.get(i);
            String sql2 = "insert into User_task (S_UT_id,UT_name,UT_mail,UT_mob,UT_loc,UT_lat,UT_lon,UT_desc,UT_date,UT_status,UT_code) values("+Ut.getS_UT_id()+",'"+Ut.getUT_name()+"','"+Ut.getUT_mail()+"',"+Ut.getUT_mob()+",'"+Ut.getUT_loc()+"',"+Ut.getUT_lat()+","+Ut.getUT_lon()+",'"+Ut.getUT_desc()+"','"+ Ut.getUT_date()+"',"+Ut.getUT_status()+" ,'"+Ut.getUT_code()+"')";
            db2.execSQL(sql2);
        }

    }

    public ArrayList<UT_class> get_assignUT(){
        ArrayList<UT_class> UT_array = new ArrayList<UT_class>();
        String  selectQuery = "select UT_id,S_UT_id,UT_date,UT_status,UT_loc from User_task where UT_status!=2 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                UT_class UT=new UT_class(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4));
                UT_array.add(UT);
            }
            while (cursor.moveToNext());
        }
        return UT_array;
    }

    public ArrayList<UT_class> get_completedUT(){
        ArrayList<UT_class> UT_array = new ArrayList<UT_class>();
        String  selectQuery = "select UT_id,S_UT_id,UT_date,UT_status,UT_loc from User_task where UT_status=2 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                UT_class UT=new UT_class(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4));
                UT_array.add(UT);
            }
            while (cursor.moveToNext());
        }
        return UT_array;
    }


    public ArrayList<NT_class> get_assignNT(){
        ArrayList<NT_class> NT_array = new ArrayList<NT_class>();
        String  selectQuery = "select UT_id,UT_name,UT_mob,UT_loc from User_task ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                NT_class UT=new NT_class(cursor.getInt(0),cursor.getString(1),cursor.getLong(2),cursor.getString(3));
                NT_array.add(UT);
            }
            while (cursor.moveToNext());
        }
        return NT_array;
    }

    public UT_class get_detail_UT(int id){
        UT_class UT = null;
        String  selectQuery = "select S_UT_id,UT_Name,UT_mail,UT_mob,UT_loc,UT_lat,UT_lon,UT_desc,UT_date,UT_status,UT_code from User_task where UT_id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                UT=new UT_class(id,cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getString(4),cursor.getDouble(5),cursor.getDouble(6),cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10));
            }
            while (cursor.moveToNext());
        }
        return UT;

    }

    public void remove_task(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "delete from User_task where UT_id ="+id;
        db.execSQL(sql);
    }


    public void Register_NGO(NGO_class ngo){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql1 = "delete from NGO_user";
        db.execSQL(sql1);
        String sql = "insert into NGO_user (N_id,N_name,N_mail,N_mob,N_pass) values("+ngo.getN_id()+",'"+ngo.getN_name()+"','"+ngo.getN_mail()+"',"+ngo.getN_mob()+",'"+ngo.getN_password()+"')";
        db.execSQL(sql);
    }

    public int Login_NGO( long mob , String pass){
        int result=0;
        String  selectQuery = "select * from NGO_user";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                NGO_class ngo=new NGO_class(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getString(4));
                if(ngo.getN_mob()== mob && ngo.getN_password().equals(pass)){
                    Log.d("mob","ok");
                    result=1;
                }

            }
            while (cursor.moveToNext());
        }

        return result;

    }

    public void Logout_NGO(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "delete from NGO_user";
        db.execSQL(sql);
    }




    public int NGO_check(){
        int result=0;
        String  selectQuery = "select * from NGO_user";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(1)!=null){
                    result=1;
                }
            }
            while (cursor.moveToNext());
        }

        return result;
    }

    public String[] getNGO_Masters(){
        String[] master_array=new String[6];
        String  selectQuery = "select * from Masters where M_name IS NOT NULL and  M_place IS NOT NULL";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                master_array[i]=cursor.getString(1);
                i=i+1;
                master_array[i]=cursor.getString(2);
                i=i+1;

                Log.d("loop222",""+cursor.getString(1)+"  "+cursor.getString(2));
            }
            while (cursor.moveToNext());
        }

        return master_array;
    }

    public void putNGO_master(String masters[]){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "delete from Masters";
        db.execSQL(sql);

//        if(masters.length==1){
//            String sql2 = "insert into Masters (M_name,M_place) values('"+masters[0]+"','"+masters[1]+"')";
//            db.execSQL(sql2);
//        }
//        else if(masters.length==2){
//            String sql2 = "insert into Masters (M_name,M_place) values('"+masters[0]+"','"+masters[1]+"'),('"+masters[2]+"','"+masters[3]+"')";
//            db.execSQL(sql2);
//        }
//        else if(masters.length==6){
            String sql2 = "insert into Masters (M_name,M_place) values('"+masters[0]+"','"+masters[1]+"'),('"+masters[2]+"','"+masters[3]+"'),('"+masters[4]+"','"+masters[5]+"')";
            db.execSQL(sql2);
//        }

    }

    public void refresh_NT(ArrayList <UT_class> UT_array){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from Ngo_task";
        db.execSQL(sql);

        SQLiteDatabase db2 = this.getWritableDatabase();
        for(int i=0;i<UT_array.size();i++){
            UT_class Nt=UT_array.get(i);
            String sql2 = "insert into Ngo_task (S_NT_id,NT_name,NT_mail,NT_mob,NT_loc,NT_lat,NT_lon,NT_desc,Ngo_id,NT_status) values("+Nt.getS_UT_id()+",'"+Nt.getUT_name()+"','"+Nt.getUT_mail()+"',"+Nt.getUT_mob()+",'"+Nt.getUT_loc()+"',"+Nt.getUT_lat()+","+Nt.getUT_lon()+",'"+Nt.getUT_desc()+"',"+Nt.getNgo_id()+","+Nt.getUT_status()+")";
            db2.execSQL(sql2);
        }

    }

    public int get_Ngo_id() {
        int Ngo_id=0;
        String  selectQuery = "select N_id from Ngo_user";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Ngo_id=cursor.getInt(0);
        }
        return Ngo_id;
    }

    public ArrayList<UT_class> get_all_NT() {
        ArrayList<UT_class> NT_array = new ArrayList<UT_class>();
        String  selectQuery = "select NT_id,S_NT_id,NT_name,NT_mail,NT_mob,NT_loc,NT_lat,NT_lon,NT_desc,Ngo_id,NT_status from Ngo_task where Ngo_id=0 and NT_status= 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UT_class NT=new UT_class(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getLong(4),cursor.getString(5),cursor.getDouble(6),cursor.getDouble(7),cursor.getString(8),cursor.getInt(9),cursor.getInt(10));
                NT_array.add(NT);
            }
            while (cursor.moveToNext());
        }
        return NT_array;
    }

    public ArrayList<UT_class> get_my_NT(int Ngo_id) {
        ArrayList<UT_class> NT_array = new ArrayList<UT_class>();
        Log.d("sqllll",Ngo_id+":");
        String  selectQuery = "select NT_id,S_NT_id,NT_name,NT_mail,NT_mob,NT_loc,NT_lat,NT_lon,NT_desc,Ngo_id,NT_status  from Ngo_task where Ngo_id = "+Ngo_id+" and NT_status= 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UT_class NT=new UT_class(cursor.getInt(0),cursor.getInt(1), cursor.getString(2),cursor.getString(3), cursor.getLong(4), cursor.getString(5), cursor.getDouble(6),cursor.getDouble(7), cursor.getString(8),cursor.getInt(9),cursor.getInt(10));
                NT_array.add(NT);
            }
            while (cursor.moveToNext());
        }
        return NT_array;
    }

    public ArrayList<UT_class> get_completed_NT(int Ngo_id) {
        ArrayList<UT_class> NT_array = new ArrayList<UT_class>();
        Log.d("sqllll",Ngo_id+":");
        String  selectQuery = "select NT_id,S_NT_id,NT_name,NT_mail,NT_mob,NT_loc,NT_lat,NT_lon,NT_desc,Ngo_id,NT_status  from Ngo_task where Ngo_id = "+Ngo_id+" and NT_status= 2;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UT_class NT=new UT_class(cursor.getInt(0),cursor.getInt(1), cursor.getString(2),cursor.getString(3), cursor.getLong(4), cursor.getString(5), cursor.getDouble(6),cursor.getDouble(7), cursor.getString(8),cursor.getInt(9),cursor.getInt(10));
                NT_array.add(NT);
            }
            while (cursor.moveToNext());
        }
        return NT_array;
    }

    public void task_update(int ngo_id, int ut_id,int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE Ngo_task SET Ngo_id = "+ngo_id+", NT_status="+status+" where S_NT_id ="+ut_id+"";
        db.execSQL(sql);
    }
}
