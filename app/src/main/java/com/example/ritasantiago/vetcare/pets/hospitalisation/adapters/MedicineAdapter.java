package com.example.ritasantiago.vetcare.pets.hospitalisation.adapters;

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
        return new MedicineViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MedicineViewHolder holder, final int position) {
        final String map = meds.get(position).name;
        String tmp0010 = map.replace("Frequency per day", "");
        String tmp0011 = tmp0010.replace("Dosage (mg)", "");
        String tmp0100 = tmp0011.replace("Total Days", "");
        String tmp0001 = tmp0100.replace("=", "");
        String[] parts0 = tmp0001.split(",");
        String tmp0101 = parts0[0];

        final String dosage = parts0[1];
        final String period = tmp0101.replaceAll("[^0-9]", "");
        final String name = tmp0101.replaceAll("[^a-zA-Z]", "");

        holder.med_name.setText(name);
        holder.med_dosage.setText(dosage);
        holder.med_period.setText(period);
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
