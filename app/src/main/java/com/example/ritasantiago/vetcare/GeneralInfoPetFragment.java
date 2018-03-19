package com.example.ritasantiago.vetcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.firebase.Animal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.BREED;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.COAT;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.DOB;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.NAME;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.OWNER_NAME;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.SEX;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.SPECIE;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.WEIGHT;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class GeneralInfoPetFragment extends Fragment {
    private FirebaseFirestore db;
    private Animal animal;

    TextView animalName;
    TextView animalDateOfBirth;
    TextView animalSex;
    TextView animalSpecie;
    TextView animalBreed;
    TextView animalCoat;
    TextView animalWeight;
    TextView animalOwner;

    private void initializeData() {
        db = FirebaseFirestore.getInstance();
        db.collection("Animals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot query = task.getResult();
                    List<DocumentSnapshot> doc = query.getDocuments();
                    for(int i = 0; i< doc.size(); i++) {
                        addAnimal(new Animal(doc.get(i).get(NAME).toString(),
                                doc.get(i).get(DOB).toString(),
                                doc.get(i).get(SEX).toString(),
                                doc.get(i).get(SPECIE).toString(),
                                doc.get(i).get(BREED).toString(),
                                doc.get(i).get(COAT).toString(),
                                doc.get(i).get(WEIGHT).toString(),
                                doc.get(i).get(OWNER_NAME).toString()));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_general_info_pet, container, false);

        initializeData();

        animalName = (TextView) rootView.findViewById(R.id.g_name);
        animalDateOfBirth = (TextView) rootView.findViewById(R.id.g_date);
        animalSex = (TextView) rootView.findViewById(R.id.g_sex);
        animalSpecie = (TextView) rootView.findViewById(R.id.g_specie);
        animalBreed = (TextView) rootView.findViewById(R.id.g_breed);
        animalCoat = (TextView) rootView.findViewById(R.id.g_coat);
        animalWeight = (TextView) rootView.findViewById(R.id.g_weight);
        animalOwner = (TextView) rootView.findViewById(R.id.g_owner);

        animalName.setText(animal.name);
        animalDateOfBirth.setText(animal.dateOfBirth);
        animalSex.setText(animal.sex);
        animalSpecie.setText(animal.specie);
        animalBreed.setText(animal.breed);
        animalCoat.setText(animal.coat);
        animalWeight.setText(animal.weight);
        animalOwner.setText(animal.owner_name);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("GeneralInfoFragment");
    }

    void addAnimal(Animal animal){
        this.animal = animal;
    }

}
