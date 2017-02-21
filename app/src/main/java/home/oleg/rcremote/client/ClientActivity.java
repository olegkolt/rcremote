package home.oleg.rcremote.client;

import android.app.Notification;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import home.oleg.rcremote.R;
import home.oleg.rcremote.server.WebServer;

public class ClientActivity extends AppCompatActivity {

    public static volatile float mainX = 0;
    public static volatile boolean isClientActive = false;

    private Button startButton;
    private Button stopButton;
    private EditText serverAddressEditText;

    private SensorManager sensorManager;

    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        serverAddressEditText = (EditText) findViewById(R.id.server_address);
        stopButton.setEnabled(false);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClientActive = true;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                Intent intent = new Intent(ClientActivity.this, UploadService.class);
                intent.putExtra(UploadService.SERVER_ADDRESS_BUNDLE_KEY, buildServerAddress());
                startService(intent);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClientActive = false;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);

                stopService(new Intent(ClientActivity.this, UploadService.class));
            }
        });
    }

    private String buildServerAddress() {
        return serverAddressEditText.getText().toString().trim() + ":" + WebServer.PORT;
    }


    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(new SensorListener(),
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();
    }

    @Override
    protected void onStop() {
        super.onStop();

        wakeLock.release();
    }
}
