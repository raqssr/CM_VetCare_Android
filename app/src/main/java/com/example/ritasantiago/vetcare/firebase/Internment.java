package com.example.ritasantiago.vetcare.firebase;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class Internment {
    public String name;
    public String comments;
    public String reason_internment;
    public String doctor;
    public String dateIn;

    public Internment(){

    }

    public Internment(String dateIn, String motive, String comments, String doctor){
        this.comments = comments;
        this.reason_internment = motive;
        this.doctor = doctor;
        this.dateIn = dateIn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReason_internment() {
        return reason_internment;
    }

    public void setReason_internment(String reason_internment) {
        this.reason_internment = reason_internment;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }
}
