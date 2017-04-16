package com.michaeltroger.sensorrecording;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjoe64.graphview.GraphView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Graph mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graphView = (GraphView) findViewById(R.id.graph);
        mGraph = new Graph(graphView);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        mSensor = sensors.get(0);

        LinearLayout availableSensorsContainer = (LinearLayout) findViewById(R.id.available_sensors);
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        int id = 0;
        for(final Sensor sensor : sensors){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(sensor.getName());
            radioButton.setId(id++);
            radioGroup.addView(radioButton);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSensorManager.unregisterListener(MainActivity.this);
                    mSensor = sensor;
                    mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    mGraph.reset();
                }
            });
        }
        availableSensorsContainer.addView(radioGroup);

        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mGraph.reset();
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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
