package com.example.ritasantiago.vetcare.pets.hospitalisation;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.DatabaseActions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;
import static com.example.ritasantiago.vetcare.pets.AddPetFragment.ANIMAL_BUNDLE_KEY;

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
    FirebaseFirestore db;
    EditText entry_motive, entry_vet, entry_observations;
    private String nameAnimal;

    private FirebaseDatabase mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //returning our layout file
        View rootView = inflater.inflate(R.layout.fragment_add_hospitalisation, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Add Animal");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_button));

        Bundle args = getArguments();
        this.nameAnimal = (String) args.getSerializable(ANIMAL_BUNDLE_KEY);
        Log.d("Aqui vai o bundle", nameAnimal);
        db = FirebaseFirestore.getInstance();
        entry_motive = (EditText) rootView.findViewById(R.id.entry_motive);
        entry_vet = (EditText) rootView.findViewById(R.id.entry_vet);
        entry_observations = (EditText) rootView.findViewById(R.id.entry_obs);

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.btn_saveAnimal);
        button.setOnClickListener(v -> createHospitalisation(v));
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
                .addOnSuccessListener(aVoid -> Log.d("TAG", "Internment Registered"))
                .addOnFailureListener(e -> Log.d("TAG", e.toString()));

        Context context = getActivity().getApplicationContext();

        Toast.makeText(context,"Pet added with success!", Toast.LENGTH_SHORT).show();

        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();

    }
}
