package com.scott.vp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.scott.vp.base.BaseActivity;
import com.scott.vp.base.MyBaseAdapter;
import com.scott.vp.bean.MovieBean;
import com.scott.vp.bean.VideoBean;
import com.scott.vp.managers.DataManager;
import com.scott.vp.managers.DisplayImageOptionsManager;
import com.scott.vp.utils.Contants;
import com.scott.vp.utils.ImageUtils;
import com.scott.vp.utils.LogUtils;
import com.scott.vp.utils.PlayRecordUtil;
import com.scott.vp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * MovieDetailActivity
 * 电影详情界面
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
 * Created by scott on 18/10/25 21:27.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class MovieDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    //立即播放  检查本地视频是否存在code
    private static final int PLAY_PING_MOVIE_URL = 0X2399;
    //立即支付  检查本地视频是否存在code
    private static final int PAY_PING_MOVIE_URL = 0X23921;
    /**
     * 从那个界面跳转过来的
     */
    public static final String FROM_ACTIVITY = "from_activity";

    public static final int FROM_SPEECH_SERVER = 0X02;
    private static final int GET_BANNER_ADV = 0X233;
    private static final int PAY_REQEUST_CODE = 0X1231;
    private static final int GET_OTHER_MOVIE_CODE = 0121312;
    //控件
    private ImageView riv_thumb;
    private TextView tv_moviename;
    private TextView tv_level;
    private TextView tv_releasedate;
    private TextView tv_director;
    private TextView tv_time;
    private TextView tv_type;
    private TextView tv_tostar;
    private TextView tv_country;
    private TextView lbl_country;
    private ImageView iv_source;
    private TextView tv_description;
    private Button btn_start;
    private GridView gv_othermovie;
    private ImageView iv_new;
    //数据
    private OtherMoviesAdapter mAdapter;
    private MovieBean mMovieBean;
    private List<MovieBean> mOtherMovies = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        getMovieBeaninfo();
        initView();
        showData();
        setOtherMovies();
    }

    private void getMovieBeaninfo() {
        if (getIntent() == null) {
            return;
        }
        Bundle bundle = getIntent().getBundleExtra(BUNDLE);
        if (bundle != null) {
            mMovieBean = (MovieBean) bundle.getSerializable(MovieBean.KEY);
        } else {
            Uri uri = getIntent().getData();
            LogUtils.info("schema", "-----uri=" + uri.toString());
            if (null == uri) {
                return;
            } else {
                String movieId = uri.getQueryParameter("movieId");
                mMovieBean = new MovieBean();
                mMovieBean.id = Integer.parseInt(movieId);
            }
        }
    }

    private void initView() {
        iv_new = (ImageView) findViewById(R.id.iv_new);
        riv_thumb = (ImageView) findViewById(R.id.riv_thumb);
        tv_moviename = (TextView) findViewById(R.id.tv_moviename);
        tv_level = (TextView) findViewById(R.id.tv_level);
        tv_releasedate = (TextView) findViewById(R.id.tv_releasedate);
        tv_director = (TextView) findViewById(R.id.tv_director);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_tostar = (TextView) findViewById(R.id.tv_tostar);
        lbl_country = (TextView) findViewById(R.id.lbl_country);
        tv_country = (TextView) findViewById(R.id.tv_country);
        iv_source = (ImageView) findViewById(R.id.iv_source);
        tv_description = (TextView) findViewById(R.id.tv_description);
        btn_start = (Button) findViewById(R.id.btn_start);
        gv_othermovie = (GridView) findViewById(R.id.gv_othermovie);

        setTouchListener();
        btn_start.setOnClickListener(this);


        gv_othermovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
                if (position == mOtherMovies.size() - 1) {
                    // TODO: 2019-09-03 跳转到所有电影
                    ToastUtils.show(getApplicationContext(), "等待实现...");
//                    Bundle bundle1 = new Bundle();
//                    MovieTypeBean movieTypeBean = new MovieTypeBean();
//                    movieTypeBean.id = mMovieBean.parentTypeId;
//                    movieTypeBean.name = mMovieBean.parentTypeName;
//                    bundle1.putSerializable(MovieTypeBean.KEY, movieTypeBean);
//                    changeView(MovieListActivity.class, bundle1);
                } else {
                    MovieBean bean = mOtherMovies.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(MovieBean.KEY, mOtherMovies.get(position));
                    if (bean.type == 1) {
//                        changeView(SeriesDetailsActivity.class, bundle, true);
                    } else {
                        changeView(MovieDetailActivity.class, bundle, true);
                    }
                }
            }
        });

        gv_othermovie.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    gv_othermovie.setSelection(-1);
                }
            }
        });

    }


    private void setTouchListener() {
        btn_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    LogUtils.info(TAG, "执行了btn_start的onTouch方法");
//                    pingMovieUrl(PLAY_PING_MOVIE_URL);
                    playVideo();
                }
                return false;
            }
        });


    }


    private void playVideo() {
        if (TextUtils.isEmpty(mMovieBean.trailer)) {
            mMovieBean.trailer = "http://obs-401.obs.cn-north-1.myhwclouds.com/4.1/%E9%A2%84%E5%91%8A%E5%A4%A7%E4%BA%BA%E7%89%A9.mp4";
        }


        Bundle bundle = new Bundle();
        List<VideoBean> list = new ArrayList<>();
        VideoBean videoBean = new VideoBean();
        videoBean.url = mMovieBean.trailer;
        list.add(videoBean);
        mMovieBean.video = list;
        bundle.putSerializable(MovieBean.KEY, mMovieBean);
        bundle.putSerializable(VideoBean.KEY, mMovieBean.video.get(0));
        changeView(PlayerActivity.class, bundle);


//        if (mMovieBean.video != null && mMovieBean.video.size() > 0) {
//            if (!TextUtils.isEmpty(mMovieBean.video.get(0).url)) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(MovieBean.KEY, mMovieBean);
//                bundle.putSerializable(VideoBean.KEY, mMovieBean.video.get(0));
//                changeView(PlayerActivity.class, bundle);
//            } else {
//                ToastUtils.show(this, R.string.str_video_url_isnull);
//            }
//
//        } else {
//            ToastUtils.show(this, R.string.str_video_url_isnull);
//        }
    }


    private void setOtherMovies() {
        List<MovieBean> recommends = DataManager.getInstance().getRecommends();
        if (recommends != null && recommends.size() > 0) {
            mOtherMovies.clear();
            if (recommends.size() > 8) {
                mOtherMovies.addAll(recommends.subList(0, 8));
            } else {
                mOtherMovies.addAll(recommends);
            }
            mAdapter = new OtherMoviesAdapter(this, mOtherMovies);
            gv_othermovie.setAdapter(mAdapter);
        }
        mHandler.sendEmptyMessageDelayed(0, 500);
    }


    private void showData() {
        if (mMovieBean == null) {
            return;
        }
        if (!TextUtils.isEmpty(mMovieBean.image)) {
            if (!isFinishing()) {
                ImageLoader.getInstance().displayImage(mMovieBean.image, riv_thumb, DisplayImageOptionsManager.getInstance().getOptions450x604());
            }
        }
        tv_moviename.setText(mMovieBean.title);
        tv_level.setText(mMovieBean.score + "");
        tv_releasedate.setText(mMovieBean.releaseTime);
        tv_director.setText(mMovieBean.director);
        tv_time.setText(mMovieBean.duration);
        tv_type.setText(mMovieBean.typeName);
        tv_tostar.setText(mMovieBean.cast);

        descriptionFromat();
        tv_level.setText(mMovieBean.score + "分");

        if (TextUtils.isEmpty(mMovieBean.sourceIcon)) {
            lbl_country.setText("国家地区 : ");
            tv_country.setVisibility(View.VISIBLE);
            tv_country.setText(mMovieBean.country);
            iv_source.setVisibility(View.GONE);
        } else {
            lbl_country.setText("来源 : ");
            tv_country.setVisibility(View.GONE);
            iv_source.setVisibility(View.VISIBLE);
            if (!isFinishing()) {
                ImageLoader.getInstance().displayImage(mMovieBean.sourceIcon, iv_source);
            }
        }


//        if (mMovieBean.trailer != null) {
//            if (mMovieBean.video.size() > 1) {
//                btn_start.setVisibility(View.GONE);
//            } else {
//                btn_start.setVisibility(View.VISIBLE);
//            }
//        } else {
//            ToastUtils.show(getApplicationContext(), "没有视频播放资源");
//        }


        if (PlayRecordUtil.getPlayRecordTime(this, mMovieBean.id, -1) > 0) {
            btn_start.setBackgroundResource(R.drawable.start_btn_selector2);
        } else {
            btn_start.setBackgroundResource(R.drawable.start_btn_selector);
        }


        if (mMovieBean.badgeType != -1) {
            iv_new.setVisibility(View.VISIBLE);
            iv_new.setImageResource(ImageUtils.resIdFromMovieType(mMovieBean.badgeType));
        } else {
            iv_new.setVisibility(View.GONE);
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gv_othermovie.clearFocus();
            btn_start.setFocusable(true);
            btn_start.requestFocus();
            btn_start.setFocusableInTouchMode(true);
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                LogUtils.info(TAG, "执行了btn_start的onClick方法");
                playVideo();
                break;

        }
    }


    public class OtherMoviesAdapter extends MyBaseAdapter<MovieBean> {

        public OtherMoviesAdapter(Context mContext, List<MovieBean> mList) {
            super(mContext, mList);
        }

        @Override
        protected View makeView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_othermovie_item, parent, false);
            }

            TextView tv_moviename = getViewById(convertView, R.id.tv_moviename);
            ImageView civ = getViewById(convertView, R.id.icv_thumb);
            ImageView iv_newtag = getViewById(convertView, R.id.iv_newtag);
            MovieBean bean = mList.get(position);
            if (position == mList.size() - 1) {
                ImageLoader.getInstance().displayImage(Contants.DETAIL_ALL_MOVIE_IMAGE_URL, civ, DisplayImageOptionsManager.getInstance().getOptions450x604());
                tv_moviename.setVisibility(View.GONE);
                iv_newtag.setVisibility(View.GONE);

            } else {
                tv_moviename.setText(bean.title);
                if (!TextUtils.isEmpty(bean.image)) {
                    ImageLoader.getInstance().displayImage(bean.image, civ, DisplayImageOptionsManager.getInstance().getOptions450x604());
                }

                if (bean.badgeType != -1) {
                    iv_newtag.setVisibility(View.VISIBLE);
                    iv_newtag.setImageResource(ImageUtils.resIdFromMovieType(bean.badgeType));
                } else {
                    iv_newtag.setVisibility(View.GONE);
                }
            }

            return convertView;
        }
    }

    public void descriptionFromat() {
        String data = mMovieBean.description;
        if (data.length() > 130) {
            data = data.substring(0, 130);
        }
        StringBuilder desc = new StringBuilder("<font color=\"#FFFFFF\">" + data + "... </font>");
        tv_description.setText(Html.fromHtml(desc.toString()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.info(TAG, "执行了onDestroy.......");
    }
}
