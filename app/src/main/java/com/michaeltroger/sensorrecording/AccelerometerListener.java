package com.michaeltroger.sensorrecording;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class AccelerometerListener implements SensorEventListener {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] sensorValues = event.values;
        String sensorValuesPrintable = event.sensor.getName() + ":";
        for (float s : sensorValues) {
            sensorValuesPrintable += s + "-";
        }
        Log.d(TAG, sensorValuesPrintable);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
