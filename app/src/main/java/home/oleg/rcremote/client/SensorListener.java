package home.oleg.rcremote.client;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import home.oleg.rcremote.ClientActivity;

/**
 * Created by oleg on 2/17/17.
 */

public class SensorListener implements SensorEventListener {
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ClientActivity.mainX = sensorEvent.values[0];
//        float axisY = event.values[1];
//        float axisZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
