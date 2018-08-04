package com.zcm.library.uimonitor;

import android.util.Log;
import android.view.Choreographer;

/**
 * Create by zcm on 2018/8/3 下午4:03
 */
public class FPSFrameCallback implements Choreographer.FrameCallback {
    private static final String TAG = "FPSFrameCallback";
    private long mLastFrameTimeNanos = 0;
    private long mFrameIntervalNanos;

    public FPSFrameCallback(long lastFrameTimeNanos) {
        mLastFrameTimeNanos = lastFrameTimeNanos;
        mFrameIntervalNanos = (long) (1000000000 / 60.0);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if (mLastFrameTimeNanos == 0) {
            mLastFrameTimeNanos = frameTimeNanos;
        }
        long jitterNanos = frameTimeNanos - mLastFrameTimeNanos;
        if (jitterNanos >= mFrameIntervalNanos) {
            long skippedFrames = jitterNanos / mFrameIntervalNanos;
            if (skippedFrames > 10) {
                Log.e(TAG, "doFrame: Skipped  " + skippedFrames + "  frames!");
            }
        }
        mLastFrameTimeNanos = frameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
    }
}
