package com.example.ritasantiago.vetcare;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.firebase.DatabaseActions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
        View rootView = inflater.inflate(R.layout.fragment_pets, container, false);

        db = FirebaseFirestore.getInstance();
        save = (Button) rootView.findViewById(R.id.btn_saveAnimal);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnimal(v);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Pet");
    }

    private void createAnimal(View view) {
        EditText nameView = (EditText) view.findViewById(R.id.animal_name);
        //EditText sexView = (EditText) view.findViewById(R.id.animal_sex);
        //RadioGroup sexOption = (RadioGroup) view.findViewById(R.id.radio_group);
        RadioButton maleOption = (RadioButton) view.findViewById(R.id.male);
        RadioButton femaleOption = (RadioButton) view.findViewById(R.id.female);
        String sexOptionChosen;
        if (maleOption.isChecked())
        {
            sexOptionChosen = "Male";
        }
        else
        {
            sexOptionChosen = "Female";
        }
        EditText weightView = (EditText) view.findViewById(R.id.animal_weight);
        EditText specieView = (EditText) view.findViewById(R.id.animal_specie);
        EditText dateView = (EditText) view.findViewById(R.id.animal_dob);
        EditText breedView = (EditText) view.findViewById(R.id.animal_breed);
        EditText coatView = (EditText) view.findViewById(R.id.animal_coat);
        EditText ownerView = (EditText) view.findViewById(R.id.animal_ownerName);

        String nameText = nameView.getText().toString();
        String sexText = sexOptionChosen;
        Double weightText = Double.parseDouble(weightView.getText().toString());
        String specieText = specieView.getText().toString();
        String dateText = dateView.getText().toString();
        String breedText = breedView.getText().toString();
        String coatText = coatView.getText().toString();
        String ownerText = ownerView.getText().toString();

        Map<String, Object> newAnimal = new HashMap<>();
        newAnimal.put(NAME_KEY, nameText);
        newAnimal.put(SEX_KEY, sexText);
        newAnimal.put(WEIGHT_KEY, weightText);
        newAnimal.put(SPECIE_KEY, specieText);
        newAnimal.put(DATE_KEY, dateText);
        newAnimal.put(BREED_KEY, breedText);
        newAnimal.put(COAT_KEY, coatText);
        newAnimal.put(OWNER_NAME, ownerText);

        db.collection("Animals").document("Animal").set(newAnimal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Animal Registered");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.toString());
                    }
                });

        //createOwner(view);
    }

    private void createOwner(View view){
        EditText ownerView = (EditText) view.findViewById(R.id.animal_ownerName);
        EditText ownerPhoneView = (EditText) view.findViewById(R.id.animal_ownerPhone);
        EditText ownerAddrView = (EditText) view.findViewById(R.id.animal_ownerAddress);

        String ownerText = ownerView.getText().toString();
        int ownerPhoneText = Integer.parseInt(ownerPhoneView.getText().toString());
        String ownerAddrText = ownerAddrView.getText().toString();

        Map<String, Object> newOwner = new HashMap<>();
        newOwner.put(NAME_KEY, ownerText);
        newOwner.put(PHONE_KEY, ownerPhoneText);
        newOwner.put(ADDRESS_KEY, ownerAddrText);

        db.collection("Animals").document("Owner").set(newOwner).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Owner Registered");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }
}
