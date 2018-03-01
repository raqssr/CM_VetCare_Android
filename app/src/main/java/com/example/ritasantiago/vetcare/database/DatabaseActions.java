package com.example.ritasantiago.vetcare.database;

import android.util.Log;

import java.util.List;

/**
 * Created by ritasantiago on 01-03-2018.
 */

public class DatabaseActions {
    private static final String TAG = DatabaseActions.class.getName();

    private static Animal_general addAnimal(final AppDatabase db, Animal_general animal){
        db.animalDao().insertAnimal(animal);
        return animal;
    }

    private static void populateWithTestData(AppDatabase db) {
        Animal_general animal = new Animal_general();
        animal.setAnimal_name("Bicas");
        animal.setSex("F");
        animal.setAnimal_picture_id(1);
        animal.setWeight(1.2);
        animal.setSpecie("Cão");
        animal.setDateOfBirth("12-03-2011");
        animal.setBreed("Rafeiro");
        animal.setCoat("Curta");

        addAnimal(db, animal);

        List<Animal_general> animalsList = db.animalDao().getAllAnimals();
        Log.d(DatabaseActions.TAG, "Rows Count: " + animalsList.size());
    }


}
