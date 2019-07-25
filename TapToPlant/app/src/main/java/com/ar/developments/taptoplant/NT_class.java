package com.ar.developments.taptoplant;

import java.io.Serializable;

/**
 * Created by Admin on 2/22/2017.
 */
public class NT_class  implements Serializable  {
    private int NT_id;
    private String NT_name;
    private String NT_mail;
    private Long NT_mob;
    private String NT_loc;
    private Double NT_lat;
    private Double NT_lon;
    private String  NT_desc;
    private String NT_date;

    public NT_class(int NT_id, String NT_name, Long NT_mob, String NT_loc){
        this.NT_name=NT_name;
        this.NT_id=NT_id;
        this.NT_mob=NT_mob;
        this.NT_loc=NT_loc;
    }
    public NT_class(int NT_id, String NT_name, String NT_mail, Long NT_mob, String NT_loc, Double NT_lat, Double NT_lon, String NT_desc, String NT_date)
    {
        this.NT_name=NT_name;
        this.NT_mail=NT_mail;
        this.NT_mob=NT_mob;
        this.NT_loc=NT_loc;
        this.NT_lon=NT_lon;
        this.NT_lat=NT_lat;
        this.NT_desc=NT_desc;
        this.NT_date=NT_date;
        this.NT_id=NT_id;
    }

    public int getNT_id() {
        return NT_id;
    }

    public void setNT_id(int NT_id) {
        this.NT_id = NT_id;
    }

    public String getNT_name() {
        return NT_name;
    }

    public void setNT_name(String NT_name) {
        this.NT_name = NT_name;
    }

    public String getNT_mail() {
        return NT_mail;
    }

    public void setNT_mail(String NT_mail) {
        this.NT_mail = NT_mail;
    }

    public Long getNT_mob() {
        return NT_mob;
    }

    public void setNT_mob(Long NT_mob) {
        this.NT_mob = NT_mob;
    }

    public String getNT_loc() {
        return NT_loc;
    }

    public void setNT_loc(String NT_loc) {
        this.NT_loc = NT_loc;
    }

    public Double getNT_lat() {
        return NT_lat;
    }

    public void setNT_lat(Double NT_lat) {
        this.NT_lat = NT_lat;
    }

    public Double getNT_lon() {
        return NT_lon;
    }

    public void setNT_lon(Double NT_lon) {
        this.NT_lon = NT_lon;
    }

    public String getNT_desc() {
        return NT_desc;
    }

    public void setNT_desc(String NT_desc) {
        this.NT_desc = NT_desc;
    }

    public String getNT_date() {
        return NT_date;
    }

    public void setNT_date(String NT_date) {
        this.NT_date = NT_date;
    }
}
