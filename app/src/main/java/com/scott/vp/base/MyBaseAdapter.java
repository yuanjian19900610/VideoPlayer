package com.scott.vp.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.scott.vp.R;

import java.util.List;

/**
 * FUNCTION BaseAdapter基础类
 * Created by yuanjian on 16/12/13.
 * COPYRIGHT 厦门麦卓信息科技有限公司
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater mInflater;
    protected DisplayImageOptions options;

    public MyBaseAdapter(Context mContext, List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.icon_loading_default_450_604)
                .showImageOnLoading(R.drawable.icon_loading_default_450_604)
                .showImageForEmptyUri(R.drawable.icon_loading_default_450_604)
                .cacheInMemory(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .bitmapConfig(Bitmap.Config.ALPHA_8)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }


    public void setListData(List<T> list_data) {
        this.mList = list_data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public T getItem(int position) {
        if (mList == null || mList.size() <= position) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return makeView(position, convertView, parent);
    }


    protected <T extends View> T getViewById(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }


    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param viewGroup 要更新的listview或者viewGroup
     * @param position  要更新的位置
     */
    public void notifyDataSetChanged(AbsListView viewGroup, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = viewGroup.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = viewGroup.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = viewGroup.getChildAt(position - firstVisiblePosition);
            getView(position, view, viewGroup);
        }

    }


    /**
     * 创建listview 的子view
     *
     * @param position：当前view在listview中的index
     * @param convertView：当前view
     * @param parent
     * @return
     */
    protected abstract View makeView(int position, View convertView, ViewGroup parent);


}
