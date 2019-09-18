package com.scott.vp.weigit;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.scott.vp.R;


/**
 * Created by yuanjian on 17/9/25.
 */

public class FocusRelativeLayout extends RelativeLayout {

    private Animation scaleSmallAnimation;
    private Animation scaleBigAnimation;

    private  MyFocusChangeListener mMyFocusChangeListener;

    public void setMyFocusChangeListener(MyFocusChangeListener mMyFocusChangeListener) {
        this.mMyFocusChangeListener = mMyFocusChangeListener;
    }

    public FocusRelativeLayout(Context context) {
        super(context);
    }

    public FocusRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if (gainFocus) {
            getRootView().invalidate();
            zoomOut();
        } else {
            zoomIn();
        }

        if(mMyFocusChangeListener!=null){
            mMyFocusChangeListener.focusChanged(gainFocus);
        }

    }

    private void zoomIn() {
        if (scaleSmallAnimation == null) {
            scaleSmallAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_small);
        }
        startAnimation(scaleSmallAnimation);
    }

    private void zoomOut() {
        if (scaleBigAnimation == null) {
            scaleBigAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale_big);
        }
        startAnimation(scaleBigAnimation);
    }

    public interface  MyFocusChangeListener{
        public void focusChanged(boolean isFocus);
    }
}
