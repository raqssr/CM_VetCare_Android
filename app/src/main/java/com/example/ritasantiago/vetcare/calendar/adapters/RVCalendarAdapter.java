package com.example.ritasantiago.vetcare.calendar.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.db.entity.EventsCalendar;

import java.util.List;

/**
 * Created by raquelramos on 19-03-2018.
 */

public class RVCalendarAdapter extends RecyclerView.Adapter<RVCalendarAdapter.ViewHolder>
{
    private List<EventsCalendar> events;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView event_name;
        public TextView event_hour;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            event_name = (TextView) v.findViewById(R.id.event_name);
            event_hour = (TextView) v.findViewById(R.id.event_hour);
        }
    }

    public void addEvent(EventsCalendar event) {
        this.events.add(event);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        events.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RVCalendarAdapter(List<EventsCalendar> myDataset) {
        events = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RVCalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_events, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RVCalendarAdapter.ViewHolder vh = new RVCalendarAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RVCalendarAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.event_name.setText(events.get(position).name);
        holder.event_hour.setText(events.get(position).hour);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return events.size();
    }

}
