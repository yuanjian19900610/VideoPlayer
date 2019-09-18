package com.scott.vp.weigit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scott.vp.MovieDetailActivity;
import com.scott.vp.R;
import com.scott.vp.adapters.AdvAdapter;
import com.scott.vp.bean.ChannelBean;
import com.scott.vp.bean.MovieBean;
import com.scott.vp.interfaces.RecyclerviewItemClickListener;
import com.scott.vp.interfaces.ViewOnKeyListener;
import com.scott.vp.managers.DisplayImageOptionsManager;
import com.scott.vp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuanjian on 17/9/23.
 */

public class MoviesView extends LinearLayout {

    private List<MovieBean> mListData = new ArrayList<>();
    private TextView tv_title;
    private ScaleRecyclerView srv_movies;
    private MovieAdapter mAdapter;

    private ViewOnKeyListener mMovieViewOnKeyListener;

    public MoviesView(Context context, ViewOnKeyListener mMovieViewOnKeyListener) {
        super(context);
        this.mMovieViewOnKeyListener = mMovieViewOnKeyListener;
        initView(context);
    }


    public MoviesView(Context context) {
        super(context);
        initView(context);
    }

    public MoviesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MoviesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(final Context context) {
        View view = View.inflate(context, R.layout.layout_movies, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        srv_movies = (ScaleRecyclerView) view.findViewById(R.id.srv_moves);
        GridLayoutManager manager = new GridLayoutManager(context, 6);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.supportsPredictiveItemAnimations();
        srv_movies.setLayoutManager(manager);

        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerView_item_space1);
        srv_movies.addItemDecoration(new SpaceItemDecoration(itemSpace));
        mAdapter = new MovieAdapter(context, mListData, mMovieViewOnKeyListener);
        mAdapter.setOnItemStateListener(new AdvAdapter.OnItemStateListener() {
            @Override
            public void onItemClick(View view, int position) {
                srv_movies.smoothToCenter(position);
            }
        });
        mAdapter.setRecyclerviewItemClickListener(new RecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MovieBean movieBean = mListData.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MovieBean.KEY, movieBean);
                Intent mIntent=null;
                if (movieBean.type == 0) {
                    //电影
                    mIntent = new Intent(context, MovieDetailActivity.class);
                } else {
                    //连续剧
//                    mIntent = new Intent(context, SeriesDetailsActivity.class);
                }
                if(mIntent!=null) {
                    mIntent.putExtra("bundle", bundle);
                    context.startActivity(mIntent);
                }
            }
        });
        srv_movies.setAdapter(mAdapter);


        this.addView(view);

    }


    public void setData(String title, List<MovieBean> datas) {
        mListData.clear();
        mListData.addAll(datas);
        tv_title.setText(title);
        mAdapter.notifyDataSetChanged();

    }



    class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<MovieBean> mList;
        private AdvAdapter.OnItemStateListener mListener;
        private LayoutInflater mInflater;
        private RecyclerviewItemClickListener mRecyclerviewItemClickListener;
        private ViewOnKeyListener mMovieViewOnKeyListener;

        public void setOnItemStateListener(AdvAdapter.OnItemStateListener listener) {
            mListener = listener;
        }

        public void setRecyclerviewItemClickListener(RecyclerviewItemClickListener mRecyclerviewItemClickListener) {
            this.mRecyclerviewItemClickListener = mRecyclerviewItemClickListener;
        }


        public MovieAdapter(Context mContext, List<MovieBean> mList, ViewOnKeyListener listener) {
            this.mContext = mContext;
            this.mList = mList;
            this.mMovieViewOnKeyListener = listener;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MovieViewHolder(mInflater.inflate(R.layout.layout_movie_item, parent, false), mMovieViewOnKeyListener);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.ffl_movie.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRecyclerviewItemClickListener != null) {
                        mRecyclerviewItemClickListener.onItemClick(view, position);
                    }
                }
            });
            MovieBean movieBean = mListData.get(position);

            if (!TextUtils.isEmpty(movieBean.image)) {
                ImageLoader.getInstance().displayImage(movieBean.image, movieViewHolder.riv_thumb, DisplayImageOptionsManager.getInstance().getOptions450x604());
            }
            movieViewHolder.tv_moviename.setText(movieBean.title);

            if(movieBean.badgeType!=-1){
                movieViewHolder.iv_newtag.setVisibility(VISIBLE);
                movieViewHolder.iv_newtag.setImageResource(ImageUtils.resIdFromMovieType(movieBean.badgeType));
            }else{
                movieViewHolder.iv_newtag.setVisibility(GONE);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        private class MovieViewHolder extends RecyclerView.ViewHolder {

            private FocusFrameLayout ffl_movie;
            private TextView tv_moviename;
            private ImageView riv_thumb;
            private ImageView iv_selected;
            private ImageView iv_newtag;
            private ViewOnKeyListener mMovieViewOnKeyListener;

            public MovieViewHolder(View itemView, ViewOnKeyListener listener) {
                super(itemView);
                this.mMovieViewOnKeyListener = listener;
                ffl_movie = (FocusFrameLayout) itemView.findViewById(R.id.ffl_movie);
                tv_moviename = (TextView) itemView.findViewById(R.id.tv_moviename);
                iv_selected = (ImageView) itemView.findViewById(R.id.iv_selected);
                riv_thumb = (ImageView) itemView.findViewById(R.id.riv_thumb);
                iv_newtag = (ImageView) itemView.findViewById(R.id.iv_newtag);
                ffl_movie.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (mMovieViewOnKeyListener != null) {
                            mMovieViewOnKeyListener.onKey(v, keyCode, event);
                        }
                        return false;
                    }
                });
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

}
