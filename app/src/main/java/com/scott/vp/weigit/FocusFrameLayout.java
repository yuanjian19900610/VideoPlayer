package com.scott.vp.weigit;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.scott.vp.R;

/**
 *<pre>
 * FocusFrameLayout
 *
 * *-------------------------------------------------------------------*
 *     scott
 *                                    江城子 . 程序员之歌
 *     /\__/\
 *    /`    '\                     十年生死两茫茫，写程序，到天亮。
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!          千行代码，Bug何处藏。
 *    \  --  /                     纵使上线又怎样，朝令改，夕断肠。
 *   /        \                    领导每天新想法，天天改，日日忙。
 *  /          \                       相顾无言，惟有泪千行。
 * |            |                  每晚灯火阑珊处，夜难寐，加班狂。
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * Created by scott on 2019-08-29 13:30.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class FocusFrameLayout extends FrameLayout {

    private Animation scaleSmallAnimation;
    private Animation scaleBigAnimation;

    private  MyFocusChangeListener mMyFocusChangeListener;

    public void setMyFocusChangeListener(MyFocusChangeListener mMyFocusChangeListener) {
        this.mMyFocusChangeListener = mMyFocusChangeListener;
    }

    public FocusFrameLayout(Context context) {
        super(context);
    }

    public FocusFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
