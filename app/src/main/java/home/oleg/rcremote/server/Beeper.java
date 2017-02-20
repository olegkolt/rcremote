package home.oleg.rcremote.server;

import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Created by oleg on 2/20/17.
 */

public class Beeper {
    private static final int FIRST_LEVEL_BEEP_INTERVAL = 500;
    private static final int SECOND_LEVEL_BEEP_INTERVAL = 250;

    private static final int FIRST_LEVEL_EDGE = 4;
    private static final int SECOND_LEVEL_EDGE = 6;

    private long lastBeep = 0;

    private ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);


    public void onUpdate(float x) {
        x = Math.abs(x);

        if (x < FIRST_LEVEL_EDGE) {
            return;
        }
        int interval = findInterval(x);
        long now = System.currentTimeMillis();
        if ((lastBeep + interval) < now) {
            beep();
            lastBeep = now;
        }
    }

    private int findInterval(float x) {
        if (x >= SECOND_LEVEL_EDGE) {
            return SECOND_LEVEL_BEEP_INTERVAL;
        }
        return FIRST_LEVEL_BEEP_INTERVAL;
    }

    private void beep() {
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }
}
