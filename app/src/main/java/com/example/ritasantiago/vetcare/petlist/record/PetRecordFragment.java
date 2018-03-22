package com.example.ritasantiago.vetcare.petlist.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.db.entity.Procedure;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters.PetRecordAdapter;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters.ProcedureAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.PROCEDURE_DATE_KEY;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.PROCEDURE_DOCTOR_KEY;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.PROCEDURE_KEY;


public class PetRecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;
    private List<Procedure> procedures = new ArrayList<>();
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private Animal animal;

    private void getProcedures (final String animalName){
        db = FirebaseFirestore.getInstance();
        db.collection("Historics").document(animalName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    procedures.add(new Procedure(doc.get(PROCEDURE_KEY).toString()));
                }
                mAdapter = new PetRecordAdapter(procedures);
                recyclerView.setAdapter(mAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pet_record, container, false);
        Bundle args = getArguments();
        this.animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_historic);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getProcedures(animal.name);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Pet Record");
    }
}
