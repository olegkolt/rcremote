package home.oleg.rcremote.client;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by oleg on 2/16/17.
 */

public class UploadRunnable implements Runnable {
    private static OkHttpClient client = new OkHttpClient();

    private String host;

    public UploadRunnable(String host) {
        this.host = host;
    }

    @Override
    public void run() {

        while (ClientActivity.isClientActive) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(host).newBuilder();
            urlBuilder.addQueryParameter("x", String.valueOf(ClientActivity.mainX));
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                Log.d("Response", response.code() + "");
            } catch (IOException ex) {
                Log.d("Exception", ex.getMessage());
            }
        }
    }
}
