package com.example.ritasantiago.vetcare.db.entity;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by ritasantiago on 03-03-2018.
 */

@IgnoreExtraProperties
public class Animal implements Serializable {
    public String name;
    public String sex;
    public Bitmap image;
    public String weight;
    public String specie;
    public String dateOfBirth;
    public String breed;
    public String coat;
    public String owner_name;
    public String owner_phone;

    //required for calls to getValue
    public Animal(){

    }

    public Animal(String name, String sex, Bitmap image, String weight, String specie, String dateOfBirth, String breed, String coat, String owner_name, String owner_phone){
        this.name = name;
        this.sex = sex;
        this.image = image;
        this.weight = weight;
        this.specie = specie;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.coat = coat;
        this.owner_name = owner_name;
        this.owner_phone = owner_phone;
    }

    public Animal(String name, String dateOfBirth, String sex, String specie, String breed, String coat, String weight, String owner_name){
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.specie = specie;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.coat = coat;
        this.owner_name = owner_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }
}
