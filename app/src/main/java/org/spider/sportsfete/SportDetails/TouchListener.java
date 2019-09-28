package org.spider.sportsfete.SportDetails;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by AVINASH on 2/20/2018.
 */

public abstract class TouchListener implements View.OnTouchListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;

    private long lastClickTime = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(v);
        } else {
            onSingleClick(v);
        }
        lastClickTime = clickTime;

        return false;
    }

    public abstract void onSingleClick(View v);

    public abstract void onDoubleClick(View v);

}