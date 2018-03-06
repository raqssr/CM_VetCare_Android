package com.example.ritasantiago.vetcare.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ritasantiago on 03-03-2018.
 */

@IgnoreExtraProperties
public class Owner {
    public String name;
    public int phone;
    public String address;

    //required for calls getValue
    public Owner(){

    }

    public Owner(String name, int phone, String address){
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
