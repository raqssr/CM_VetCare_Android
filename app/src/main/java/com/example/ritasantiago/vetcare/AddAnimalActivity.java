package com.example.ritasantiago.vetcare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.firebase.DatabaseActions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class AddAnimalActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        db = FirebaseFirestore.getInstance();
        //txtDisplay = (TextView) findViewById(R.id.textDisplay);

        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnimal(v);
            }
        });

    }

    private void createAnimal(View view){
        EditText nameView = (EditText) findViewById(R.id.name);
        EditText sexView = (EditText) findViewById(R.id.sex);
        EditText weightView = (EditText) findViewById(R.id.weight);
        EditText specieView = (EditText) findViewById(R.id.specie);
        EditText dateView = (EditText) findViewById(R.id.dateOfBirth);
        EditText breedView = (EditText) findViewById(R.id.breed);
        EditText coatView = (EditText) findViewById(R.id.coat);
        EditText ownerView = (EditText) findViewById(R.id.owner_name);

        String nameText = nameView.getText().toString();
        String sexText = sexView.getText().toString();
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

        //createOwner();

    }

    private void createOwner(){
        EditText ownerView = (EditText) findViewById(R.id.owner_name);
        EditText ownerPhoneView = (EditText) findViewById(R.id.owner_phone);
        EditText ownerAddrView = (EditText) findViewById(R.id.owner_address);

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