package com.scott.vp;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scott.vp.adapters.GoodMovieAdapter;
import com.scott.vp.adapters.NewBannerAdapter;
import com.scott.vp.adapters.SecondCategoryAdapter;
import com.scott.vp.base.BaseFragment;
import com.scott.vp.bean.BannerBean;
import com.scott.vp.bean.CategoryBean;
import com.scott.vp.bean.ChannelBean;
import com.scott.vp.bean.ChannelEntity;
import com.scott.vp.bean.IndexBean;
import com.scott.vp.bean.MovieBean;
import com.scott.vp.bean.MovieTypeBean;
import com.scott.vp.interfaces.RecyclerviewItemClickListener;
import com.scott.vp.interfaces.ViewOnFocusChanageListener;
import com.scott.vp.interfaces.ViewOnKeyListener;
import com.scott.vp.managers.DataManager;
import com.scott.vp.utils.Contants;
import com.scott.vp.utils.LogUtils;
import com.scott.vp.utils.ToastUtils;
import com.scott.vp.weigit.MoviesView;
import com.scott.vp.weigit.ScaleRecyclerView;
import com.scott.vp.weigit.SpaceItemDecoration;
import com.scott.vp.weigit.TVLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * MainFragment
 *  首页影片
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
 * Created by scott on 18/10/25 20:29.
 *
 * *-------------------------------------------------------------------*m
 *  </pre>
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {


    private static final int GET_BANNER_ADV = 0X32120;
    private ScrollView sc_main;
    private ScaleRecyclerView srv_advs;
    private RecyclerView rv_second_category;
    private RecyclerView rv_goodmovie;
    //其它电影的根节点
    private LinearLayout layout_movies;
    private TextView tv_goodmovie_title;
    //推荐三部影片，
    private NewBannerAdapter mBannerAdapter;
    private List<BannerBean> bannerBeans = new ArrayList<>();
    //二级菜单
    private SecondCategoryAdapter mSecondCategoryAdapter;
    private List<CategoryBean> mSecondCategorys = new ArrayList<>();
    //精选电影
    private GoodMovieAdapter mGoodMovieAdapter;
    private List<ChannelBean> mGoodMovies = new ArrayList<>();
    //频道实体，
    private IndexBean mIndexBean;
    private TVLinearLayoutManager tvLinearLayoutManager;
    public int SCOLL_DISTANCE_Y;

    @Override
    public View
    onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        SCOLL_DISTANCE_Y = (int) (getActivity().getWindow().getWindowManager().getDefaultDisplay().getHeight() * 0.2);
        mIndexBean = DataManager.getInstance().getIndexBean();
        initView(view);
        updateData();
        return view;
    }

    private void initView(View view) {
        //设置布局管理器
        sc_main = (ScrollView) view.findViewById(R.id.sc_main);
        srv_advs = (ScaleRecyclerView) view.findViewById(R.id.srv_advs);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.supportsPredictiveItemAnimations();
        srv_advs.setLayoutManager(manager);
        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerView_item_space1);
        srv_advs.addItemDecoration(new SpaceItemDecoration(itemSpace));
        mBannerAdapter = new NewBannerAdapter(getActivity(), bannerBeans);
        srv_advs.setAdapter(mBannerAdapter);
        mBannerAdapter.setmRecyclerviewItemClickListener(new RecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MovieBean movieBean = mIndexBean.channel.get(position).data.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MovieBean.KEY, movieBean);
                if (movieBean.type == 0) {
                    //电影
                    changeView(MovieDetailActivity.class, bundle);
                } else {
                    //连续剧
//                    changeView(SeriesDetailsActivity.class, bundle);
                }

