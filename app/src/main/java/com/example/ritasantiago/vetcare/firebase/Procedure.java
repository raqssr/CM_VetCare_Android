package com.example.ritasantiago.vetcare.firebase;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class Procedure {
    public int uid;
    public String date;
    public int picture_id;

    public Procedure(){

    }

    public Procedure(int uid, String date, int picture_id){
        this.uid = uid;
        this.date = date;
        this.picture_id = picture_id;
    }
}
