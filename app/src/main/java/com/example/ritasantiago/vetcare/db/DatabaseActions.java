package com.example.ritasantiago.vetcare.db;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ritasantiago on 04-03-2018.
 */

public class DatabaseActions {

    FirebaseFirestore db; //firestore database

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

    //internment's doc keys
    public static final String REASON_KEY = "Motive";
    public static final String DOCTOR_KEY = "Veterinarian";
    public static final String COMMENTS_KEY = "Observations";
    public static final String DATE_IN_KEY = "Entry date";
    public static final String PROCEDURE_KEY = "Procedure";

    //procedure's doc keys
    public static final String PROCEDURE_DATE_KEY = "Procedure Date";

    //medicine's doc keys
    public static final String DOSAGE_KEY = "Dosage";
    public static final String FREQUENCY_KEY = "Frequency";
    public static final String TOTALDAYS_KEY = "Total Days";
    public static final String MEDICINE_KEY = "Medicine";


    //add functions
    public void createAnimal(String nameText, String sexText, double weightText, String specieText, String dateText, String breedText, String coatText, String ownerText){
        Map<String, Object> newAnimal = new HashMap<>();
        newAnimal.put(NAME_KEY, nameText);
        newAnimal.put(SEX_KEY, sexText);
        newAnimal.put(WEIGHT_KEY, weightText);
        newAnimal.put(SPECIE_KEY, specieText);
        newAnimal.put(DATE_KEY, dateText);
        newAnimal.put(BREED_KEY, breedText);
        newAnimal.put(COAT_KEY, coatText);
        newAnimal.put(OWNER_NAME, ownerText);

        db.collection("Animals").document(nameText).set(newAnimal)
                .addOnSuccessListener(aVoid -> Log.d("TAG", "Animal Registered"))
                .addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    public void createOwner(String ownerText, int ownerPhoneText, String ownerAddrText){

        Map<String, Object> newOwner = new HashMap<>();
        newOwner.put(NAME_KEY, ownerText);
        newOwner.put(PHONE_KEY, ownerPhoneText);
        newOwner.put(ADDRESS_KEY, ownerAddrText);

        db.collection("Owners").document(ownerText).set(newOwner)
                .addOnSuccessListener(aVoid -> Log.d("TAG", "Owner Registered")).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    public void createInternment(String name, String comments, String reason, String doctor, String procedure, String medicine){
        Map<String, Object> newInter = new HashMap<>();
        newInter.put(NAME_KEY,name);
        newInter.put(COMMENTS_KEY,comments);
        newInter.put(REASON_KEY,reason);
        newInter.put(DOCTOR_KEY,doctor);

        Date dateIn = Calendar.getInstance().getTime();

        newInter.put(DATE_IN_KEY,dateIn);

        db.collection("Internments").document(name).set(newInter)
                .addOnSuccessListener(aVoid -> Log.d("TAG", "Internment Created!")).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    public void createHistoric(String name, List<String> procedures){
        Map<String, Object> newHist = new HashMap<>();
        newHist.put(NAME_KEY,name);
        newHist.put(PROCEDURE_KEY,procedures);

        db.collection("Historic").document(name).set(newHist).addOnSuccessListener(aVoid -> Log.d("TAG", "Internment Created!")).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    public void createProcedure(String procedure, String date){
        Map<String, String> newProc = new HashMap<>();
        newProc.put(PROCEDURE_KEY,procedure);
        newProc.put(PROCEDURE_DATE_KEY,date);

        db.collection("Procedures").document(procedure).set(newProc).addOnSuccessListener(aVoid -> Log.d("TAG", "Internment Created!")).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    public void createMedicine(String medicine, double dosage, int frequency, int totalDays){
        Map<String, Object> newMed = new HashMap<>();
        newMed.put(MEDICINE_KEY,medicine);
        newMed.put(DOSAGE_KEY,dosage);
        newMed.put(FREQUENCY_KEY,frequency);
        newMed.put(TOTALDAYS_KEY,totalDays);

        db.collection("Medicines").document(medicine).set(newMed).addOnSuccessListener(aVoid -> Log.d("TAG", "Internment Created!")).addOnFailureListener(e -> Log.d("TAG", e.toString()));
    }

    //read functions
    public List<String> readAllAnimals(){
        final List<String> infos = new ArrayList<>();
        db.collection("Animals").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> data = query.getDocuments();
                infos.add(data.toString());
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return infos;
    }
    public String readSingleAnimal(String name){
        final StringBuilder fields = new StringBuilder("");
        db.collection("Animals").document(name).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                fields.append("\nName: ").append(doc.get(NAME_KEY));
                fields.append("\nSex: ").append(doc.get(SEX_KEY));
                fields.append("\nWeight: ").append(doc.get(WEIGHT_KEY));
                fields.append("\nSpecie: ").append(doc.get(SPECIE_KEY));
                fields.append("\nDate of Birth: ").append(doc.get(DATE_KEY));
                fields.append("\nBreed: ").append(doc.get(BREED_KEY));
                fields.append("\nCoat: ").append(doc.get(COAT_KEY));
                fields.append("\nOwner's Name: ").append(doc.get(OWNER_NAME));
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return fields.toString();
    }

    public String readSingleOwner(String name){
        final StringBuilder fields = new StringBuilder("");
        db.collection("Owners").document(name).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                fields.append("\nName: ").append(doc.get(OWNER_NAME));
                fields.append("\nPhone: ").append(doc.get(PHONE_KEY));
                fields.append("\nAddress: ").append(doc.get(ADDRESS_KEY));
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return fields.toString();
    }

    public String readInternment(String name){
        final StringBuilder fields = new StringBuilder("");
        db.collection("Internments").document(name).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                fields.append("\nName: ").append(doc.get(NAME_KEY));
                fields.append("\nComments: ").append(doc.get(COMMENTS_KEY));
                fields.append("\nReason of Internment: ").append(doc.get(REASON_KEY));
                fields.append("\nDoctor: ").append(doc.get(DOCTOR_KEY));
                fields.append("\nProcedure: ").append(doc.get(PROCEDURE_KEY));
                fields.append("\nMedicine: ").append(doc.get(MEDICINE_KEY));
                fields.append("\nDate In: ").append(doc.get(DATE_IN_KEY));
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return fields.toString();
    }

    public String readHistoric(String name){
        final StringBuilder fields = new StringBuilder("");
        db.collection("Historic").document(name).get().addOnCompleteListener(task -> {
        if(task.isSuccessful()){
            DocumentSnapshot doc = task.getResult();
            fields.append("\nName: ").append(doc.get(NAME_KEY));
            fields.append("\nProcedure: ").append(doc.get(PROCEDURE_KEY));
        }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return fields.toString();
    }

    public String readProcedure(String name){
        final StringBuilder fields = new StringBuilder("");
        db.collection("Procedures").document(name).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                fields.append("\nName: ").append(doc.get(PROCEDURE_KEY));
                fields.append("\nDate: ").append(doc.get(PROCEDURE_DATE_KEY));
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return fields.toString();
    }

    public String readMedicine(String name){
        final StringBuilder fields = new StringBuilder("");
        db.collection("Medicines").document(name).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot doc = task.getResult();
                fields.append("\nName: ").append(doc.get(MEDICINE_KEY));
                fields.append("\nDosage: ").append(doc.get(DOSAGE_KEY));
            }
        }).addOnFailureListener(e -> Log.d("TAG", e.toString()));
        return fields.toString();
    }

    //update functions
    public void updateAnimalWeight(String name, double weight){
        db.collection("Animals").document(name).update(WEIGHT_KEY, weight).addOnSuccessListener(aVoid -> Log.d("TAG", "Animal Updated!"));
    }

    public void updateOwnersInfo(String name, int phone, String address){
        db.collection("Owners").document(name).update(PHONE_KEY,phone);
        db.collection("Owners").document(name).update(ADDRESS_KEY,address).addOnSuccessListener(aVoid -> Log.d("TAG", "Owner Updated!"));
    }

    public void updateInternment(String name, String procedure, String medicine){
        db.collection("Internments").document(name).update(PROCEDURE_KEY,procedure);
        db.collection("Internments").document(name).update(MEDICINE_KEY,medicine).addOnSuccessListener(aVoid -> Log.d("TAG","Internment Updated"));
    }

    public void updateMedicine(String name, int frequency, int totalDays){
        db.collection("Medicines").document(name).update(FREQUENCY_KEY, frequency);
        db.collection("Medicines").document(name).update(TOTALDAYS_KEY, totalDays).addOnSuccessListener(aVoid -> Log.d("TAG", "Dosage Updated!"));
    }

    //delete functions
    public void deleteAnimal(String name){
        db.collection("Animals").document(name).delete().addOnSuccessListener(aVoid -> Log.d("TAG", "Animal deleted!"));
    }

    public void deleteInternment(String name){
        db.collection("Internments").document(name).delete().addOnSuccessListener(aVoid -> Log.d("TAG", "Animal deleted!"));
    }
}