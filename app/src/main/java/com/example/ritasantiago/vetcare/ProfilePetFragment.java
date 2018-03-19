package com.example.ritasantiago.vetcare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.firebase.Animal;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ritasantiago.vetcare.PetsFragment.ANIMAL_BUNDLE_KEY;
import static com.example.ritasantiago.vetcare.firebase.FirebaseFields.IMAGE_ID;

public class ProfilePetFragment extends Fragment {

    private FirebaseFirestore db;
    private TextView generalInfo;
    private TextView hosp;
    private TextView recordInfo;
    private Animal animal;
    private CircleImageView photo;
    private TextView name, dob, owner;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_profile_pet, container, false);
        Bundle args = getArguments();
        this.animal = (Animal) args.getSerializable(ANIMAL_BUNDLE_KEY);
        photo = (CircleImageView) rootView.findViewById(R.id.profile_photo);
        name = (TextView) rootView.findViewById(R.id.animal_name);
        dob = (TextView) rootView.findViewById(R.id.animal_dob);
        owner = (TextView) rootView.findViewById(R.id.animal_owner);

        name.setText(animal.name);
        dob.setText(animal.dateOfBirth);
        owner.setText(animal.owner_name);
        photo.setImageBitmap(animal.image);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("ProfilePets");
        generalInfo = (TextView) getActivity().findViewById(R.id.tv_generalInfo);
        generalInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new GeneralInfoPetFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        hosp = (TextView) getActivity().findViewById(R.id.tv_hospitalisation);
        hosp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new HospitalisationFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        recordInfo = (TextView) getActivity().findViewById(R.id.tv_animalrecords);
        recordInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new PetRecordFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
