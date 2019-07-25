package com.ar.developments.taptoplant;

import java.io.Serializable;

/**
 * Created by Admin on 2/3/2017.
 */
public class UT_class implements Serializable {
    private int UT_id;
    private int S_UT_id;
    private String UT_name;
    private String UT_mail;
    private Long UT_mob;
    private String UT_loc;
    private Double UT_lat;
    private Double UT_lon;
    private String  UT_desc;
    private String UT_code;
    private int UT_status;
    private String UT_date;
    private int Ngo_id;

    public UT_class(int UT_id,int S_UT_id, String UT_name, String UT_mail, Long UT_mob, String UT_loc, Double UT_lat, Double UT_lon, String UT_desc, String UT_date, int UT_status, String UT_code){
        this.UT_name=UT_name;
        this.UT_mail=UT_mail;
        this.S_UT_id=S_UT_id;
        this.UT_mob=UT_mob;
        this.UT_loc=UT_loc;
        this.UT_lon=UT_lon;
        this.UT_lat=UT_lat;
        this.UT_desc=UT_desc;
        this.UT_date=UT_date;
        this.UT_status=UT_status;
        this.UT_code=UT_code;
        this.UT_id=UT_id;
    }

    public UT_class(int UT_id,int S_UT_id, String UT_name, String UT_mail, Long UT_mob, String UT_loc, Double UT_lat, Double UT_lon, String UT_desc, int Ngo_id,int UT_status){
        this.UT_name=UT_name;
        this.UT_mail=UT_mail;
        this.S_UT_id=S_UT_id;
        this.UT_mob=UT_mob;
        this.UT_loc=UT_loc;
        this.UT_lon=UT_lon;
        this.UT_lat=UT_lat;
        this.UT_desc=UT_desc;
        this.UT_status=UT_status;
        this.UT_id=UT_id;
        this.Ngo_id=Ngo_id;
    }

    public UT_class(String UT_name, String UT_mail, Long UT_mob, String UT_loc, Double UT_lat, Double UT_lon, String UT_desc, String UT_date, int UT_status, String UT_code){
        this.UT_name=UT_name;
        this.UT_mail=UT_mail;
        this.UT_mob=UT_mob;
        this.UT_loc=UT_loc;
        this.UT_lon=UT_lon;
        this.UT_lat=UT_lat;
        this.UT_desc=UT_desc;
        this.UT_date=UT_date;
        this.UT_status=UT_status;
        this.UT_code=UT_code;
    }
    public UT_class(int UT_id, int S_UT_id,String UT_date, int UT_status, String UT_loc){
        this.UT_id=UT_id;
        this.S_UT_id=S_UT_id;
        this.UT_date=UT_date;
        this.UT_status=UT_status;
        this.UT_loc=UT_loc;
    }


    public int getUT_id() {
        return UT_id;
    }

    public void setUT_id(int UT_id) {
        this.UT_id = UT_id;
    }

    public String getUT_name() {
        return UT_name;
    }

    public void setUT_name(String UT_name) {
        this.UT_name = UT_name;
    }

    public String getUT_mail() {
        return UT_mail;
    }

    public void setUT_mail(String UT_mail) {
        this.UT_mail = UT_mail;
    }

    public Long getUT_mob() {
        return UT_mob;
    }

    public void setUT_mob(Long UT_mob) {
        this.UT_mob = UT_mob;
    }

    public String getUT_desc() {
        return UT_desc;
    }

    public void setUT_desc(String UT_desc) {
        this.UT_desc = UT_desc;
    }

    public String getUT_code() {
        return UT_code;
    }

    public void setUT_code(String UT_code) {
        this.UT_code = UT_code;
    }

    public String getUT_loc() {
        return UT_loc;
    }

    public void setUT_loc(String UT_loc) {
        this.UT_loc = UT_loc;
    }

    public Double getUT_lat() {
        return UT_lat;
    }

    public void setUT_lat(Double UT_lat) {
        this.UT_lat = UT_lat;
    }

    public Double getUT_lon() {
        return UT_lon;
    }

    public void setUT_lon(Double UT_lon) {
        this.UT_lon = UT_lon;
    }

    public String getUT_date() {
        return UT_date;
    }

    public void setUT_date(String UT_date) {
        this.UT_date = UT_date;
    }

    public int getUT_status() {
        return UT_status;
    }

    public void setUT_status(int UT_status) {
        this.UT_status = UT_status;
    }

    public int getS_UT_id() {
        return S_UT_id;
    }

    public void setS_UT_id(int s_UT_id) {
        S_UT_id = s_UT_id;
    }

    public int getNgo_id() {
        return Ngo_id;
    }

    public void setNgo_id(int ngo_id) {
        Ngo_id = ngo_id;
    }
}
