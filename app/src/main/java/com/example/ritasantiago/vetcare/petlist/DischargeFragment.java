package com.example.ritasantiago.vetcare.petlist;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ritasantiago.vetcare.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by raquelramos on 21-03-2018.
 */

public class DischargeFragment extends Fragment {

    private static final String TAG = "PdfCreatorActivity";
    private EditText name, weight, doctor, obs;
    private Button generatePdf;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private FirebaseFirestore db;
    public static final String vetClinicName = "Vet Clinic X";
    private String finalText;

    private void deleteAnimal(String name){
        db.collection("Animals").document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Animal deleted!");
            }
        });

        db.collection("Internments").document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "Animal deleted!");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_discharge, container, false);
        db = FirebaseFirestore.getInstance();
        name = (EditText) rootView.findViewById(R.id.et_nameAnimal);
        weight = (EditText) rootView.findViewById(R.id.et_weight);
        doctor = (EditText) rootView.findViewById(R.id.et_doctor);
        obs = (EditText) rootView.findViewById(R.id.et_observations);
        generatePdf = (Button) rootView.findViewById(R.id.btn_generatePdf);
        generatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });


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
                    showMessageOKCancel("You need to allow access to Storage",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            }
                        });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity().getApplicationContext(), "WRITE_EXTERNAL: Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        String fileName = name.getText().toString() + ".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), fileName);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(vetClinicName));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Animal name: " + name.getText().toString()));
        document.add(new Paragraph("Weight: " + weight.getText().toString()));
        document.add(new Paragraph("Veterinarian: " + doctor.getText().toString()));
        document.add(new Paragraph("Observations: " + obs.getText().toString()));
        document.close();
        Toast.makeText(getActivity().getApplicationContext(),"Discharge document generated with success!",Toast.LENGTH_SHORT).show();
        String s = name.getText().toString();
        deleteAnimal(s);
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
        //previewPdf();

    }

    /*private void previewPdf() {

        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("/storage/emulated/0/Documents");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "/storage/emulated/0/Documents");

            startActivity(intent);
        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }*/

}
