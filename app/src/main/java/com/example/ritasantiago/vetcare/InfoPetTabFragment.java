package com.example.ritasantiago.vetcare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.firebase.Internment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.*;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class InfoPetTabFragment extends Fragment {

    private FirebaseFirestore db;
    private Internment internment = new Internment();

    TextView dateEntry;
    TextView motive;
    TextView observations;
    TextView doctor;

    private void initializeData(){
        db = FirebaseFirestore.getInstance();
        db.collection("Internments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot query = task.getResult();
                    List<DocumentSnapshot> doc = query.getDocuments();
                    for(int i = 0; i< doc.size(); i++) {
                        addInternment(new Internment(doc.get(i).get(DATE_IN_KEY).toString(),
                            doc.get(i).get(REASON_KEY).toString(),
                            doc.get(i).get(COMMENTS_KEY).toString(),
                            doc.get(i).get(DOCTOR_KEY).toString()));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", e.toString());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initializeData();
        return inflater.inflate(R.layout.fragment_info_pet_tab, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateEntry = (TextView) view.findViewById(R.id.entry_date_tv);
        motive = (TextView) view.findViewById(R.id.motive_tv);
        observations = (TextView) view.findViewById(R.id.observations_tv);
        doctor = (TextView) view.findViewById(R.id.doctor_tv);

        dateEntry.setText(internment.dateIn);
        motive.setText(internment.reason_internment);
        observations.setText(internment.comments);
        doctor.setText(internment.doctor);

        getActivity().setTitle("InfoPetTabFragment");
    }

    void addInternment(Internment internment){
        this.internment = internment;
    }
}
