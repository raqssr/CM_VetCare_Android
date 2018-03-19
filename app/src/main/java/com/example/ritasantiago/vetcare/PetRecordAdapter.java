package com.example.ritasantiago.vetcare;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by raquelramos on 19-03-2018.
 */

public class PetRecordAdapter extends RecyclerView.Adapter<PetRecordAdapter.ViewHolder>{

    private List<String> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date;
        public TextView proc_consult;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            date = (TextView) v.findViewById(R.id.date);
            proc_consult = (TextView) v.findViewById(R.id.proc_consult);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PetRecordAdapter(List<String> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PetRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.fragment_item_record, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PetRecordAdapter.ViewHolder vh = new PetRecordAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PetRecordAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        holder.date.setText(name);
        holder.proc_consult.setText(name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
