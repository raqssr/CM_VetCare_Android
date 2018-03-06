package com.example.ritasantiago.vetcare.firebase;

import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ritasantiago on 03-03-2018.
 */
@IgnoreExtraProperties
public class Medicine {
    public int uid;
    public String name;
    public double dosage;

    public Medicine(){

    }

    public Medicine(int uid, String name, double dosage){
        this.uid = uid;
        this.name = name;
        this.dosage = dosage;
    }
}
