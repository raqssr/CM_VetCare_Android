package com.example.ritasantiago.vetcare.pets.hospitalisation.adapters;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.Procedure;

/**
 * Created by raquelramos on 19-03-2018.
 */

public class PetRecordAdapter extends RecyclerView.Adapter<PetRecordAdapter.ViewHolder>{

    private List<Procedure> values = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date;
        public TextView proc_name;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            date = (TextView) v.findViewById(R.id.date);
            proc_name = (TextView) v.findViewById(R.id.proc_consult);
        }
    }

    public void add(Procedure p) {
        values.add(p);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PetRecordAdapter(List<Procedure> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PetRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_record, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PetRecordAdapter.ViewHolder holder, final int position) {
        final String map = values.get(position).name;
        String[] parts = map.split("=");
        String tmp0 = parts[0];
        String tmp1 = parts[1];

        final String name = tmp0.replace("{", "");
        final String date = tmp1.replace("}","");
        holder.date.setText(name);
        holder.proc_name.setText(date);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
