package com.michaeltroger.sensorrecording;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.hardware.SensorManager.getAltitude;

public class BarometerListener implements SensorEventListener {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] sensorValues = event.values;
        String sensorValuesPrintable = event.sensor.getName() + ":";
        for (float s : sensorValues) {
            sensorValuesPrintable += s + "-";
        }
        Log.d(TAG, sensorValuesPrintable);

        float presure = event.values[0];
        float altitude = getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, presure);
        Log.d(TAG, ""+altitude);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
