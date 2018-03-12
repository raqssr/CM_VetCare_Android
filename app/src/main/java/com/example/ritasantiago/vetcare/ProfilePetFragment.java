package com.example.ritasantiago.vetcare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePetFragment extends Fragment {

    FirebaseFirestore db;
    TextView generalInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_profile_pet, container, false);
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
    }
}
