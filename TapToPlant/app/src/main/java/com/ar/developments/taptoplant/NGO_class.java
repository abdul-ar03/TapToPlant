package com.ar.developments.taptoplant;

import java.io.Serializable;

/**
 * Created by Admin on 2/2/2017.
 */
public class NGO_class implements Serializable {

    private int N_id;
    private String N_name;
    private String N_mail;
    private Long N_mob;
    private String N_password;

    
    public NGO_class(int N_id, String N_name, String N_mail, Long N_mob, String N_password){
        this.N_id=N_id;
        this.N_name=N_name;
        this.N_mail=N_mail;
        this.N_mob=N_mob;
        this.N_password=N_password;
    }

    public NGO_class(String N_name, String N_mail, Long N_mob, String N_password){
        this.N_id=N_id;
        this.N_name=N_name;
        this.N_mail=N_mail;
        this.N_mob=N_mob;
        this.N_password=N_password;
    }

    public int getN_id() {
        return N_id;
    }

    public void setN_id(int n_id) {
        N_id = n_id;
    }

    public String getN_name() {
        return N_name;
    }

    public void setN_name(String n_name) {
        N_name = n_name;
    }

    public String getN_mail() {
        return N_mail;
    }

    public void setN_mail(String n_mail) {
        N_mail = n_mail;
    }

    public Long getN_mob() {
        return N_mob;
    }

    public void setN_mob(Long n_mob) {
        N_mob = n_mob;
    }

    public String getN_password() {
        return N_password;
    }

    public void setN_password(String n_password) {
        N_password = n_password;
    }
}
