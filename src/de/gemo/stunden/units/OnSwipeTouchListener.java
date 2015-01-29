package de.gemo.stunden.units;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import de.gemo.stunden.utils.GUICreator;

/**
 * Detects left and right swipes across a view.
 */
public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener() {
        this.gestureDetector = new GestureDetector(GUICreator.APP.getApplicationContext(), new GestureListener());
    }

    public boolean onSwipeLeft() {
        return false;
    }

    public boolean onSwipeRight() {
        return false;
    }

    public boolean onSwipeUp() {
        return false;
    }

    public boolean onSwipeDown() {
        return false;
    }

    public boolean onTouch() {
        return false;
    }

    public boolean onDoubleTouch() {
        return false;
    }

    public void onLongTouch() {
    }

    public boolean onScrollHorizontal(float distance) {
        return false;
    }

    public boolean onScrollVertical(float distance) {
        return false;
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return onDoubleTouch();
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTouch();
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onLongTouch();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1 == null || e2 == null) {
                return false;
            }

            distanceX = e2.getRawX() - e1.getRawX();
            distanceY = e2.getRawY() - e1.getRawY();

            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                return onScrollHorizontal(distanceX);
            } else if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return onScrollVertical(distanceY);
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1 == null || e2 == null) {
                return false;
            }
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();

            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    return onSwipeRight();
                else
                    return onSwipeLeft();
            } else if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceY > 0)
                    return onSwipeDown();
                else
                    return onSwipeUp();
            }
            return false;
        }
    }
}
