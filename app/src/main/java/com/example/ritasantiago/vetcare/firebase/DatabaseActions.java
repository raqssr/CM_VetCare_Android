package com.example.ritasantiago.vetcare.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
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
    public static final String COMMENTS_KEY = "Comments";
    public static final String REASON_KEY = "Reason of Internment";
    public static final String DOCTOR_KEY = "Doctor";
    public static final String PROCEDURE_KEY = "Procedure";
    public static final String MEDICINE_KEY = "Medicine";
    public static final String DATE_IN_KEY = "Date In";

    //procedure's doc keys
    public static final String PROCEDURE_DATE_KEY = "Procedure Date";

    //medicine's doc keys
    public static final String DOSAGE_KEY = "Dosage";


    //reference for documents
    private DocumentReference animalDoc = db.getInstance().document("Animals/Animal");
    private DocumentReference ownerDoc = db.getInstance().document("Animals/Owner");
    private DocumentReference internmentDoc = db.getInstance().document("Animals/Internment");
    private DocumentReference historicDoc = db.getInstance().document("Animals/Historic");
    private DocumentReference medicineDoc = db.getInstance().document("Animals/Medicine");
    private DocumentReference procedureDoc = db.getInstance().document("Animals/Procedure");


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
    }

    public void createOwner(String ownerText, int ownerPhoneText, String ownerAddrText){

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

    public void createInternment(String name, String comments, String reason, String doctor, String procedure, String medicine, String dateIn){
        Map<String, String> newInter = new HashMap<>();
        newInter.put(NAME_KEY,name);
        newInter.put(COMMENTS_KEY,comments);
        newInter.put(REASON_KEY,reason);
        newInter.put(DOCTOR_KEY,doctor);
        newInter.put(PROCEDURE_KEY,procedure);
        newInter.put(MEDICINE_KEY,medicine);
        newInter.put(DATE_IN_KEY,dateIn);

        internmentDoc.set(newInter).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Internment Created!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    public void createHistoric(String name, String procedure){
        Map<String, String> newHist = new HashMap<>();
        newHist.put(NAME_KEY,name);
        newHist.put(PROCEDURE_KEY,procedure);

        historicDoc.set(newHist).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Internment Created!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    public void createProcedure(String procedure, String date){
        Map<String, String> newProc = new HashMap<>();
        newProc.put(PROCEDURE_KEY,procedure);
        newProc.put(PROCEDURE_DATE_KEY,date);

        procedureDoc.set(newProc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Internment Created!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    public void createMedicine(String medicine, double dosage){
        Map<String, Object> newMed = new HashMap<>();
        newMed.put(MEDICINE_KEY,medicine);
        newMed.put(DOSAGE_KEY,dosage);

        medicineDoc.set(newMed).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Internment Created!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    //read functions
    public String readSingleAnimal(){
        final StringBuilder fields = new StringBuilder("");
        animalDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
            }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", e.toString());
                }
             });
        return fields.toString();
    }

    public String readSingleOwner(){
        final StringBuilder fields = new StringBuilder("");
        ownerDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    fields.append("\nName: ").append(doc.get(OWNER_NAME));
                    fields.append("\nPhone: ").append(doc.get(PHONE_KEY));
                    fields.append("\nAddress: ").append(doc.get(ADDRESS_KEY));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
        return fields.toString();
    }

    public String readInternment(){
        final StringBuilder fields = new StringBuilder("");
        internmentDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
        return fields.toString();
    }

    public String readHistoric(){
        final StringBuilder fields = new StringBuilder("");
        historicDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    fields.append("\nName: ").append(doc.get(NAME_KEY));
                    fields.append("\nProcedure: ").append(doc.get(PROCEDURE_KEY));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
        return fields.toString();
    }

    public String readProcedure(){
        final StringBuilder fields = new StringBuilder("");
        procedureDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    fields.append("\nName: ").append(doc.get(PROCEDURE_KEY));
                    fields.append("\nDate: ").append(doc.get(PROCEDURE_DATE_KEY));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
        return fields.toString();
    }

    public String readMedicine(){
        final StringBuilder fields = new StringBuilder("");
        medicineDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    fields.append("\nName: ").append(doc.get(MEDICINE_KEY));
                    fields.append("\nDosage: ").append(doc.get(DOSAGE_KEY));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
        return fields.toString();
    }

    //update functions
    public void updateAnimalWeight(double weight){
        animalDoc.update(WEIGHT_KEY, weight).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Animal Updated!");
            }
        });
    }

    public void updateOwnersInfo(int phone, String address){
        ownerDoc.update(PHONE_KEY,phone);
        ownerDoc.update(ADDRESS_KEY,address).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Owner Updated!");
            }
        });
    }

    public void updateInternment(String procedure, String medicine){
        internmentDoc.update(PROCEDURE_KEY,procedure);
        internmentDoc.update(MEDICINE_KEY,medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","Internment Updated");
            }
        });
    }

    public void updateMedicine(double dosage){
        historicDoc.update(DOCTOR_KEY,dosage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Dosage Updated!");
            }
        });
    }

    //delete functions
    public void deleteAnimal(){
        animalDoc.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Animal deleted!");
            }
        });
    }

    public void deleteInternment(){
        internmentDoc.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Animal deleted!");
            }
        });
    }
}
