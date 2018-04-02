package com.example.ritasantiago.vetcare.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.calendar.adapters.RVCalendarAdapter;
import com.example.ritasantiago.vetcare.db.entity.EventsCalendar;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by raquelramos on 04-03-2018.
 */

public class CalendarFragment extends Fragment {

    //CalendarView calendarView;
    private CompactCalendarView calendar;
    private TextView tv_month;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String eventsKey = "Events";
    private String events;

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Calendar");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        calendar = (CompactCalendarView) rootView.findViewById(R.id.compactcalendar_view);

        tv_month = (TextView) rootView.findViewById(R.id.tv_month);
        tv_month.setText(dateFormatForMonth.format(calendar.getFirstDayOfCurrentMonth()));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        events = sharedPref.getString(eventsKey, "coco");
        Log.d("Calendar Fragment", events);

        String[] eventItem = events.split(",");
        for(int i = 0; i < eventItem.length; i++)
        {
            String[] s = eventItem[i].split("\\(");
            String task = s[0].trim().replaceAll("\\[", "");
            String[] taskInfo = task.split(":");
            String task_animal = taskInfo[0];
            String task_description = taskInfo[1].trim();
            String eventInfo = s[1].replaceAll("\\)", "");
            String[] eventSpliter = eventInfo.split("T");
            String eventDate = eventSpliter[0];
            String eventTime = eventSpliter[1].replaceAll(".000Z", "");
            final String eventDateTime = eventDate + " " + eventTime;
            final long dateToTimestamp = convertDateToTimestamp(eventDateTime);
            Event ev1 = new Event(R.color.colorAccent, dateToTimestamp, task);
            calendar.addEvent(ev1);
            calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {

                    List<Event> eventsCalendar = calendar.getEvents(dateClicked);
                    List<EventsCalendar> eventItens = new ArrayList<>();
                    for (int i = 0; i < eventsCalendar.size(); i++)
                    {
                        Log.d("EventsCalendar", eventsCalendar.get(i).getData().toString());
                        String name = eventsCalendar.get(i).getData().toString();
                        String date = convertToDate(eventsCalendar.get(i).getTimeInMillis());
                        String[] dateInfo = date.split(" ");
                        eventItens.add(new EventsCalendar(name, dateInfo[1]));

                    }

                    mAdapter = new RVCalendarAdapter(eventItens);
                    recyclerView.setAdapter(mAdapter);

                    Log.d("Calendar clicked: ", "Day was clicked: " + dateClicked + " with events " + eventsCalendar);
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    tv_month.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                }
            });
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("CalendarFragment");
    }

    private long convertDateToTimestamp(String s)
    {
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(s);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    private String convertToDate(long time) {
        java.util.Calendar cal = java.util.Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString();
        Log.d("getDate", date);
        return date;
    }
}
