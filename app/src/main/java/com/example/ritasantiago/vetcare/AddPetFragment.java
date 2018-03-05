package com.example.ritasantiago.vetcare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.firebase.DatabaseActions;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class AddPetFragment extends Fragment {

    DatabaseActions database;
    FirebaseFirestore db;
    TextView txtDisplay;
    TextView message;
    Button save;

    //animal's doc keys
    public static final String NAME_KEY = "Name";
    public static final String SEX_KEY = "Sex";
    public static final String SPECIE_KEY = "Specie";
    public static final String WEIGHT_KEY = "Weight";
    public static final String DATE_KEY = "Date of Birth";
    public static final String BREED_KEY = "Breed";
    public static final String COAT_KEY = "Coat";
    public static final String OWNER_NAME = "Owner's Name";

    //owner's doc keys
    public static final String PHONE_KEY = "Phone";
    public static final String ADDRESS_KEY = "Address";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_add_pet, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Pet");
    }
}
