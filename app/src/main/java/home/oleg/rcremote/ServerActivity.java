package home.oleg.rcremote;

import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import de.nitri.gauge.Gauge;
import home.oleg.rcremote.server.Beeper;
import home.oleg.rcremote.server.WebServer;

public class ServerActivity extends AppCompatActivity {
    public static final String X_BUNDLE_KEY = "x_bundle_key";

    private Gauge indicator;

    private PowerManager.WakeLock wakeLock;

    private Beeper beeper;


    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        beeper = new Beeper();

        indicator = (Gauge) findViewById(R.id.gauge);


        WebServer webServer = new WebServer();
        webServer.start(handler);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();

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

    public void updateX(float x) {

        x = x * -1;

        indicator.setValue(x);
        beeper.onUpdate(x);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            updateX(bundle.getFloat(X_BUNDLE_KEY));
        }
    };

}
