package com.example.ritasantiago.vetcare.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ritasantiago on 01-03-2018.
 */

@Entity(tableName = "animal_internment")
public class Internment {
    @PrimaryKey
    private String animal_name;

    public String getAnimal_name(){
        return animal_name;
    }

    public void setAnimal_name(String animal_name){
        this.animal_name = animal_name;
    }

    @ColumnInfo(name = "comments")
    private String comments;

    public String getComments(){
        return comments;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    @ColumnInfo(name = "reason_internment")
    private String reason_internment;

    public String getReason_internment(){
        return reason_internment;
    }

    public void setReason_internment(String reason_internment){
        this.reason_internment = reason_internment;
    }

    @ColumnInfo(name = "doctor")
    private String doctor;

    public String getDoctor(){
        return doctor;
    }

    public void setDoctor(String doctor){
        this.doctor = doctor;
    }

    @ColumnInfo(name = "procedures_id")
    private String procedures_id;

    public String getProcedures_id(){
        return procedures_id;
    }

    public void setProcedures_id(String procedures_id){
        this.procedures_id = procedures_id;
    }

    @ColumnInfo(name = "medicines_id")
    private int medicines_id;

    public int getMedicines_id(){
        return medicines_id;
    }

    public void setMedicines_id(int medicines_id){
        this.medicines_id = medicines_id;
    }

    @ColumnInfo(name = "dateIn")
    private String dateIn;

    public String getDateIn(){
        return dateIn;
    }

    public void setDateIn(String dateIn){
        this.dateIn = dateIn;
    }
}
