package com.example.ritasantiago.vetcare.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by ritasantiago on 03-03-2018.
 */

@IgnoreExtraProperties
public class Historic {
    public String name;
    public List<String> procedures;

    public Historic(){

    }
    public Historic(String name, List<String> procedures){
        this.name = name;
        this.procedures = procedures;
    }
}
