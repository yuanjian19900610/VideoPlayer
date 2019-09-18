package com.scott.vp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scott.vp.R;
import com.scott.vp.bean.BannerBean;
import com.scott.vp.interfaces.RecyclerviewItemClickListener;
import com.scott.vp.managers.DisplayImageOptionsManager;
import com.scott.vp.weigit.FocusFrameLayout;
import java.util.List;

/**
 * 新的banner adapter
 * Created by yuanjian on 17/12/26.
 */

public class NewBannerAdapter extends RecyclerView.Adapter<NewBannerAdapter.BannerViewHolder> {

    private Context mContext;

    private List<BannerBean> mList;

    private LayoutInflater mInflater;

    private RecyclerviewItemClickListener mRecyclerviewItemClickListener;

    private BannerFocusListener mBannerFocusListener;


    public void setmBannerFocusListener(BannerFocusListener mBannerFocusListener) {
        this.mBannerFocusListener = mBannerFocusListener;
    }

    public void setmRecyclerviewItemClickListener(RecyclerviewItemClickListener mRecyclerviewItemClickListener) {
        this.mRecyclerviewItemClickListener = mRecyclerviewItemClickListener;
    }

    public NewBannerAdapter(Context mContext, List<BannerBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(mInflater.inflate(R.layout.layout_newadv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, final int position) {
        BannerBean bannerBean = mList.get(position);
        if (!TextUtils.isEmpty(bannerBean.image)) {
            ImageLoader.getInstance().displayImage(bannerBean.image, holder.iv_thumb, DisplayImageOptionsManager.getInstance().getOptions556x375());
        }

        holder.ffl_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerviewItemClickListener != null) {
                    mRecyclerviewItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout_movie;

        private FocusFrameLayout ffl_movie;
        private ImageView iv_thumb;
        private ImageView iv_focus;

        public BannerViewHolder(View itemView) {
            super(itemView);
            layout_movie = (LinearLayout) itemView.findViewById(R.id.layout_movie);
            ffl_movie = (FocusFrameLayout) itemView.findViewById(R.id.ffl_movie);
            iv_thumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
            iv_focus = (ImageView) itemView.findViewById(R.id.iv_focus);

            ffl_movie.setMyFocusChangeListener(new FocusFrameLayout.MyFocusChangeListener() {
                @Override
                public void focusChanged(boolean isFocus) {
                    if(mBannerFocusListener!=null){
                        mBannerFocusListener.focusChanged(0,isFocus);
                    }
                    if (isFocus) {
                        if (getAdapterPosition() == 0) {
                            ffl_movie.setNextFocusLeftId(R.id.ffl_movie);
                        }

                        if (getAdapterPosition() == mList.size() - 1) {
                            ffl_movie.setNextFocusRightId(R.id.ffl_movie);
                        }

                        iv_focus.setVisibility(View.VISIBLE);
                    } else {
                        iv_focus.setVisibility(View.GONE);
                    }


                }
            });
        }
    }

    public interface BannerFocusListener {
        public void focusChanged(int position, boolean isFocus);
    }
}
