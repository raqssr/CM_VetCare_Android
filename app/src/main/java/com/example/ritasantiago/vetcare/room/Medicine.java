package com.example.ritasantiago.vetcare.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ritasantiago on 01-03-2018.
 */

@Entity(tableName = "medicine")
public class Medicine {
    @PrimaryKey(autoGenerate = true)
    private  int m_id;

    public int getM_id(){
        return m_id;
    }

    public void setM_id(int m_id){
        this.m_id = m_id;
    }

    @ColumnInfo(name = "med_name")
    private String med_name;

    public String getMed_name(){
        return med_name;
    }

    public void setMed_name(String med_name){
        this.med_name = med_name;
    }

    @ColumnInfo(name = "dosage")
    private double dosage;

    public double getDosage(){
        return dosage;
    }

    public void setDosage(Double dosage){
        this.dosage = dosage;
    }
}
