package com.example.ritasantiago.vetcare.db.entity;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ritasantiago on 03-03-2018.
 */
@IgnoreExtraProperties
public class Medicine {
    public String name, dosage, frequency, totalDays;

    public Medicine(){

    }

    public Medicine(String name, String dosage, String frequency){
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.totalDays = totalDays;
    }

    public Medicine(String name){
        this.name = name;
    }
}
