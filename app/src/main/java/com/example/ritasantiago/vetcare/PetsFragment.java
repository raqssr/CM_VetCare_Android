package com.example.ritasantiago.vetcare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ritasantiago.vetcare.firebase.Animal;
import com.example.ritasantiago.vetcare.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class PetsFragment extends Fragment {

    private List<Animal> persons;



    private void initializeData(){
        persons = new ArrayList<>();

        /*
        * final List<String> infos = new ArrayList<>();
        db.collection("Animals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot query = task.getResult();
                    List<DocumentSnapshot> data = query.getDocuments();
                    infos.add(data.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });

        persons.add(new Animal(infos.toString());
        * */
        persons.add(new Animal("Emma Wilson", "23 years old", "ss","s"));
        persons.add(new Animal("Lavery Maiss", "25 years old", "ss", "ss"));
        persons.add(new Animal("Lillie Watts", "35 years old", "ss", "ss"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_pets, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        initializeData();
        RVPetAdapter adapter = new RVPetAdapter(persons);
        rv.setAdapter(adapter);

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.button_addPet);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AddPetFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("PetsFragment");
    }
}
