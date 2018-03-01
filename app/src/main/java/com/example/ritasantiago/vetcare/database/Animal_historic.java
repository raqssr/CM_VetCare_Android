package com.example.ritasantiago.vetcare.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ritasantiago on 01-03-2018.
 */

@Entity(tableName = "animal_historic")
public class Animal_historic {
    @PrimaryKey
    private String animal_name;

    public String getAnimal_name(){
        return animal_name;
    }

    public void setAnimal_name(String animal_name){
        this.animal_name = animal_name;
    }

    @ColumnInfo(name = "procedures_id")
    private String procedures_id;

    public String getProcedures_id(){
        return procedures_id;
    }

    public void setProcedures_id(String procedures_id){
        this.procedures_id = procedures_id;
    }

}
