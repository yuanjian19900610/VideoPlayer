package com.scott.vp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scott.vp.R;
import com.scott.vp.bean.CategoryBean;
import com.scott.vp.interfaces.RecyclerviewItemClickListener;
import com.scott.vp.interfaces.ViewOnFocusChanageListener;
import com.scott.vp.managers.DisplayImageOptionsManager;
import com.scott.vp.utils.LogUtils;
import com.scott.vp.weigit.FocusFrameLayout;
import java.util.List;

/**
 *<pre>
 * SecondCategoryAdapter
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
 * Created by scott on 2019-08-29 13:40.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class SecondCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CategoryBean> mList;
    private RecyclerviewItemClickListener mRecyclerviewItemClickListener;
    private ViewOnFocusChanageListener mViewOnFocusChanageListener;

    public SecondCategoryAdapter(Context mContext, List<CategoryBean> mList, ViewOnFocusChanageListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mViewOnFocusChanageListener=listener;
    }

    public void setOnRecyclerviewItemClickListener(RecyclerviewItemClickListener mRecyclerviewItemClickListener) {
        this.mRecyclerviewItemClickListener = mRecyclerviewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieTypeViewHolder(View.inflate(mContext, R.layout.layout_second_category_item, null),mViewOnFocusChanageListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MovieTypeViewHolder typeHolder = (MovieTypeViewHolder) holder;
        LogUtils.info("111", "－－－－－二级菜单界面刷新了");
        typeHolder.ffl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerviewItemClickListener != null) {
                    mRecyclerviewItemClickListener.onItemClick(view, position);
                }
            }
        });
        ImageLoader.getInstance().displayImage(mList.get(position).image, typeHolder.iv_category, DisplayImageOptionsManager.getInstance().getOptions260x120());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class MovieTypeViewHolder extends RecyclerView.ViewHolder {

        private FocusFrameLayout ffl_main;
        private ImageView iv_category;
        private ImageView iv_focus;
        private ViewOnFocusChanageListener mViewOnFocusChanageListener;

        public MovieTypeViewHolder(View itemView,ViewOnFocusChanageListener listener) {
            super(itemView);
            this.mViewOnFocusChanageListener=listener;
            ffl_main = (FocusFrameLayout) itemView.findViewById(R.id.ffl_main);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            ffl_main.setLayoutParams(params);
            iv_category = (ImageView) itemView.findViewById(R.id.iv_category);
            iv_focus = (ImageView) itemView.findViewById(R.id.iv_focus);

            ffl_main.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(mViewOnFocusChanageListener!=null){
                        mViewOnFocusChanageListener.onFocusChange(view,b);
                    }
                    LogUtils.info("111", "二级菜单adpaer 获得光标=" + b);
                    if (b) {
                        iv_focus.setVisibility(View.VISIBLE);
                    } else {
                        iv_focus.setVisibility(View.GONE);
                    }

                }
            });


        }
    }
}
