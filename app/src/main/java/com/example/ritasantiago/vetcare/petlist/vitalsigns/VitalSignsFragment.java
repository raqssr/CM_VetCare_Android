package com.example.ritasantiago.vetcare.petlist.vitalsigns;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ritasantiago.vetcare.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raquelramos on 23-03-2018.
 */

public class VitalSignsFragment extends Fragment {

    LineChart heart_chart, temp_chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView = inflater.inflate(R.layout.fragment_vital_signs, container, false);
        heart_chart = (LineChart) rootView.findViewById(R.id.chart_heart);
        temp_chart = (LineChart) rootView.findViewById(R.id.chart_temp);

        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();

        double x = 0;
        int numDataPoints = 1000;
        for (int i = 0; i < numDataPoints; i++)
        {
            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAXESsin.add(new Entry(sinFunction, i));
            yAXEScos.add(new Entry(cosFunction, i));
            xAXES.add(i, String.valueOf(x));
        }

        String[] xaxes = new String[xAXES.size()];
        for (int i = 0; i < xAXES.size(); i++)
        {
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(R.color.blue);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin, "sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(R.color.colorError);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        heart_chart.setData(new LineData(lineDataSet1));
        heart_chart.getXAxis().setAxisMaximum(10f);
        heart_chart.getXAxis().setAxisMinimum(0f);
        temp_chart.setData(new LineData(lineDataSet2));

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Vital Signs");
    }


}
