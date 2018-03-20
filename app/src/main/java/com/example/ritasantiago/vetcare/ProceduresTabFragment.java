package com.example.ritasantiago.vetcare;

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

import com.example.ritasantiago.vetcare.firebase.Animal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.DOSAGE_KEY;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.FREQUENCY_KEY;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.NAME;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.PROCEDURE_DATE_KEY;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.PROCEDURE_KEY;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class ProceduresTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;
    private List<String> procedures = new ArrayList<>();
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private Animal animal;

    private void getProcedures (final String animalName){
        db = FirebaseFirestore.getInstance();
        db.collection("Procedures").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot query = task.getResult();
                    List<DocumentSnapshot> data = query.getDocuments();
                    for (int i = 0; i < data.size(); i++) {
                        if(data.get(i).get("Animal Associated").toString().equals(animalName)){
                            procedures.add(data.get(i).get(PROCEDURE_KEY).toString());
                            procedures.add(data.get(i).get(PROCEDURE_DATE_KEY).toString());
                        }
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
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_procedures_tab, container, false);
        Bundle args = getArguments();
        this.animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_procedures);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        getProcedures("Kiko");

        mAdapter = new ProcedureAdapter(procedures);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("ProceduresTabFragment");
    }
}
