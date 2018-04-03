package com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ritasantiago.vetcare.db.entity.Animal;

import com.example.ritasantiago.vetcare.petlist.hospitalisation.InfoPetTabFragment;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.MedicineTabFragment;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.ProceduresTabFragment;

/**
 * Created by raquelramos on 12-03-2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public Animal animal;
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Animal animal) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.animal = animal;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoPetTabFragment tab1 = new InfoPetTabFragment();
                Bundle bundle1  = new Bundle();
                bundle1.putSerializable(ANIMAL_BUNDLE_KEY, animal);
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                MedicineTabFragment tab2 = new MedicineTabFragment();
                Bundle bundle2  = new Bundle();
                bundle2.putSerializable(ANIMAL_BUNDLE_KEY, animal);
                tab2.setArguments(bundle2);
                return tab2;
            case 2:
                ProceduresTabFragment tab3 = new ProceduresTabFragment();
                Bundle bundle3  = new Bundle();
                bundle3.putSerializable(ANIMAL_BUNDLE_KEY, animal);
                tab3.setArguments(bundle3);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

