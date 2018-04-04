package com.example.ritasantiago.vetcare.pets.profile;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.pets.info.GeneralInfoPetFragment;
import com.example.ritasantiago.vetcare.pets.hospitalisation.HospitalisationFragment;
import com.example.ritasantiago.vetcare.pets.record.PetRecordFragment;
import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Animal;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ritasantiago.vetcare.pets.PetsFragment.ANIMAL_BUNDLE_KEY;

public class ProfilePetFragment extends Fragment {

    private FirebaseFirestore db;
    public Animal animal;
    private FloatingActionButton send;
    private Button discharge;
    private CircleImageView photo;
    private TextView name, dob, owner;
    private String SEND_SMS = "SEND_SMS";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_profile_pet, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_button));

        Bundle args = getArguments();
        this.animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);
        photo = (CircleImageView) rootView.findViewById(R.id.profile_photo);
        name = (TextView) rootView.findViewById(R.id.g_name);
        dob = (TextView) rootView.findViewById(R.id.animal_dob);
        owner = (TextView) rootView.findViewById(R.id.animal_owner);

        name.setText(animal.name);
        dob.setText(animal.dateOfBirth);
        owner.setText(animal.owner_name);
        photo.setImageBitmap(animal.image);

        discharge = (Button) rootView.findViewById(R.id.btn_discharge);
        discharge.setOnClickListener(view -> {
            Fragment fragment = new DischargeFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        send = (FloatingActionButton) rootView.findViewById(R.id.sendTextMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
                Fragment fragment = new SendTextFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("ProfilePets");
        TextView generalInfo = (TextView) getActivity().findViewById(R.id.tv_generalInfo);
        generalInfo.setOnClickListener(v -> {
            Fragment fragment = new GeneralInfoPetFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        TextView hosp = (TextView) getActivity().findViewById(R.id.tv_hospitalisation);
        hosp.setOnClickListener(v -> {
            Fragment fragment = new HospitalisationFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        TextView recordInfo = (TextView) getActivity().findViewById(R.id.tv_animalrecords);
        recordInfo.setOnClickListener(v -> {
            Fragment fragment = new PetRecordFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });
    }


}
