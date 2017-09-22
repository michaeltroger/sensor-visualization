# Simple Real Time Sensor Visualization for Android

<img src="/screenshot.png" alt="Sensor Visualization" width="400px"/>

This simple application supports the real time visualization of all sensor data of your phone in form of a line graph.
It outputs the data just as they come in provided by Google's APIs. A legend describes what kind of data the values are.

In the case of an accelerometer, the read out data will be acceleration x-axis, y-axis and z-axis (in this order) and the unit m/s^2. The legend's text is retrieved of an open source library called [Sensor Value Legend](https://github.com/michaeltroger/sensorvaluelegend).

To get more information about the description and units of the sensor data check out: https://developer.android.com/reference/android/hardware/SensorEvent.html
Be aware that the app also doesn't feature the recording of sensor data.

Get it on Google Play:
https://play.google.com/store/apps/details?id=com.michaeltroger.sensorvisualization
