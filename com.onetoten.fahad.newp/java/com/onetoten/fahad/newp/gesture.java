package com.onetoten.fahad.newp;

import android.app.Activity;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class gesture extends Activity {
    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;

    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);
        switch (action) {
            case R.styleable.View_android_focusable /*0*/:
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    this.mVelocityTracker.clear();
                }
                this.mVelocityTracker.addMovement(event);
                break;
            case Upload_Image.REQUEST_IMAGE_CAPTURE /*1*/:
            case R.styleable.Toolbar_subtitle /*3*/:
                this.mVelocityTracker.recycle();
                break;
            case R.styleable.View_paddingEnd /*2*/:
                this.mVelocityTracker.addMovement(event);
                this.mVelocityTracker.computeCurrentVelocity(1);
                Log.d(BuildConfig.FLAVOR, "X velocity: " + VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, pointerId));
                Log.d(BuildConfig.FLAVOR, "Y velocity: " + VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, pointerId));
                break;
        }
        return true;
    }
}
