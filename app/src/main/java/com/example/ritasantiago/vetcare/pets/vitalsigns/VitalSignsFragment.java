package com.example.ritasantiago.vetcare.pets.vitalsigns;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ritasantiago.vetcare.R;
import com.example.ritasantiago.vetcare.pets.vitalsigns.custom.MyMarkerView;
import com.example.ritasantiago.vetcare.pets.vitalsigns.simulators.Simulators;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

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

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Vital Signs");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_button));

        heart_chart = (LineChart) rootView.findViewById(R.id.chart_heart);
        // no description text
        heart_chart.getDescription().setEnabled(false);
        // enable touch gestures
        heart_chart.setTouchEnabled(true);
        // enable scaling and dragging
        heart_chart.setDragEnabled(true);
        heart_chart.setScaleEnabled(true);

        temp_chart = (LineChart) rootView.findViewById(R.id.chart_temp);
        // no description text
        temp_chart.getDescription().setEnabled(false);
        // enable touch gestures
        temp_chart.setTouchEnabled(true);
        // enable scaling and dragging
        temp_chart.setDragEnabled(true);
        temp_chart.setScaleEnabled(true);

        MyMarkerView mv = new MyMarkerView(getActivity());
        mv.setChartView(heart_chart); // For bounds control
        heart_chart.setMarker(mv); // Set the marker to the chart
        mv.setChartView(temp_chart); // For bounds control
        temp_chart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(2f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis_heart = heart_chart.getXAxis();
        xAxis_heart.enableGridDashedLine(10f, 10f, 0f);

        XAxis xAxis_temp = temp_chart.getXAxis();
        xAxis_temp.enableGridDashedLine(10f, 10f, 0f);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        LimitLine ll1_heart = new LimitLine(100f, "Upper Limit");
        ll1_heart.setLineWidth(2f);
        ll1_heart.enableDashedLine(10f, 10f, 0f);
        ll1_heart.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1_heart.setTextSize(10f);
        ll1_heart.setLineColor(R.color.black);
        //ll1.setTypeface(tf);

        LimitLine ll2_heart = new LimitLine(60f, "Lower Limit");
        ll2_heart.setLineWidth(2f);
        ll2_heart.enableDashedLine(10f, 10f, 0f);
        ll2_heart.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2_heart.setTextSize(10f);
        ll2_heart.setLineColor(R.color.black);
        //ll2.setTypeface(tf);

        YAxis leftAxis_heart = heart_chart.getAxisLeft();
        leftAxis_heart.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis_heart.addLimitLine(ll1_heart);
        leftAxis_heart.addLimitLine(ll2_heart);
        leftAxis_heart.setAxisMaximum(110f);
        leftAxis_heart.setAxisMinimum(50f);
        //leftAxis.setYOffset(20f);
        leftAxis_heart.enableGridDashedLine(10f, 10f, 0f);
        leftAxis_heart.setDrawZeroLine(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis_heart.setDrawLimitLinesBehindData(true);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        LimitLine ll1_temp = new LimitLine(40f, "Upper Limit");
        ll1_temp.setLineWidth(2f);
        ll1_temp.enableDashedLine(10f, 10f, 0f);
        ll1_temp.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1_temp.setTextSize(10f);
        ll1_temp.setLineColor(R.color.black);
        //ll1.setTypeface(tf);

        LimitLine ll2_temp = new LimitLine(20f, "Lower Limit");
        ll2_temp.setLineWidth(2f);
        ll2_temp.enableDashedLine(10f, 10f, 0f);
        ll2_temp.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2_temp.setTextSize(10f);
        ll2_temp.setLineColor(R.color.black);
        //ll2.setTypeface(tf);

        YAxis leftAxis_temp = temp_chart.getAxisLeft();
        leftAxis_temp.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis_temp.addLimitLine(ll1_temp);
        leftAxis_temp.addLimitLine(ll2_temp);
        leftAxis_temp.setAxisMaximum(50f);
        leftAxis_temp.setAxisMinimum(10f);
        //leftAxis.setYOffset(20f);
        leftAxis_temp.enableGridDashedLine(10f, 10f, 0f);
        leftAxis_temp.setDrawZeroLine(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis_temp.setDrawLimitLinesBehindData(true);

        heart_chart.getAxisRight().setEnabled(false);
        temp_chart.getAxisRight().setEnabled(false);
        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        //add data
        setDataHeart();
        setDataTemperature();

        heart_chart.animateX(1500);
        temp_chart.animateX(1500);

        //get the legend (only possible after setting data)
        Legend l_heart = heart_chart.getLegend();
        Legend l_temp = temp_chart.getLegend();

        //modify the legend ...
        l_heart.setForm(Legend.LegendForm.LINE);
        l_temp.setForm(Legend.LegendForm.LINE);

        //refresh the drawing
        //mChart.invalidate();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Vital Signs");
    }

    private void setDataHeart() {

        ArrayList<Entry> valuesHeart = new ArrayList<>();
        LineDataSet set1;
        Simulators s = new Simulators();

        for (int i = 0; i < 24; i++) {
            float val = (float) s.calcBat(60,100);
            valuesHeart.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
        }

        if (heart_chart.getData() != null &&
                heart_chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) heart_chart.getData().getDataSetByIndex(0);
            set1.setValues(valuesHeart);
            heart_chart.getData().notifyDataChanged();
            heart_chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(valuesHeart, "Heart Beats (heart beats/minute)");

            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            heart_chart.setData(data);
        }
    }

    private void setDataTemperature()
    {
        ArrayList<Entry> valuesTemp = new ArrayList<>();
        LineDataSet set2;
        Simulators s = new Simulators();

        for (int i = 0; i < 24; i++) {
            float val = (float) s.calcTemperature(20,40);
            valuesTemp.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
        }

        if (temp_chart.getData() != null &&
                temp_chart.getData().getDataSetCount() > 0) {
            set2 = (LineDataSet) temp_chart.getData().getDataSetByIndex(0);
            set2.setValues(valuesTemp);
            temp_chart.getData().notifyDataChanged();
            temp_chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set2 = new LineDataSet(valuesTemp, "Temperature (ºC)");

            set2.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set2.enableDashedLine(10f, 5f, 0f);
            set2.enableDashedHighlightLine(10f, 5f, 0f);
            set2.setColor(Color.BLACK);
            set2.setCircleColor(Color.BLACK);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setValueTextSize(9f);
            set2.setDrawFilled(true);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set2.setFillDrawable(drawable2);
            }
            else {
                set2.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
            dataSets2.add(set2); // add the datasets

            // create a data object with the datasets
            LineData data2 = new LineData(dataSets2);

            // set data
            temp_chart.setData(data2);
        }
    }


}
