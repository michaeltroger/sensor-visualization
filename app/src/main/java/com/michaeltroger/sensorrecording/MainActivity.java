package com.michaeltroger.sensorrecording;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    public static final java.lang.String ARG_SECTION_NUMBER = "a";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private AccelerometerListener mAccelerometerListener;
    private Sensor mMagnetometer;
    private MagnetometerListener mMagnetometerListener;
    private Sensor mBarometer;
    private BarometerListener mBarometerListener;
    private RealtimeScrolling mRealtimeScrolling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mBarometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mAccelerometerListener = new AccelerometerListener();
        mMagnetometerListener = new MagnetometerListener();
        mBarometerListener = new BarometerListener();

        /*GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/
        mRealtimeScrolling = new RealtimeScrolling();
        GraphView graph = (GraphView) findViewById(R.id.graph);
        mRealtimeScrolling.initGraph(graph);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mMagnetometerListener, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mBarometerListener, mBarometer, SensorManager.SENSOR_DELAY_NORMAL);
        mRealtimeScrolling.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mAccelerometerListener);
        mSensorManager.unregisterListener(mMagnetometerListener);
        mSensorManager.unregisterListener(mBarometerListener);
        mRealtimeScrolling.onPause();
    }
}
