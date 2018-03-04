package com.example.ritasantiago.vetcare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.firebase.Animal;
import com.example.ritasantiago.vetcare.firebase.Owner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ritasantiago on 03-03-2018.
 */

public class AddAnimalActivity extends AppCompatActivity {

    private static final String TAG = AddAnimalActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputSex, inputWeight, inputSpecie, inputDateOfBirth, inputBreed,
            inputCoat, inputOwnerName, inputOwnerPhone, inputOwnerAddress; //missing picture
    private Button btnSave;
    FirebaseFirestore db;
    private String animalId, ownerId;

    @Override
    protected void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_animal);

        txtDetails = (TextView) findViewById(R.id.animal_txt);
        inputName = (EditText) findViewById(R.id.name);
        inputSex = (EditText) findViewById(R.id.sex);
        inputWeight = (EditText) findViewById(R.id.weight);
        inputSpecie = (EditText) findViewById(R.id.specie);
        inputDateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        inputBreed = (EditText) findViewById(R.id.breed);
        inputCoat = (EditText) findViewById(R.id.coat);
        inputOwnerName = (EditText) findViewById(R.id.owner_name);
        //aqui temos de ver como fazemos: se drop down ou assim, para já fica tudo seguido
        inputOwnerPhone = (EditText) findViewById(R.id.owner_phone);
        inputOwnerAddress = (EditText) findViewById(R.id.owner_address);
        btnSave = (Button) findViewById(R.id.btn_save);

        db = FirebaseFirestore.getInstance();

        //save animal
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String sex = inputSex.getText().toString();
                Double weight = Double.parseDouble(inputWeight.getText().toString());
                String specie = inputSpecie.getText().toString();
                String dateOfBirth = inputDateOfBirth.getText().toString();
                String breed = inputBreed.getText().toString();
                String coat = inputCoat.getText().toString();
                String ownerName = inputOwnerName.getText().toString();
                int ownerPhone = Integer.parseInt(inputOwnerPhone.getText().toString());
                String ownerAddress = inputOwnerAddress.getText().toString();

                createAnimal(name,sex,weight,specie,dateOfBirth,breed,coat,ownerName);
                createOwner(ownerName,ownerPhone,ownerAddress);
            }
        });

        toggleButton();
    }

    private void toggleButton(){
        if(TextUtils.isEmpty(animalId)){
            btnSave.setText("Save");
        }
        else{
            btnSave.setText("Update");
        }

    }

    private void createAnimal(String name, String sex, double weight, String specie, String dateOfBirth, String breed, String coat, String ownerName){
        final Map<Object, Object> newAnimal = new HashMap<>();
        newAnimal.put(name,name);
        newAnimal.put(sex,sex);
        newAnimal.put(weight,weight);
        newAnimal.put(specie,specie);
        newAnimal.put(dateOfBirth,dateOfBirth);
        newAnimal.put(breed,breed);
        newAnimal.put(coat,coat);
        newAnimal.put(ownerName,ownerName);

        db.collection("Animals").document("Animal").set(newAnimal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddAnimalActivity.this, "Animal Registered",
                        Toast.LENGTH_SHORT).show();

                txtDetails.setText(newAnimal.toString());
                //clear edit text
                inputName.setText("");
                inputSex.setText("");
                inputBreed.setText("");
                inputCoat.setText("");
                inputDateOfBirth.setText("");
                inputOwnerName.setText("");
                inputSpecie.setText("");
                inputWeight.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddAnimalActivity.this, "ERROR" + e.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.toString());
            }
        });
    }

    private void createOwner(final String name, int phone, String address){
        final Map<Object, Object> newOwner = new HashMap<>();
        newOwner.put(name,name);
        newOwner.put(phone,phone);
        newOwner.put(address,address);

        db.collection("Animals").document("Owner").set(newOwner).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddAnimalActivity.this, "Owner Registered",
                        Toast.LENGTH_SHORT).show();

                txtDetails.setText(newOwner.toString());

                //clear edit text
                inputOwnerAddress.setText("");
                inputOwnerName.setText("");
                inputOwnerPhone.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddAnimalActivity.this, "ERROR" + e.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.toString());
            }
        });
    }

}
