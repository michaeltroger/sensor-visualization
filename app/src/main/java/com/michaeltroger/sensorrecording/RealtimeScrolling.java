package com.michaeltroger.sensorrecording;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Random;


public class RealtimeScrolling {
    private final Handler mHandler = new Handler();
    private Runnable mTimer;
    private double graphLastXValue = 5d;
    private LineGraphSeries<DataPoint> mSeriesXAxis;
    private double mLastRandom = 2;
    private Random mRand = new Random();
    private long time = System.currentTimeMillis();
    private LineGraphSeries<DataPoint> mSeriesYAxis;
    private LineGraphSeries<DataPoint> mSeriesZAxis;
    private PointsGraphSeries<DataPoint> mTagSeries;
    private long startTime = SystemClock.elapsedRealtimeNanos();
    float seconds;

    public void initGraph(GraphView graph) {
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(10);

        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTagSeries.appendData(new DataPoint(seconds, 0), false, 100);
            }
        });
        mSeriesXAxis = new LineGraphSeries<>();
        mSeriesXAxis.setColor(Color.RED);
        mSeriesXAxis.setTitle("x");

        mSeriesYAxis = new LineGraphSeries<>();
        mSeriesYAxis.setColor(Color.BLUE);
        mSeriesYAxis.setTitle("y");

        mSeriesZAxis = new LineGraphSeries<>();
        mSeriesZAxis.setColor(Color.YELLOW);
        mSeriesZAxis.setTitle("z");

        graph.addSeries(mSeriesXAxis);
        graph.addSeries(mSeriesYAxis);
        graph.addSeries(mSeriesZAxis);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        mTagSeries = new PointsGraphSeries<>();
        mTagSeries.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x, 0, x, canvas.getHeight(), paint);
            }
        });
        mTagSeries.setSize(10);
        mTagSeries.setColor(Color.BLACK);
        mTagSeries.setTitle("tag");
        graph.addSeries(mTagSeries);

        startTime = SystemClock.elapsedRealtimeNanos();
    }
    public void onCreate() {

    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void printSensorData(long nanoseconds, float[] value) {
        long now = System.currentTimeMillis();
        if (now - time > 100) {
            seconds = (nanoseconds - startTime) / 1000000000f;
            mSeriesXAxis.appendData(new DataPoint(seconds, value[0]), true, 1000);
            mSeriesYAxis.appendData(new DataPoint(seconds, value[1]), false, 1000);
            mSeriesZAxis.appendData(new DataPoint(seconds, value[2]), false, 1000);
            time = now;
        }
    }


}