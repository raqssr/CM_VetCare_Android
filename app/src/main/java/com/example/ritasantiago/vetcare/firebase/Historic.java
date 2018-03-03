package com.example.ritasantiago.vetcare.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ritasantiago on 03-03-2018.
 */

@IgnoreExtraProperties
public class Historic {
    public String name;
    public int procedure_id;

    public Historic(){

    }
    public Historic(String name, int procedure_id){
        this.name = name;
        this.procedure_id = procedure_id;
    }
}
