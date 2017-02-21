package home.oleg.rcremote.client;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import home.oleg.rcremote.R;

public class UploadService extends Service {
    public static String SERVER_ADDRESS_BUNDLE_KEY = "server_address";

    private static final int FOREGROUND_ID = 777;

    private Thread uploadThread;

    public UploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(getString(R.string.upload_notification))
                .setSmallIcon(android.R.drawable.ic_dialog_info);
        Notification notification;
        if (Build.VERSION.SDK_INT < 16)
            notification = builder.getNotification();
        else
            notification = builder.build();

        startForeground(UploadService.FOREGROUND_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String serverAddress = intent.getStringExtra(SERVER_ADDRESS_BUNDLE_KEY);

        uploadThread = new Thread(new UploadRunnable(serverAddress));
        uploadThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopForeground(true);

        uploadThread.interrupt();
    }
}
