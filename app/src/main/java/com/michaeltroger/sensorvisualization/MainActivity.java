package com.michaeltroger.sensorvisualization;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjoe64.graphview.GraphView;

import java.util.List;

import de.psdev.licensesdialog.LicensesDialog;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Graph mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GraphView graphView = findViewById(R.id.graph);
        mGraph = new Graph(graphView);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        final List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        mSensor = sensors.get(0);

        final RadioGroup radioGroup = findViewById(R.id.available_sensors);
        setupRadioButtons(sensors, radioGroup);

        final RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
    }

    private void setupRadioButtons(@NonNull final List<Sensor> sensors, @NonNull final RadioGroup radioGroup) {
        int id = 0;
        for(final Sensor sensor : sensors){
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setText(sensor.getName());
            radioButton.setId(id++);
            radioGroup.addView(radioButton);

            radioButton.setOnClickListener(v -> {
                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = sensor;
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                mGraph.reset();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGraph.reset();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mGraph.printSensorData(event.timestamp, event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // accuracy is expected to not change
    }

    public void showLicenseInfo(@NonNull final View view) {
        new LicensesDialog.Builder(this)
                .setNotices(R.raw.notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }
}
