package com.scott.vp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scott.vp.R;
import com.scott.vp.bean.ChannelBean;
import com.scott.vp.interfaces.RecyclerviewItemClickListener;
import com.scott.vp.managers.DisplayImageOptionsManager;
import com.scott.vp.weigit.FocusFrameLayout;
import com.scott.vp.weigit.RoundRectImageView;

import java.util.List;

/**
 * 一级分类适配器
 * Created by yuanjian on 17/9/27.
 */

public class GoodMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ChannelBean> mList;
    private RecyclerviewItemClickListener mRecyclerviewItemClickListener;
    private LayoutInflater mInflater;

    public GoodMovieAdapter(Context mContext, List<ChannelBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setOnRecyclerviewItemClickListener(RecyclerviewItemClickListener mRecyclerviewItemClickListener) {
        this.mRecyclerviewItemClickListener = mRecyclerviewItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(mInflater.inflate(R.layout.layout_goodmovie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MovieViewHolder movieHolder = (MovieViewHolder) holder;
        movieHolder.ffl_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerviewItemClickListener != null) {
                    mRecyclerviewItemClickListener.onItemClick(view, position);
                }
            }
        });

        ChannelBean channelBean = mList.get(position);
        if(!TextUtils.isEmpty(channelBean.image)){
            ImageLoader.getInstance().displayImage(channelBean.image,movieHolder.riv_thumb, DisplayImageOptionsManager.getInstance().getOptions450x604());
        }
        movieHolder.tv_moviename.setText(channelBean.title);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class MovieViewHolder extends RecyclerView.ViewHolder {

        private FocusFrameLayout ffl_movie;
        private TextView tv_moviename;
        private ImageView iv_selected;
        private RoundRectImageView riv_thumb;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ffl_movie = (FocusFrameLayout) itemView.findViewById(R.id.ffl_movie);
            tv_moviename = (TextView) itemView.findViewById(R.id.tv_moviename);
            riv_thumb= (RoundRectImageView) itemView.findViewById(R.id.riv_thumb);
            iv_selected = (ImageView) itemView.findViewById(R.id.iv_selected);
            ffl_movie.setMyFocusChangeListener(new FocusFrameLayout.MyFocusChangeListener() {
                @Override
                public void focusChanged(boolean isFocus) {
                    if (isFocus) {
                        if (getAdapterPosition() == 0) {
                            ffl_movie.setNextFocusLeftId(R.id.ffl_movie);
                        }

                        if (getAdapterPosition() == mList.size() - 1) {
                            ffl_movie.setNextFocusRightId(R.id.ffl_movie);
                        }
                        iv_selected.setVisibility(View.VISIBLE);
                    } else {
                        iv_selected.setVisibility(View.GONE);
                    }
                }
            });


        }
    }

}
