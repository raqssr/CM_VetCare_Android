package com.example.ritasantiago.vetcare.interfaces;

import com.example.ritasantiago.vetcare.db.entity.Animal;

/**
 * Created by raquelramos on 12-03-2018.
 */

public interface PetClickListener {

    void onPetClick(Animal animal);
    void onPetSignalClick(Animal animal);
}
