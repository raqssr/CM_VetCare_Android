package com.example.ritasantiago.vetcare.petlist.hospitalisation;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.DatabaseActions;
import com.example.ritasantiago.vetcare.petlist.profile.ProfilePetFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;
import static com.example.ritasantiago.vetcare.petlist.AddPetFragment.ANIMAL_BUNDLE_KEY;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raquelramos on 21-03-2018.
 */

public class AddHospitalisationFragment extends Fragment {

    public static final String DEBUG_TAG = "AddHospitalisationFragment";
    DatabaseActions database;
    FirebaseFirestore db;;
    EditText entry_motive, entry_vet, entry_observations;
    private String nameAnimal;

    private FirebaseDatabase mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //returning our layout file
        View rootView = inflater.inflate(R.layout.fragment_add_hospitalisation, container, false);
        Bundle args = getArguments();
        this.nameAnimal = (String) args.getSerializable(ANIMAL_BUNDLE_KEY);
        Log.d("Aqui vai o bundle", nameAnimal);
        db = FirebaseFirestore.getInstance();
        entry_motive = (EditText) rootView.findViewById(R.id.entry_motive);
        entry_vet = (EditText) rootView.findViewById(R.id.entry_vet);
        entry_observations = (EditText) rootView.findViewById(R.id.entry_obs);

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.btn_saveAnimal);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createHospitalisation(v);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Pet");
    }

    private void createHospitalisation(View view) {
        DocumentReference animalReference = db.collection("Animals").document(nameAnimal);

        String motive = entry_motive.getText().toString();
        String doctor = entry_vet.getText().toString();
        String obs = entry_observations.getText().toString();

        Map<String, Object> newInter = new HashMap<>();
        Date dateIn = Calendar.getInstance().getTime();
        newInter.put(DATE_IN_KEY, dateIn);
        newInter.put(REASON_KEY, motive);
        newInter.put(DOCTOR_KEY, doctor);
        newInter.put(COMMENTS_KEY, obs);

        db.collection("Internments").document(animalReference.getId()).set(newInter)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Internment Registered");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.toString());
                    }
                });

        Context context = getActivity().getApplicationContext();

        Toast.makeText(context,"Pet added with success!", Toast.LENGTH_SHORT).show();

        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();

    }
}
