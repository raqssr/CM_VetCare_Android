package com.example.ritasantiago.vetcare.petlist;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.DatabaseActions;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.AddHospitalisationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import static com.example.ritasantiago.vetcare.db.entity.FirebaseFields.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by raquelramos on 04-03-2018.
 */

public class AddPetFragment extends Fragment {

    public static final String DEBUG_TAG = "AddPetFragment";
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    DatabaseActions database;
    FirebaseFirestore db;
    TextView txtDisplay;
    TextView message;
    Button save;
    RadioGroup sexOption;
    View radioButtonSelected;
    RadioButton rb;
    EditText nameView, weightView, specieView, dateView, breedView, coatView, ownerView, ownerPhoneView, ownerAddrView;

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
        nameView = (EditText) rootView.findViewById(R.id.g_name);
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

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.btn_nextFrag);
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

        final Map<String, Object> newAnimal = new HashMap<>();
        newAnimal.put(NAME, nameText);
        newAnimal.put(SEX, sexText);
        newAnimal.put(WEIGHT, weightText);
        newAnimal.put(SPECIE,specieText);
        newAnimal.put(DOB, dateText);
        newAnimal.put(BREED, breedText);
        newAnimal.put(COAT, coatText);


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

        newAnimal.put(IMAGE_ID, imageStr);

        Map<String, Object> newOwner = new HashMap<>();
        newOwner.put(OWNER_NAME, ownerText);
        newOwner.put(PHONE_KEY, ownerPhoneText);
        newOwner.put(ADDRESS_KEY, ownerAddrText);

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

        DocumentReference ownerReference = db.collection("Owners").document(ownerText);
        newAnimal.put(OWNER_NAME, ownerReference.getId());


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

        final DocumentReference animalReference = db.collection("Animals").document(nameText);

        final Map<String, Object> newMed = new HashMap<>();
        final String medicine = randomMedicine();
        final Map<String, Object> medicines = new HashMap<>();
        Double dosage = roundDoubles(randomDosage());
        int frequency = randomFrequency();
        int totalDays = randomDays();
        medicines.put(DOSAGE_KEY, dosage);
        medicines.put(FREQUENCY_KEY, frequency);
        medicines.put(TOTALDAYS_KEY, totalDays);
        final Map<String, Map<String, Object>> animalMedicines = new HashMap<>();
        animalMedicines.put(medicine,medicines);
        newMed.put(animalReference.getId(), animalMedicines);

        db.collection("Medicines").document(medicine).set(newMed)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Medicine Registered");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.toString());
                    }
                });


        for(int i = 1; i <= numberProcedures(); i++) {
            Map<String, Object> newProc = new HashMap<>();
            String procedure = randomProcedure();
            Map<String, String> procedures = new HashMap<>();
            String date = randomDate();
            procedures.put(PROCEDURE_KEY, procedure);
            procedures.put(PROCEDURE_DATE_KEY, date);
            Map<String,Map<String,String>> animalProcedures = new HashMap<>();
            animalProcedures.put(PROCEDURE_KEY,procedures);
            newProc.put(animalReference.getId(), animalProcedures);

            db.collection("Procedures").document(procedure).set(newProc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "Procedure Registered");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", e.toString());
                        }
                    });
        }

        for(int i = 1; i<= numberRegulars(); i++){
            Map<String, Object> newHist = new HashMap<>();
            String regularProcedure = randomRegular();
            String date = randomDate();
            Map<String,String> procedures = new HashMap<>();
            procedures.put(regularProcedure,date);
            newHist.put(PROCEDURE_KEY,procedures);

            db.collection("Historics").document(animalReference.getId()).set(newHist)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "Historic Registered");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", e.toString());
                        }
                    });
        }



        //Context context = getActivity().getApplicationContext();

        //Toast.makeText(context,"Pet added with success!", Toast.LENGTH_SHORT).show();

        //getFragmentManager().popBackStackImmediate();
        goToNextFragment();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Log.i("dispatch", "fez o dispatch");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private Double randomDosage(){
        Random r = new Random();
        int tmp = 100 + (100-2);
        return tmp * r.nextDouble();
    }

    private Double roundDoubles(Double value){
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private int randomFrequency(){
        Random r = new Random();
        return r.nextInt(7-1)+1;
    }

    private int randomDays(){
        Random r = new Random();
        return r.nextInt(60-7)+7;
    }

    private String randomMedicine(){
        String [] medicines = {"Banacep", "Dermocanis Alercaps", "Fortekor", "Ecuphar", "Omnipharm", "Intervet International", "Sogeval"};
        Random r = new Random();
        int idx = r.nextInt(medicines.length-1)+1;
        return medicines[idx];
    }

    private String randomDate(){
        String [] dates = {"23/03/2018", "24/03/2018", "27/03/2018", "26/03/2018", "30/03/2018", "02/04/2018", "05/04/2018", "03/04/2018", "04/04/2018"};
        Random r = new Random();
        int idx = r.nextInt(dates.length);
        return dates[idx];
    }

    private String randomProcedure(){
        String [] procedures = {"Análises", "Tratamento Acupuntura", "Ecocardiograma", "Ecografia Abdominal", "Raio-x", "Vacina Parvovirose", "Vacina Tosse", "Vacina Leucemia"};
        Random r = new Random();
        int idx = r.nextInt(procedures.length-1)+1;
        return procedures[idx];
    }

    private int numberProcedures(){
        String [] procedures = {"Análises", "Tratamento Acupuntura", "Ecocardiograma", "Ecografia Abdominal", "Raio-x", "Vacina Parvovirose", "Vacina Tosse", "Vacina Leucemia"};
        Random r = new Random();
        return r.nextInt(procedures.length-1)+1;
    }

    private String randomRegular(){
        String [] regular = {"Desparasitação", "Castração", "Tosquia", "Consulta Rotina", "Esterilização", "Microchip", "Vacina Raiva"};
        Random r = new Random();
        int idx = r.nextInt(regular.length-1)+1;
        return regular[idx];
    }

    private int numberRegulars(){
        String [] regular = {"Desparasitação", "Castração", "Tosquia", "Consulta Rotina", "Esterilização", "Microchip", "Vacina Raiva"};
        Random r = new Random();
        return r.nextInt(regular.length-1)+1;
    }

    public void goToNextFragment(){
        Fragment fragment = new AddHospitalisationFragment();
        Bundle bundle = new Bundle();
        String nameText = nameView.getText().toString();
        bundle.putSerializable(ANIMAL_BUNDLE_KEY, nameText);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
