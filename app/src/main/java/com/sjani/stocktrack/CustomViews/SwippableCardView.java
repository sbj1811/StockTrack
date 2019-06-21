package com.sjani.stocktrack.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.cardview.widget.CardView;

public class SwippableCardView extends CardView {
    private GestureDetector mGestureDetector;

    public SwippableCardView(Context context) {
        super(context);
    }

    public SwippableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        mGestureDetector = gestureDetector;
    }
}
