package home.oleg.rcremote.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UploadService extends Service {
    public static String SERVER_ADDRESS_BUNDLE_KEY = "server_address";

    private Thread uploadThread;

    public UploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String serverAdress = intent.getStringExtra(SERVER_ADDRESS_BUNDLE_KEY);

        uploadThread = new Thread(new UploadRunnable(serverAdress));
        uploadThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        uploadThread.interrupt();
    }
}