//                BannerBean bannerBean = bannerBeans.get(position);
//                Bundle bundle = new Bundle();
//                MovieBean mMovieBean = new MovieBean();
//                mMovieBean.id = bannerBean.movieId;
//                bundle.putSerializable(MovieBean.KEY, mMovieBean);
//                if (bannerBean.type == 0) {
//                    //电影
//                    changeView(MovieDetailActivity.class, bundle);
//                } else {
//                    //连续剧
////                    changeView(SeriesDetailsActivity.class, bundle);
//                }
            }
        });

        mBannerAdapter.setmBannerFocusListener(new NewBannerAdapter.BannerFocusListener() {
            @Override
            public void focusChanged(int position, boolean isFocus) {
                if (isFocus) {
                    if (sc_main.getScrollY() != 0) {
                        sc_main.smoothScrollTo(0, 0);
                    }
                }
            }
        });


        //二级菜单

        rv_second_category = (RecyclerView) view.findViewById(R.id.rv_second_category);
        tvLinearLayoutManager = new TVLinearLayoutManager(getActivity());
        tvLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_second_category.setLayoutManager(tvLinearLayoutManager);
        mSecondCategoryAdapter = new SecondCategoryAdapter(getActivity(), mSecondCategorys, new ViewOnFocusChanageListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus) {
                    if (sc_main.getScrollY() != 0) {
                        sc_main.smoothScrollTo(0, 0);
                    }
                }
            }
        });
        rv_second_category.setAdapter(mSecondCategoryAdapter);
        mSecondCategoryAdapter.setOnRecyclerviewItemClickListener(new RecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO:  这里可以根据二级菜单分类，跳转到对应的类型影片列表中
                ToastUtils.show(getActivity(),"等待实现...");
            }
        });


        //精选电影
        tv_goodmovie_title = (TextView) view.findViewById(R.id.tv_goodmovie_title);
        rv_goodmovie = (RecyclerView) view.findViewById(R.id.rv_goodmovie);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 4);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_goodmovie.setLayoutManager(mGridLayoutManager);
        mGoodMovieAdapter = new GoodMovieAdapter(getActivity(), mGoodMovies);
        mGoodMovieAdapter.setOnRecyclerviewItemClickListener(new RecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO: 2019-09-03 临时处理
                MovieBean movieBean = mIndexBean.channel.get(position).data.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MovieBean.KEY, movieBean);
                if (movieBean.type == 0) {
                    //电影
                    changeView(MovieDetailActivity.class, bundle);
                } else {
                    //连续剧
//                    changeView(SeriesDetailsActivity.class, bundle);
                }

//                ChannelBean channelBean = mGoodMovies.get(position);
//                Bundle bundle = new Bundle();
//                MovieBean mMovieBean = new MovieBean();
//                mMovieBean.id = channelBean.id;
//                bundle.putSerializable(MovieBean.KEY, mMovieBean);
//                if (channelBean.type == 0) {
//                    //电影
//                    changeView(MovieDetailActivity.class, bundle);
//                } else {
//                    //连续剧
////                    changeView(SeriesDetailsActivity.class, bundle);
//                }

            }
        });
        rv_goodmovie.setAdapter(mGoodMovieAdapter);
        //其它电影
        layout_movies = (LinearLayout) view.findViewById(R.id.layout_movies);

    }


    /**
     * 更新界面数据
     */
    private void updateData() {

        if (mIndexBean != null) {
            if (mIndexBean.subType != null && mIndexBean.subType.size() > 0) {
                rv_second_category.setVisibility(View.VISIBLE);
                mSecondCategorys.clear();
                CategoryBean allCategoryBean = new CategoryBean();
                allCategoryBean.image = Contants.SECOND_MENU_ALL_MOVIE_IMAGE_URL;
                mSecondCategorys.add(allCategoryBean);
                mSecondCategorys.addAll(mIndexBean.subType);
                mSecondCategoryAdapter.notifyDataSetChanged();
            } else {
                rv_second_category.setVisibility(View.GONE);
            }

            if (mIndexBean.banner != null && mIndexBean.banner.size() > 0) {
                bannerBeans.clear();
                bannerBeans.addAll(mIndexBean.banner);
                mBannerAdapter.notifyDataSetChanged();
            }

            mGoodMovies.clear();
            layout_movies.removeAllViews();
            if (mIndexBean.channel != null && mIndexBean.channel.size() > 0) {
                List<ChannelEntity> entity = mIndexBean.channel;
                try {
                    for (int i = 0; i < entity.size(); i++) {
                        MoviesView view = new MoviesView(getActivity(), new ViewOnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                //处理遥控器，用户每次按下遥控板上的上/下按键时，需要移动列表，始终让用户选中的影片处于屏幕中央
                                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                                    sc_main.smoothScrollTo(0, sc_main.getScrollY() + SCOLL_DISTANCE_Y);

                                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                                    sc_main.smoothScrollTo(0, sc_main.getScrollY() - SCOLL_DISTANCE_Y);
                                }
                                return false;
                            }
                        });
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        view.setLayoutParams(params);
                        view.setData(entity.get(i).title, entity.get(i).data);
                        layout_movies.addView(view);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            //精选电影
            if (mIndexBean.recommend != null) {
                tv_goodmovie_title.setText(mIndexBean.recommend.title);
                mGoodMovies.addAll(mIndexBean.recommend.data);
            }

            if (mGoodMovies.size() == 0) {
                rv_goodmovie.setVisibility(View.GONE);
                tv_goodmovie_title.setVisibility(View.GONE);
            } else {
                rv_goodmovie.setVisibility(View.VISIBLE);
                tv_goodmovie_title.setVisibility(View.VISIBLE);
                mGoodMovieAdapter.notifyDataSetChanged();
            }

        }
    }


    /**
     * 滑动到顶部，【当用户按返回键时，需要回到顶部】
     */
    public void gotoTop() {
        if (sc_main != null) {
            LogUtils.info("MainFragment", "----滑到顶部" + sc_main.getScrollY() + ",y=" + sc_main.getY());
            sc_main.scrollTo(0, 0);//滑到顶部
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

