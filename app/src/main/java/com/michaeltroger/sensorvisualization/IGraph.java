package com.michaeltroger.sensorvisualization;

import android.hardware.SensorEvent;
import android.support.annotation.NonNull;

interface IGraph {
    void printSensorData(@NonNull final SensorEvent event);
    void reset();
    void setLegendVisibility(final boolean visibility);
}
