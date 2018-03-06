package com.example.ritasantiago.vetcare.room;

/**
 * Created by ritasantiago on 01-03-2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface Animal_dao {

    //get all animals
    @Query("SELECT animal_name FROM animal_general")
    List<Animal> getAnimals();

    //get owner's name
    @Query("SELECT owner_name FROM animal_general WHERE animal_name LIKE :a_name")
    String getOwner(String a_name);

    //get general info by animal's name
    @Query("SELECT * FROM animal_general WHERE animal_name LIKE :a_name ")
    Animal findGeneralByName(String a_name);

    //get internment info by animal's name
    @Query("SELECT * FROM animal_internment WHERE animal_name LIKE :a_name ")
    Internment findInterByName(String a_name);

    //get historic info by animal's name
    @Query("SELECT * FROM animal_historic WHERE animal_name LIKE :a_name ")
    Historic findHistByName(String a_name);

    //insert animal
    @Insert
    void insertAnimal(Animal animal);

    //update animal
    @Update
    void updateAnimal(Animal animal);

}
