package com.michaeltroger.sensorvisualization;

import android.databinding.DataBindingUtil;
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

import com.michaeltroger.sensorvisualization.databinding.ActivityMainBinding;

import java.util.List;

import de.psdev.licensesdialog.LicensesDialog;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityMainBinding binding;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Graph mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setHandlers(new MyHandlers());

        mGraph = new Graph(binding.graph);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        final List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        mSensor = sensors.get(0);

        setupRadioButtons(sensors, binding.availableSensors);

        final RadioButton radioButton = (RadioButton) binding.availableSensors.getChildAt(0);
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
        mGraph.printSensorData(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // accuracy is expected to not change
    }

    public class MyHandlers {
        public void showLicenseInfo(View view) {
            new LicensesDialog.Builder(MainActivity.this)
                    .setNotices(R.raw.notices)
                    .setIncludeOwnLicense(true)
                    .build()
                    .show();
        }
    }
}
