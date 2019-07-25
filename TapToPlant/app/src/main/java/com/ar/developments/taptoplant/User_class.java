package com.ar.developments.taptoplant;

import java.io.Serializable;

/**
 * Created by Admin on 2/1/2017.
 */
public class User_class implements Serializable  {

    private int T_id;
    private String T_name;
    private String T_mail;
    private long T_mob;
    private String T_date;
    private String T_desc;

    public User_class(int T_id,String T_name,String T_mail,long T_mob,String T_date,String T_desc){
        this.T_id=T_id;
        this.T_name=T_name;
        this.T_mail=T_mail;
        this.T_mob=T_mob;
        this.T_date=T_date;
        this.T_desc=T_desc;

    }

    public int getT_id() {
        return T_id;
    }

    public void setT_id(int t_id) {
        T_id = t_id;
    }

    public String getT_name() {
        return T_name;
    }

    public void setT_name(String t_name) {
        T_name = t_name;
    }

    public String getT_mail() {
        return T_mail;
    }

    public void setT_mail(String t_mail) {
        T_mail = t_mail;
    }

    public long getT_mob() {
        return T_mob;
    }

    public void setT_mob(long t_mob) {
        T_mob = t_mob;
    }

    public String getT_date() {
        return T_date;
    }

    public void setT_date(String t_date) {
        T_date = t_date;
    }

    public String getT_desc() {
        return T_desc;
    }

    public void setT_desc(String t_desc) {
        T_desc = t_desc;
    }
}
