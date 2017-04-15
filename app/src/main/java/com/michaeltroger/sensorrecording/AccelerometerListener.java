package com.michaeltroger.sensorrecording;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

public class AccelerometerListener implements SensorEventListener {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onSensorChanged(SensorEvent event) {
        long timeInMillis = System.currentTimeMillis() + ((event.timestamp- SystemClock.elapsedRealtimeNanos())/1000000L);
        Log.d(TAG, "time:");
        Log.d(TAG, new Date(timeInMillis).toString());

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
