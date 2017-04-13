package com.michaeltroger.sensorrecording;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private AccelerometerListener mAccelerometerListener;
    private Sensor mMagnetometer;
    private MagnetometerListener mMagnetometerListener;
    private Sensor mBarometer;
    private BarometerListener mBarometerListener;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mMagnetometerListener, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mBarometerListener, mBarometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mAccelerometerListener);
        mSensorManager.unregisterListener(mMagnetometerListener);
        mSensorManager.unregisterListener(mBarometerListener);
    }
}
