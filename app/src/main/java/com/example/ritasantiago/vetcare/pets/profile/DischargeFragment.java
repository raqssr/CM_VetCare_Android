package com.example.ritasantiago.vetcare.pets.profile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by raquelramos on 21-03-2018.
 */

public class DischargeFragment extends Fragment {

    private EditText name, weight, doctor, obs;
    public File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private FirebaseFirestore db;
    public static final String vetClinicName = "Vet Clinic X";
    private String finalText;

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;

    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private Bitmap mBitmapToSave;
    private ArrayList<String> dischargeInfo = new ArrayList<>();
    private String s;

    private void deleteAnimal(final String name){
        db.collection("Animals").document(name).delete().addOnSuccessListener(aVoid -> Log.d("TAG", "Animal deleted!"));

        db.collection("Internments").document(name).delete().addOnSuccessListener(aVoid -> Log.d("TAG", "Animal deleted!"));
        db.collection("Historics").document(name).delete().addOnSuccessListener(aVoid -> Log.d("TAG", "Animal deleted!"));

        /*db.collection("Medicines").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> data = query.getDocuments();
                for(int i = 0; i < data.size(); i++){
                    DocumentSnapshot doc = data.get(i);
                    if(doc.contains(name)){
                        DocumentReference reference = doc.getDocumentReference(name);
                        reference.delete();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });*/

        /*db.collection("Procedures").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> data = query.getDocuments();
                for(int i = 0; i < data.size(); i++){
                    DocumentSnapshot doc = data.get(i);
                    if(doc.contains(name)){
                        DocumentReference reference = doc.getDocumentReference(name);
                        reference.delete();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_discharge, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.discharge);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_button));

        db = FirebaseFirestore.getInstance();
        name = (EditText) rootView.findViewById(R.id.et_nameAnimal);
        weight = (EditText) rootView.findViewById(R.id.et_weight);
        doctor = (EditText) rootView.findViewById(R.id.et_doctor);
        obs = (EditText) rootView.findViewById(R.id.et_observations);
        Button generatePdf = (Button) rootView.findViewById(R.id.btn_generatePdf);
        generatePdf.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty()){
                name.setError("Animal name is empty!");
                name.requestFocus();
                return;
            }
            if (weight.getText().toString().isEmpty()){
                weight.setError("Weight field is empty!");
                weight.requestFocus();
                return;
            }
            if (doctor.getText().toString().isEmpty()){
                doctor.setError("Doctor field is empty!");
                doctor.requestFocus();
                return;
            }
            if (obs.getText().toString().isEmpty()){
                obs.setError("Observations field is empty!");
                obs.requestFocus();
                return;
            }
            try {
                createPdfWrapper();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });

        signIn();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Discharge");
    }

    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel(
                            (dialog, which) -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }else {
            createPdf();
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage("You need to allow access to Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }

        String fileName = name.getText().toString() + ".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), fileName);
        final OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(vetClinicName));
        dischargeInfo.add(vetClinicName);
        document.add(new Paragraph("\n"));
        dischargeInfo.add("\n");
        document.add(new Paragraph("Animal name: " + name.getText().toString()));
        dischargeInfo.add("Animal name: " + name.getText().toString());
        document.add(new Paragraph("Weight: " + weight.getText().toString()));
        dischargeInfo.add("Weight: " + weight.getText().toString());
        document.add(new Paragraph("Veterinarian: " + doctor.getText().toString()));
        dischargeInfo.add("Veterinarian: " + doctor.getText().toString());
        document.add(new Paragraph("Observations: " + obs.getText().toString()));
        dischargeInfo.add("Observations: " + obs.getText().toString());
        document.close();
        Toast.makeText(getActivity().getApplicationContext(),"Discharge document generated with success!",Toast.LENGTH_SHORT).show();
        saveFileToDrive();
        s = name.getText().toString();
        //previewPdf();
    }

    /** Start sign in activity. */
    private void signIn() {
        GoogleSignInClient GoogleSignInClient = buildGoogleSignInClient();
        startActivityForResult(GoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    /** Build a Google SignIn client. */
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(getActivity(), signInOptions);
    }

    /** Create a new file and save it to Drive. */
    private void saveFileToDrive() {
        // Start by creating a new contents, and setting a callback.
        mDriveResourceClient
                .createContents()
                .continueWithTask(
                        task -> createFileIntentSender(task.getResult(), dischargeInfo))
                .addOnFailureListener(
                        e -> Log.w(TAG, "Failed to create new contents.", e));
    }

    /**
     * Creates an {@link IntentSender} to start a dialog activity with configured {@link
     * CreateFileActivityOptions} for user to create a new photo in Drive.
     */
    private Task<Void> createFileIntentSender(DriveContents driveContents, ArrayList<String> a) {
        // Get an output stream for the contents.
        OutputStream outputStream = driveContents.getOutputStream();

        try (Writer writer = new OutputStreamWriter(outputStream)) {
            for (int i = 0; i < a.size(); i++)
            {
                writer.write(a.get(i));
                writer.write("\n");
            }
        } catch (IOException e) {
            Log.w(TAG, "Unable to write file contents.", e);
        }

        // Create the initial metadata - MIME type and title.
        // Note that the user will be able to change the title later.
        MetadataChangeSet metadataChangeSet =
                new MetadataChangeSet.Builder()
                        .setMimeType("application/pdf")
                        .setTitle("teste.pdf")
                        .build();
        // Set up options to configure and display the create file activity.
        CreateFileActivityOptions createFileActivityOptions =
                new CreateFileActivityOptions.Builder()
                        .setInitialMetadata(metadataChangeSet)
                        .setInitialDriveContents(driveContents)
                        .build();

        return mDriveClient
                .newCreateFileActivityIntentSender(createFileActivityOptions)
                .continueWith(
                        task -> {
                            startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0, null);
                            return null;
                        });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {
                    // Use the last signed in account here since it already have a Drive scope.
                    mDriveClient = Drive.getDriveClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()));
                    // Build a drive resource client.
                    mDriveResourceClient =
                            Drive.getDriveResourceClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()));
                    // Start camera.
                    /*startActivityForResult(
                            new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);*/
                }
                break;
            case REQUEST_CODE_CAPTURE_IMAGE:
                // Called after a photo has been taken.
                if (resultCode == RESULT_OK) {
                    // Store the image data as a bitmap for writing later.
                    //mBitmapToSave = (Bitmap) data.getExtras().get("data");
                    //saveFileToDrive();
                }
                break;
            case REQUEST_CODE_CREATOR:
                // Called after a file is saved to Drive.
                if (resultCode == RESULT_OK) {
                    deleteAnimal(s);
                    getFragmentManager().popBackStack();
                    getFragmentManager().popBackStack();
                    //mBitmapToSave = null;
                    // Just start the camera again for another photo.
                    //startActivityForResult(
                            //new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);
                }
                break;
        }
    }

}