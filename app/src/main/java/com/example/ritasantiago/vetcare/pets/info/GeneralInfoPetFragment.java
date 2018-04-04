package com.example.ritasantiago.vetcare.pets.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;

import com.example.ritasantiago.vetcare.R;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class GeneralInfoPetFragment extends Fragment {
    private FirebaseFirestore db;
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";

    TextView animalName, animalDateOfBirth, animalSex, animalSpecie, animalBreed, animalCoat, animalWeight, animalOwner, ownerPhone;

    private void initializeOwnerInfos(String name){
        db.collection("Owners").document(name).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                ownerPhone.setText(doc.get(PHONE_KEY).toString());
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_general_info_pet, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("General Information");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_button));

        Bundle args = getArguments();
        Animal animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);

        animalName = (TextView) rootView.findViewById(R.id.g_name);
        animalDateOfBirth = (TextView) rootView.findViewById(R.id.g_date);
        animalSex = (TextView) rootView.findViewById(R.id.g_sex);
        animalSpecie = (TextView) rootView.findViewById(R.id.g_specie);
        animalBreed = (TextView) rootView.findViewById(R.id.g_breed);
        animalCoat = (TextView) rootView.findViewById(R.id.g_coat);
        animalWeight = (TextView) rootView.findViewById(R.id.g_weight);
        animalOwner = (TextView) rootView.findViewById(R.id.g_owner);
        ownerPhone = (TextView) rootView.findViewById(R.id.g_owner_phone);

        animalName.setText(animal.name);
        animalDateOfBirth.setText(animal.dateOfBirth);
        animalSex.setText(animal.sex);
        animalSpecie.setText(animal.specie);
        animalBreed.setText(animal.breed);
        animalCoat.setText(animal.coat);
        animalWeight.setText(animal.weight);
        animalOwner.setText(animal.owner_name);
        ownerPhone.setText(animal.owner_phone);


        String owner = animal.getOwner_name();

        //ownerPhone.setText(owner);
        //initializeOwnerInfos(owner);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("GeneralInfoFragment");
    }

}
