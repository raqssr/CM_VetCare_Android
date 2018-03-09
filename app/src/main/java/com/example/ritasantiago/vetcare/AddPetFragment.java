package com.example.ritasantiago.vetcare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.firebase.DatabaseActions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by raquelramos on 04-03-2018.
 */

public class AddPetFragment extends Fragment {

    public static final String DEBUG_TAG = "AddPetFragment";
    DatabaseActions database;
    FirebaseFirestore db;
    TextView txtDisplay;
    TextView message;
    Button save;
    RadioGroup sexOption;
    View radioButtonSelected;
    RadioButton rb;
    EditText nameView, weightView, specieView, dateView, breedView, coatView, ownerView, ownerPhoneView, ownerAddrView;

    //animal's doc keys
    public static final String NAME_KEY = "Name";
    public static final String SEX_KEY = "Sex";
    public static final String IMAGE_KEY = "image";
    public static final String SPECIE_KEY = "Specie";
    public static final String WEIGHT_KEY = "Weight";
    public static final String DATE_KEY = "Date of Birth";
    public static final String BREED_KEY = "Breed";
    public static final String COAT_KEY = "Coat";
    public static final String OWNER_NAME = "Owner's Name";

    //owner's doc keys
    public static final String PHONE_KEY = "Phone";
    public static final String ADDRESS_KEY = "Address";

    String sexOptionChosen = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private FirebaseDatabase mDatabase;
    private Uri filePath;
    String imageStr;
    Bitmap imageBitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //returning our layout file
        View rootView = inflater.inflate(R.layout.fragment_add_pet, container, false);
        db = FirebaseFirestore.getInstance();
        sexOption = (RadioGroup) rootView.findViewById(R.id.radio_group);
        nameView = (EditText) rootView.findViewById(R.id.animal_name);
        weightView = (EditText) rootView.findViewById(R.id.animal_weight);
        specieView = (EditText) rootView.findViewById(R.id.animal_specie);
        dateView = (EditText) rootView.findViewById(R.id.animal_dob);
        breedView = (EditText) rootView.findViewById(R.id.animal_breed);
        coatView = (EditText) rootView.findViewById(R.id.animal_coat);
        ownerView = (EditText) rootView.findViewById(R.id.animal_ownerName);
        ownerPhoneView = (EditText) rootView.findViewById(R.id.animal_ownerPhone);
        ownerAddrView = (EditText) rootView.findViewById(R.id.animal_ownerAddress);
        mDatabase = FirebaseDatabase.getInstance();

        Button uploadButton = (Button) rootView.findViewById(R.id.upload_photo);
        if (!getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getActivity().getApplicationContext(), "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        }
        uploadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dispatchTakePictureIntent();
            }
        });


        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.btn_saveAnimal);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createAnimal(v);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }
    }

    private void createAnimal(View view) {
        int selectedId = sexOption.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButtonSelected = sexOption.findViewById(selectedId);
        int radioId = sexOption.indexOfChild(radioButtonSelected);
        rb = (RadioButton) sexOption.getChildAt(radioId);
        sexOptionChosen = (String) rb.getText();

        String nameText = nameView.getText().toString();
        String sexText = sexOptionChosen;
        Double weightText = Double.parseDouble(weightView.getText().toString());
        String specieText = specieView.getText().toString();
        String dateText = dateView.getText().toString();
        String breedText = breedView.getText().toString();
        String coatText = coatView.getText().toString();
        String ownerText = ownerView.getText().toString();

        //owners info
        int ownerPhoneText = Integer.parseInt(ownerPhoneView.getText().toString());
        String ownerAddrText = ownerAddrView.getText().toString();

        Map<String, Object> newAnimal = new HashMap<>();
        newAnimal.put(NAME_KEY, nameText);
        newAnimal.put(SEX_KEY, sexText);
        newAnimal.put(WEIGHT_KEY, weightText);
        newAnimal.put(SPECIE_KEY, specieText);
        newAnimal.put(DATE_KEY, dateText);
        newAnimal.put(BREED_KEY, breedText);
        newAnimal.put(COAT_KEY, coatText);
        newAnimal.put(OWNER_NAME, ownerText);

        if (imageBitmap != null)
        {
            Log.i("AddPetFragment", "entrei no if do bitmap");
            final DatabaseReference log = mDatabase.getReference("Animals").push();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            imageStr = Base64.encodeToString(byteArray, Base64.NO_WRAP | Base64.URL_SAFE);
            Log.i("imageStr", imageStr);
            //log.child("image").setValue(imageStr);
            Toast.makeText(getActivity().getApplicationContext(),"Photo uploaded with success.", Toast.LENGTH_SHORT).show();
        }

        newAnimal.put(IMAGE_KEY, imageStr);

        Map<String, Object> newOwner = new HashMap<>();
        newOwner.put(NAME_KEY, ownerText);
        newOwner.put(PHONE_KEY, ownerPhoneText);
        newOwner.put(ADDRESS_KEY, ownerAddrText);

        Date dateIn = Calendar.getInstance().getTime(); //dateIn for internment

        db.collection("Animals").document(nameText).set(newAnimal)
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

        db.collection("Owners").document(ownerText).set(newOwner).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        Context context = getActivity().getApplicationContext();

        Toast.makeText(context,"Pet added with success!", Toast.LENGTH_SHORT).show();

        getFragmentManager().popBackStackImmediate();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Log.i("dispatch", "fez o dispatch");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
