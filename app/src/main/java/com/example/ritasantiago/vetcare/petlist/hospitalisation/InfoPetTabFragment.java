package com.example.ritasantiago.vetcare.petlist.hospitalisation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.db.entity.Internment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;

import com.example.ritasantiago.vetcare.R;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class InfoPetTabFragment extends Fragment {

    private FirebaseFirestore db;
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private Animal animal;

    TextView dateEntry, motive, observations, doctor;

    private void initializeData(String animalName){
        db = FirebaseFirestore.getInstance();
        db.collection("Internments").document(animalName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                        dateEntry.setText(doc.get(DATE_IN_KEY).toString());
                        motive.setText(doc.get(REASON_KEY).toString());
                        observations.setText(doc.get(COMMENTS_KEY).toString());
                        doctor.setText(doc.get(DOCTOR_KEY).toString());
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
        View rootView = inflater.inflate(R.layout.fragment_info_pet_tab, container, false);
        Bundle args = getArguments();
        this.animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);

        dateEntry = (TextView) rootView.findViewById(R.id.entry_date_tv);
        motive = (TextView) rootView.findViewById(R.id.motive_tv);
        observations = (TextView) rootView.findViewById(R.id.observations_tv);
        doctor = (TextView) rootView.findViewById(R.id.doctor_tv);

        //String animalName = animal.getName();

        initializeData("Kiko");

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("InfoPetTabFragment");
    }
}
