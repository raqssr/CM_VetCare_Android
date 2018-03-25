package com.example.ritasantiago.vetcare.petlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.example.ritasantiago.vetcare.interfaces.PetClickListener;
import com.example.ritasantiago.vetcare.petlist.adapters.RVPetAdapter;
import com.example.ritasantiago.vetcare.petlist.profile.ProfilePetFragment;
import com.example.ritasantiago.vetcare.petlist.vitalsigns.VitalSignsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class PetsFragment extends Fragment implements PetClickListener {

    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    private RVPetAdapter adapter;
    private FirebaseFirestore db;

    private void initializeData(){
        db = FirebaseFirestore.getInstance();
        db.collection("Animals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> data = query.getDocuments();
                for (int i = 0; i < data.size(); i++)
                {
                    byte imagem[] = Base64.decode(data.get(i).get(IMAGE_ID).toString(),Base64.NO_WRAP | Base64.URL_SAFE);
                    Bitmap bmp = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                    adapter.addAnimal(new Animal(data.get(i).get(NAME).toString(),
                                                 data.get(i).get(SEX).toString(),
                                                 bmp,
                                                 data.get(i).get(WEIGHT).toString(),
                                                 data.get(i).get(SPECIE).toString(),
                                                 data.get(i).get(DOB).toString(),
                                                 data.get(i).get(BREED).toString(),
                                                 data.get(i).get(COAT).toString(),
                                                 data.get(i).get(OWNER_NAME).toString()));

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_pets, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new RVPetAdapter(this);
        rv.setAdapter(adapter);

        initializeData();


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

    @Override
    public void onPetClick(Animal animal) {
        Fragment fragment = new ProfilePetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onPetSignalClick(Animal animal)
    {
        Fragment fragment = new VitalSignsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
