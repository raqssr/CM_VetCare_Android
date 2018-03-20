package com.example.ritasantiago.vetcare.tasklist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.tasklist.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raquelramos on 20-03-2018.
 */

public class RVTaskAdapter extends RecyclerView.Adapter<RVTaskAdapter.TaskViewHolder>
{
    private List<Task> tasks = new ArrayList<>();

    // Create new views (invoked by the layout manager)
    @Override
    public RVTaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_task, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RVTaskAdapter.TaskViewHolder vh = new RVTaskAdapter.TaskViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RVTaskAdapter.TaskViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.task_pet_name.setText(tasks.get(position).animal_name);
        holder.task_name.setText(tasks.get(position).task_name);
        holder.task_hour.setText(tasks.get(position).task_hour);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView task_pet_name;
        public TextView task_name;
        public TextView task_hour;
        //public View layout;

        TaskViewHolder(View v) {
            super(v);
            //layout = v;
            task_pet_name = (TextView) v.findViewById(R.id.med_name);
            task_name = (TextView) v.findViewById(R.id.med_dosage);
            task_hour = (TextView) v.findViewById(R.id.med_period);
        }
    }

    public void addTask(Task t) {
        this.tasks.add(t);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RVTaskAdapter(List<Task> myDataset) {
        tasks = myDataset;
    }

}
