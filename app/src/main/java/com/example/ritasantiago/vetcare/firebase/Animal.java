package com.example.ritasantiago.vetcare.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ritasantiago on 03-03-2018.
 */

@IgnoreExtraProperties
public class Animal {
    public String name;
    public String sex;
    public int picture_id;
    public double weight;
    public String specie;
    public String dateOfBirth;
    public String breed;
    public String coat;
    public String owner_name;

    //required for calls to getValue
    public Animal(){

    }

    public Animal(String name, String sex, int picture_id, double weight, String specie, String dateOfBirth, String breed, String coat, String owner_name ){
        this.name = name;
        this.sex = sex;
        this.picture_id = picture_id;
        this.weight = weight;
        this.specie = specie;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.coat = coat;
        this.owner_name = owner_name;
    }

    public Animal(String name, String sex, double weight, String specie, String dateOfBirth, String breed, String coat, String owner_name ){
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.specie = specie;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.coat = coat;
        this.owner_name = owner_name;
    }
}