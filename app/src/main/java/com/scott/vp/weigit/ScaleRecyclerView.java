package com.scott.vp.weigit;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by yuanjian on 17/9/25.
 */

public class ScaleRecyclerView extends RecyclerView {

    private int mSelectedPosition = 0;
    private Scroller mScroller;

    public ScaleRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setChildrenDrawingOrderEnabled(true);
        mScroller=new Scroller(context);
    }

    @Override
    public void onDraw(Canvas c) {
        mSelectedPosition = getChildAdapterPosition(getFocusedChild());
        super.onDraw(c);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {

        int position = mSelectedPosition;
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {
                if (position > i) {
                    position = i;
                }
                return position;
            }

            if (i == position) {
                return childCount - 1;
            }
        }
        return i;
    }


    private int mLastx;
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller!=null && mScroller.computeScrollOffset()){
            scrollBy(mLastx - mScroller.getCurrX(), 0);
            mLastx = mScroller.getCurrX();
            postInvalidate();//让系统继续重绘，则会继续重复执行computeScroll
        }
    }

    private int mTargetPos;

    /**
     * 将指定item平滑移动到整个view的中间位置
     * @param position
     */
    public void smoothToCenter(int position){
        int parentWidth = getWidth();//获取父视图的宽度
        int childCount = getChildCount();//获取当前视图可见子view的总数
        //获取可视范围内的选项的头尾位置
        int firstvisiableposition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        int lastvisiableposition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        int count = ((LinearLayoutManager)getLayoutManager()).getItemCount();//获取item总数
        mTargetPos = Math.max(0, Math.min(count - 1, position));//获取目标item的位置（参考listview中的smoothScrollToPosition方法）
        View targetChild = getChildAt(mTargetPos-firstvisiableposition);//获取目标item在当前可见视图item集合中的位置
        View firstChild = getChildAt(0);//当前可见视图集合中的最左view
        View lastChild = getChildAt(childCount-1);//当前可见视图集合中的最右view
        int childLeftPx = targetChild.getLeft();//子view相对于父view的左边距
        int childRightPx = targetChild.getRight();//子view相对于父view的右边距


        int childWidth = targetChild.getWidth();
        int centerLeft = parentWidth/2-childWidth/2;//计算子view居中后相对于父view的左边距
        int centerRight = parentWidth/2+childWidth/2;//计算子view居中后相对于父view的右边距
        if(childLeftPx>centerLeft){//子view左边距比居中view大（说明子view靠父view的右边，此时需要把子view向左平移
            //平移的起始位置就是子view的左边距，平移的距离就是两者之差
            mLastx = childLeftPx;
            mScroller.startScroll(childLeftPx,0,centerLeft-childLeftPx,0,600);//600为移动时长，可自行设定
            postInvalidate();
        }else if(childRightPx<centerRight){
            mLastx = childRightPx;
            mScroller.startScroll(childRightPx,0,centerRight-childRightPx,0,600);
            postInvalidate();
        }


    }
}
