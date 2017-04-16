package com.michaeltroger.sensorrecording;

import android.graphics.Color;
import android.os.SystemClock;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Graph {
    private static final float SATURATION = 1;
    private static final float VALUE = 1;
    private final Random mRandom;
    private float mHue;
    private long startTime = SystemClock.elapsedRealtimeNanos();
    private GraphView mGraphView;
    private List<LineGraphSeries<DataPoint>> mSeries = new ArrayList<>();

    Graph(GraphView graphView) {
        mRandom = new Random();
        mGraphView = graphView;
        mGraphView.getViewport().setXAxisBoundsManual(true);
        mGraphView.getViewport().setMinX(0);
        mGraphView.getViewport().setMaxX(4);

        mGraphView.getGridLabelRenderer().setLabelVerticalWidth(100);

        mGraphView.getLegendRenderer().setVisible(true);
        mGraphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        startTime = SystemClock.elapsedRealtimeNanos();
    }


    void printSensorData(long nanoseconds, float[] sensorValues) {
        if (mSeries.size() == 0) {
            fillSeries(sensorValues);
        }
        float seconds = (nanoseconds - startTime) / 1000000000f;
        for (int i = 0; i < sensorValues.length; i++) {
            mSeries.get(i).appendData(new DataPoint(seconds, sensorValues[i]), true, 100);
        }
    }

    private void fillSeries(float[] sensorValues) {
        for (int i = 0; i < sensorValues.length; i++) {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            series.setColor(getRandomColor());
            mSeries.add(series);
            mGraphView.addSeries(series);
        }
    }

    void reset() {
        mSeries.clear();
        mGraphView.removeAllSeries();

        startTime = SystemClock.elapsedRealtimeNanos();
    }

    private int getRandomColor(){
        mHue = (mHue + 36 + 240f * mRandom.nextFloat()) % 360f;
        return Color.HSVToColor(new float[]{mHue, SATURATION, VALUE});
    }

}