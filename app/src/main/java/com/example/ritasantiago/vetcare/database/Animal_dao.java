package com.example.ritasantiago.vetcare.database;

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
    @Query("SELECT  * FROM animal_general")
    List<Animal_general> getAllAnimals();
    //get all animals name
    @Query("SELECT animal_name FROM animal_general")
    List<Animal_general> getAnimalsName();

    //get general info by animal's name
    @Query("SELECT * FROM animal_general WHERE animal_name LIKE :a_name ")
    Animal_general findGeneralByName(String a_name);

    //get internment info by animal's name
    @Query("SELECT * FROM animal_internment WHERE animal_name LIKE :a_name ")
    Animal_internment findInterByName(String a_name);

    //get historic info by animal's name
    @Query("SELECT * FROM animal_historic WHERE animal_name LIKE :a_name ")
    Animal_historic findHistByName(String a_name);

    //insert animal
    @Insert
    void insertAnimal(Animal_general animal);

    //update animal
    @Update
    void updateAnimal(Animal_general animal);

    /*//insert animal
    @Query("INSERT INTO animal_general (animal_name, sex, animal_picture, weight, specie, dateOfBirth, breed, coat) " +
            "VALUES (:a_name, :a_sex, :a_picture, :a_weight, :a_specie, :a_dateOfBirth, :a_breed, :a_coat )")
    Animal_general insertGeneralInfo(String a_name, String a_sex, int a_picture, double a_weight, String a_specie, String a_dateOfBirth, String a_breed, String a_coat);

    //create internment
    @Query("INSERT INTO animal_internment (animal_name, comments, reason_internment, doctor, procedures_id, medicines_id, dateIn) " +
            "VALUES (:a_name, :a_comments, :a_reason, :a_doctor, :a_proc, :a_med, :a_dateIn)")
    Animal_internment createInternment(String a_name, String a_comments, String a_reason, String a_doctor, int a_proc, int a_med, String a_dateIn);*/

}
