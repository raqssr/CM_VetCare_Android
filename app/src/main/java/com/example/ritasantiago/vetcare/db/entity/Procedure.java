package com.example.ritasantiago.vetcare.db.entity;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class Procedure {
    public String name;
    public String date;

    public Procedure(){

    }

    public Procedure(String name, String date){
        this.name = name;
        this.date = date;
    }
}
