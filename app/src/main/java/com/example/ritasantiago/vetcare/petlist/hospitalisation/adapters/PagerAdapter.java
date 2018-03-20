package com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ritasantiago.vetcare.petlist.hospitalisation.InfoPetTabFragment;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.MedicineTabFragment;
import com.example.ritasantiago.vetcare.petlist.hospitalisation.ProceduresTabFragment;

/**
 * Created by raquelramos on 12-03-2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoPetTabFragment tab1 = new InfoPetTabFragment();
                return tab1;
            case 1:
                MedicineTabFragment tab2 = new MedicineTabFragment();
                return tab2;
            case 2:
                ProceduresTabFragment tab3 = new ProceduresTabFragment();
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

