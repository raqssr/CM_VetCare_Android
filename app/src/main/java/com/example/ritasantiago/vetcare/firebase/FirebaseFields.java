package com.example.ritasantiago.vetcare.firebase;

/**
 * Created by raquelramos on 06-03-2018.
 */

public class FirebaseFields {
    //fields for animal's doc
    public static final String NAME = "Name";
    public static final String SEX = "Sex";
    public static final String WEIGHT = "Weight";
    public static final String SPECIE = "Specie";
    public static final String DOB = "Date of Birth";
    public static final String BREED = "Breed";
    public static final String COAT = "Coat";
    public static final String OWNER_NAME = "Owner's Name";
    public static final String PHONE_KEY = "Phone";
    public static final String ADDRESS_KEY = "Address";
    public static final String IMAGE_ID = "image";

    //internment's doc keys
    public static final String REASON_KEY = "Motive";
    public static final String DOCTOR_KEY = "Veterinarian";
    public static final String COMMENTS_KEY = "Observations";
    public static final String DATE_IN_KEY = "Entry date";
    public static final String PROCEDURE_KEY = "Procedure";

    //procedure's doc keys
    public static final String PROCEDURE_DATE_KEY = "Procedure Date";

    //medicine's doc keys
    public static final String DOSAGE_KEY = "Dosage (mg)";
    public static final String FREQUENCY_KEY = "Frequency per day";
    public static final String TOTALDAYS_KEY = "Total Days";
    public static final String MEDICINE_KEY = "Medicine";
}
