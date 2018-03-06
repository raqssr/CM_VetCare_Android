package com.example.ritasantiago.vetcare.firebase;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class Internment {
    public String name;
    public String comments;
    public String reason_internment;
    public String doctor;
    public int procedure_id;
    public int medicine_id;
    public String dateIn;

    public Internment(){

    }

    public Internment(String name, String comments, String reason_internment, String doctor, int procedure_id, int medicine_id, String dateIn){
        this.name = name;
        this.comments = comments;
        this.reason_internment = reason_internment;
        this.doctor = doctor;
        this.procedure_id = procedure_id;
        this.medicine_id = medicine_id;
        this.dateIn = dateIn;
    }
}
