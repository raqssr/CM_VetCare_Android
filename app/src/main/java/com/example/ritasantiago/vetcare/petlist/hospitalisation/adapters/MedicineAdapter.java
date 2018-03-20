package com.example.ritasantiago.vetcare.petlist.hospitalisation.adapters;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Medicine;

/**
 * Created by raquelramos on 18-03-2018.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>{

    private List<Medicine> meds = new ArrayList<>();

    // Create new views (invoked by the layout manager)
    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_medicine, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MedicineViewHolder vh = new MedicineViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MedicineViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.med_name.setText(meds.get(position).name);
        holder.med_dosage.setText(meds.get(position).dosage);
        holder.med_period.setText(meds.get(position).frequency);
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView med_name;
        public TextView med_dosage;
        public TextView med_period;
        //public View layout;

        MedicineViewHolder(View v) {
            super(v);
            //layout = v;
            med_name = (TextView) v.findViewById(R.id.med_name);
            med_dosage = (TextView) v.findViewById(R.id.med_dosage);
            med_period = (TextView) v.findViewById(R.id.med_period);
        }
    }

    public void addMedicine(Medicine med) {
        this.meds.add(med);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        meds.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicineAdapter(List<Medicine> myDataset) {
        meds = myDataset;
    }
}
