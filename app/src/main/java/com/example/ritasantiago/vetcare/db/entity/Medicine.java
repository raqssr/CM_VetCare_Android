package com.example.ritasantiago.vetcare.db.entity;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ritasantiago on 03-03-2018.
 */
@IgnoreExtraProperties
public class Medicine {
    public String name;
    public double dosage;
    public int frequency;
    public int totalDays;

    public Medicine(){

    }

    public Medicine(String name, double dosage, int frequency, int totalDays){
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.totalDays = totalDays;
    }
}
