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
 * Created by raquelramos on 18-03-2018.
 */

public class ProcedureAdapter extends RecyclerView.Adapter<ProcedureAdapter.ProcedureViewHolder>{

    private List<Procedure> proc = new ArrayList<>();

    // Create new views (invoked by the layout manager)
    @Override
    public ProcedureViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_procedure, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ProcedureViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProcedureViewHolder holder, final int position) {
        final String map = proc.get(position).name;
        String[] parts = map.split(",");
        String tmp00 = parts[0];
        String tmp01 = parts[1];
        String parts00 = tmp00.replace("{", "");
        String tmp10 = parts00.replace("Procedure", "");
        String tmp010 = tmp10.replace("=", "");
        String[] parts01 = tmp01.split("=");
        String tmp11 = parts01[1];
        String[] parts010 = tmp11.split("=");
        String tmp011 = parts010[0];

        final String name = tmp011.replace("}", "");
        final String date = tmp010.replace("Date", "");

        holder.proc_name.setText(name);
        holder.proc_date.setText(date);
    }

    //Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return proc.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ProcedureViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView proc_name;
        public TextView proc_date;
        public View layout;

        ProcedureViewHolder(View v) {
            super(v);
            //layout = v;
            proc_name = (TextView) v.findViewById(R.id.proc_name);
            proc_date = (TextView) v.findViewById(R.id.proc_date);
        }
    }

    public void add(Procedure p) {
        this.proc.add(p);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        proc.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProcedureAdapter(List<Procedure> myDataset) {
        proc = myDataset;
    }


}
