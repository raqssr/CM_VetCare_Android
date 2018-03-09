package com.example.ritasantiago.vetcare.firebase;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class Internment {
    public String name;
    public String comments;
    public String reason_internment;
    public String doctor;
    public String procedure;
    public String medicine;
    public String dateIn;

    public Internment(){

    }

    public Internment(String name, String comments, String reason_internment, String doctor, String procedure, String medicine, String dateIn){
        this.name = name;
        this.comments = comments;
        this.reason_internment = reason_internment;
        this.doctor = doctor;
        this.procedure = procedure;
        this.medicine = medicine;
        this.dateIn = dateIn;
    }
}
