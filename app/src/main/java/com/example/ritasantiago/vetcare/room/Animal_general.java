package com.example.ritasantiago.vetcare.room;

/**
 * Created by ritasantiago on 01-03-2018.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "animal_general")
public class Animal_general {
    @PrimaryKey
    private String animal_name;

    public String getAnimal_name(){
        return animal_name;
    }

    public void setAnimal_name(String animal_name){
        this.animal_name = animal_name;
    }

    @ColumnInfo(name = "sex")
    private String sex;

    public String getSex(){
        return sex;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    @ColumnInfo(name = "animal_picture")
    private int animal_picture_id;

    public int getAnimal_picture_id(){
        return animal_picture_id;
    }

    public void setAnimal_picture_id(int animal_picture_id){
        this.animal_picture_id = animal_picture_id;
    }

    @ColumnInfo(name = "weight")
    private double weight;

    public double getWeight(){
        return weight;
    }

    public void setWeight(Double weight){
        this.weight = weight;
    }

    @ColumnInfo(name = "specie")
    private String specie;

    public String getSpecie(){
        return specie;
    }

    public void setSpecie(String specie){
        this.specie = specie;
    }

    @ColumnInfo(name = "dateOfBirth")
    private String dateOfBirth;

    public String getDateOfBirth(){
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    @ColumnInfo(name = "breed")
    private String breed;

    public String getBreed(){
        return breed;
    }

    public void setBreed(String breed){
        this.breed = breed;
    }

    @ColumnInfo(name = "coat")
    private String coat;

    public String getCoat(){
        return coat;
    }

    public void setCoat(String coat){
        this.coat = coat;
    }


}
