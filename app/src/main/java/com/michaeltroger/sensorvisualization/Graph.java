package com.michaeltroger.sensorvisualization;

import android.graphics.Color;
import android.hardware.SensorEvent;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.michaeltroger.sensorvaluelegend.SensorValueLegend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Graph implements IGraph {
    private static final float SATURATION = 1;
    private static final float VALUE = 1;
    private static final int SECONDS_TO_SHOW = 10;

    private final Random mRandom;
    private float mHue;
    private long mStartTime = SystemClock.elapsedRealtimeNanos();
    private final GraphView mGraphView;
    private final List<LineGraphSeries<DataPoint>> mSeries = new ArrayList<>();

    Graph(@NonNull final GraphView graphView) {
        mRandom = new Random();
        mGraphView = graphView;
        mGraphView.getViewport().setXAxisBoundsManual(true);
        mGraphView.getViewport().setMinX(0);
        mGraphView.getViewport().setMaxX(SECONDS_TO_SHOW);

        mGraphView.getLegendRenderer().setVisible(true);
        mGraphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        mStartTime = SystemClock.elapsedRealtimeNanos();
    }

    @Override
    public void printSensorData(@NonNull final SensorEvent event) {
        final long nanoseconds = event.timestamp;
        final float[] sensorValues = event.values;
        final int sensorType = event.sensor.getType();
        if (mSeries.isEmpty()) {
            fillSeries(sensorValues, sensorType);
        }
        final float seconds = (nanoseconds - mStartTime) / 1000000000f;
        for (int i = 0; i < sensorValues.length; i++) {
            mSeries.get(i).appendData(new DataPoint(seconds, sensorValues[i]), true, 1000);
        }
    }

    private void fillSeries(@NonNull final float[] sensorValues, final int sensorType) {
        final String[] legend = SensorValueLegend.getLegend(sensorType);

        for (int i = 0; i < sensorValues.length; i++) {
            final LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            series.setColor(getRandomColor());
            if (i < legend.length) {
                series.setTitle(legend[i]);
            }

            mSeries.add(series);
            mGraphView.addSeries(series);
        }
        mGraphView.getLegendRenderer().resetStyles();
    }

    @Override
    public void reset() {
        mSeries.clear();
        mGraphView.removeAllSeries();

        mStartTime = SystemClock.elapsedRealtimeNanos();
    }

    private int getRandomColor() {
        mHue = (mHue + 36 + 240f * mRandom.nextFloat()) % 360f;
        return Color.HSVToColor(new float[]{mHue, SATURATION, VALUE});
    }

}