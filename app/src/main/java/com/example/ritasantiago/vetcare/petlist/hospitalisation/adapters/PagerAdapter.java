package com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.example.ritasantiago.vetcare.db.entity.Animal;

import static com.example.ritasantiago.vetcare.petlist.PetsFragment.ANIMAL_BUNDLE_KEY;

import com.example.ritasantiago.vetcare.petlist.hospitalisation.InfoPetTabFragment;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.MedicineTabFragment;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.ProceduresTabFragment;

/**
 * Created by raquelramos on 12-03-2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private Animal animal;
    public static final String ANIMAL_BUNDLE_KEY = "animal_bundle";
    int mNumOfTabs;

    Bundle bundle  = new Bundle();

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

        bundle.putSerializable(ANIMAL_BUNDLE_KEY, animal);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoPetTabFragment tab1 = new InfoPetTabFragment();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                MedicineTabFragment tab2 = new MedicineTabFragment();
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                ProceduresTabFragment tab3 = new ProceduresTabFragment();
                tab3.setArguments(bundle);
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

