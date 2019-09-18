package com.scott.vp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scott.vp.base.BaseActivity;
import com.scott.vp.bean.MovieBean;
import com.scott.vp.bean.VideoBean;
import com.scott.vp.player.IRenderView;
import com.scott.vp.player.IjkVideoView;
import com.scott.vp.utils.LogUtils;
import com.scott.vp.utils.PlayRecordUtil;
import com.scott.vp.utils.ToastUtils;
import com.scott.vp.weigit.CommonDialog;
import com.scott.vp.weigit.VerticalSeekBar;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * <pre>
 * PlayerActivity
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
 * Created by scott on 2019-08-28 20:19.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class PlayerActivity extends BaseActivity {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    //控制播放界面声音和快进快退的方向。
    public static final boolean IS_NORMAL = true;

    /**
     * 显示视频控制面板［更新播放视频，并显示title部分和底部控制部分；隐藏界面中间视频加载部分；如果视频已支付，并且isShowRestart＝＝false，则隐藏引导视图］
     */
    private static final int SHOW_VIDEO_CONTROL_VIEW = 0X01;
    /**
     * 隐藏视频控制面板[隐藏title视图，界面中间加载视图，底部视频控制视图；如果视频已支付，则隐藏支付引导视图]
     */
    private static final int Hide_VIDEO_CONTROL_VIEW = 0X02;
    /**
     * 刷新控制视图底部的视频播放时间
     */
    private static final int REFRESH_PLAY_TIME = 0X03;
    /**
     * 检查是否支付 [如果视频未支付，播放到5分钟时，需要暂停视频，弹出支付选择对话框，如果未到5分钟，则间隔5秒检查一次]
     */
    private static final int CHECK_PAY = 0X04;
    /**
     * 隐藏上次播放记录提示[如果视频未支付， 则显示支付引导内容]
     */
    private static final int HIDE_LAST_PLAY_RECORD_VIEW = 0X05;

    /**
     * SeekBar 最大值
     */
    private static final int SEEKBAR_MAX_VALUE = 1000;
    /**
     * 底部控制视频视图默认显示时间
     */
    private static final int SHOW_TIME = 5000;

    /**
     * 底部控制视频视频默认显示的时间
     */
    private static final int SHOW_NORMAL_TIME = 5000;

    /**
     * 引导视图显示从头播放的时间
     */
    private static final int SHOW_RESTART_TIME = 10000;

    /**
     * 试看视频时间
     */
    private static final int TESTPLAY_VIDEO_TIME = 300000;

    //快进快退时间
    private static final int FORWORD_BACK_TIME = 60 * 1000;

    //控件
    private RelativeLayout fl_main;
    private IjkVideoView video_view;
    private RelativeLayout rl_main_control;
    private LinearLayout layout_title;
    private ImageView iv_back;
    private TextView tv_videoname;
    private RelativeLayout rl_tip;
    private TextView tv_tip;
    private Button btn_pay;
    private RelativeLayout rl_center;
    private ImageView iv_center_status;
    private TextView tv_center_tip;
    private ProgressBar pb_loading;
    private LinearLayout layout_video_control;
    private ImageView iv_video_status;
    private SeekBar sb_video_progress;
    private TextView tv_current_time;
    private TextView tv_total_time;
    private ImageView image_back;

    /**
     * 电影实体对象
     */
    private MovieBean movieBean;
    private VideoBean mVideoBean;

    //playerSupport 标识视频库是否加载成功
    private boolean playerSupport;
    /**
     * 上次播放的记录
     */
    private long lastPlayTime;
    /**
     * 是否第一次加载视频资源
     */
    private boolean isFirstLoadingResource = false;
    /**
     * title部分是否显示从头播放引导
     */
    private boolean isShowRestartGuideView = false;

    /**
     * title部分是否显示支付引导
     */
    private boolean isShowPayGuideView = false;

    private AudioManager mAudioManager;

    /**
     * 当前视频播放的时长： 在按下快进，快退时进行赋值
     */
    private int currentPostion;
    /**
     * 视频总时长： 在按下快进，快退时进行赋值
     */
    private int duration;
    /**
     * 当前是否处于快进，快退中
     */
    private boolean isSpeeding;

    private float startY;//手指刚开始滑动时记录点 Y轴
    private float startX;//手指刚开始滑动时记录点 X轴
    private Vibrator vibrator;//振动
    private GestureDetector mGestureDetector;
    private int screenWidth, screenHeight;
    private int maxVolume;
    private int mVolume;
    private int touchRang;
    private float lastY;
    private float lastX;

    private VerticalSeekBar vsb_voice;
    private TextView tv_voice;
    private int VOICE_SEEKBAR_MAX_VALUE = 100;
    private LinearLayout layout_voice_seekbar;
    private LinearLayout layout_voice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        init();
        initloadingVideoShowView();
        initPlayer();
        playVideo();


    }


    /**
     * 开始播放视频
     */
    private void playVideo() {
        video_view.setAspectRatio(IRenderView.AR_MATCH_PARENT);
        if (playerSupport) {
            if (mVideoBean != null) {
                if (movieBean.type == 0) {
                    lastPlayTime = PlayRecordUtil.getPlayRecordTime(this, movieBean.id, -1);
                } else {
                    lastPlayTime = PlayRecordUtil.getPlayRecordTime(this, movieBean.id, mVideoBean.id);
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.player_url_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            LogUtils.info(TAG, "播放地址:" + mVideoBean.url);
            video_view.setVideoPath(mVideoBean.url);
            video_view.start();
            if (lastPlayTime > 0) {//
                video_view.seekTo((int) lastPlayTime);
            }
            showPlayRecordAndPay();
        } else {
            LogUtils.info(TAG, "不支持该影片格式播放");
        }
    }


    /**
     * 显示视频播放记录
     * ［隐藏title视图，隐藏界面中间视图，显示支付引导视图］
     */
    private void showPlayRecordAndPay() {
        if (lastPlayTime > 0) {//如果有播放记录，则快进到上次播放的地方
            isShowRestartGuideView = true;
            //显示10秒从头开始播放的引导视图
            tipShowView("您上次观看到" + generateTime(lastPlayTime) + "，系统默认继续播放，如果需要从头播放请点击", "从头开始");
            mHandler.sendEmptyMessageDelayed(HIDE_LAST_PLAY_RECORD_VIEW, SHOW_RESTART_TIME);
            showViewControlTime(SHOW_NORMAL_TIME);
        } else {
            //判断是否付费，如果未付费，则显示付费引导视图
            if (movieBean.payState == 0) {
                isShowPayGuideView = true;
                //未付费
                tipShowView("可免费试看5分钟，点击付费观看完整版", "付费看完整版");
                showViewControlTime(SHOW_NORMAL_TIME);
            }
        }
    }

    //tv version
    private void changePlayerStatus() {
        if (video_view.isPlaying()) {//视频正在播放
            pause();
        } else {
            start();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            //1.顶部的支付引导是否显示
            //   1.1显示从头播放
            //   1.2显示立即支付
            //2.暂停
            if (!isShowRestartGuideView && !isShowPayGuideView) {
                if (video_view.isPlaying()) {
                    pause();
                } else {
                    start();
                }
            } else {
                guideOperate();
            }

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (IS_NORMAL) {
                //减声音
                controlVudio(AudioManager.ADJUST_LOWER);
            } else {
                setBack(FORWORD_BACK_TIME);
            }

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (IS_NORMAL) {
                //加声音
                controlVudio(AudioManager.ADJUST_RAISE);
            } else {
                setSpeed(FORWORD_BACK_TIME);
            }

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (IS_NORMAL) {
                setSpeed(FORWORD_BACK_TIME);
            } else {
                //加声音
                controlVudio(AudioManager.ADJUST_RAISE);
            }

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (IS_NORMAL) {
                setBack(FORWORD_BACK_TIME);
            } else {
                //减声音
                controlVudio(AudioManager.ADJUST_LOWER);
            }

        } else if ((keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) || keyCode == KeyEvent.KEYCODE_HOME) {
            //返回，弹出对话框，提升是否结束播放
            if (video_view != null && video_view.isPlaying()) {
                pause();
            }
            showExistDailog();

        }
        return super.onKeyDown(keyCode, event);
    }


    private void setSpeed(int time) {
        LogUtils.debug(TAG, "-----------onKeyDown_up=" + isSpeeding);
        if (!isSpeeding) {
            //视频总时间
            duration = video_view.getDuration();
            //当前播放的时间
            currentPostion = video_view.getCurrentPosition();
            isSpeeding = true;
            mHandler.removeMessages(REFRESH_PLAY_TIME);
        }
        LogUtils.debug(TAG, "-----------onKeyDown_up_currentPostion=" + currentPostion);
        //快进
        videoProgressOperate(1, time);
    }

    private void setBack(int time) {
        LogUtils.debug(TAG, "-----------onKeyDown donw=" + isSpeeding);
        if (!isSpeeding) {
            //视频总时间
            duration = video_view.getDuration();
            //当前播放的时间
            currentPostion = video_view.getCurrentPosition();
            isSpeeding = true;
            mHandler.removeMessages(REFRESH_PLAY_TIME);
        }
        LogUtils.debug(TAG, "-----------onKeyDown down currentPostion=" + currentPostion);
        //快退
        videoProgressOperate(-1, time);
    }

    /**
     * 控制播放声音
     *
     * @param isAdjustRaise：声音改变的方向，AudioManager.ADJUST_RAISE：增加声音， AudioManager.ADJUST_LOWER：降低声音
     */
    private void controlVudio(int isAdjustRaise) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        }
        try {
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, isAdjustRaise, AudioManager.FX_FOCUS_NAVIGATION_UP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (IS_NORMAL) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                LogUtils.debug(TAG, "-----------onKeyUp=" + isSpeeding);
                Log.i(TAG, "-----------按键松开onKeyUp=" + isSpeeding);
                video_view.seekTo(currentPostion);
                isSpeeding = false;

                //快进快退
                start();
                showViewControlTime(SHOW_TIME);
            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                LogUtils.debug(TAG, "-----------onKeyUp=" + isSpeeding);
                Log.i(TAG, "-----------按键松开onKeyUp=" + isSpeeding);
                video_view.seekTo(currentPostion);
                isSpeeding = false;

                //快进快退
                start();
                showViewControlTime(SHOW_TIME);
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    /**
     * 视频快进，快退操作
     *
     * @param operateType:操作类型，1表示快进， －1表示快退
     * @param time                    :操作时间
     */
    private void videoProgressOperate(int operateType, int time) {
        //快进限制
        int remainTime = duration - currentPostion;
        if (operateType > 0 && time >= remainTime) {
            time = remainTime;
        }
        //快退限制
        if (operateType < 0 && currentPostion < time) {
            time = 0;
        }
        long delta = 0;
        if (operateType > 0) {
            delta = time;
        } else {
            delta = -time;
        }
        long newPosition = delta + currentPostion;
        Log.i(TAG, "-new@@@@-执行newPosition=" + newPosition);
        if (newPosition >= 0 && newPosition <= duration) {
            Log.i(TAG, "-快进快退-执行newPosition=" + newPosition);
            currentPostion = (int) newPosition;
            refreshCurrentTime(currentPostion);
        }
    }

    /**
     * 视频快进，快退操作
     *
     * @param operateType:操作类型，1表示快进， －1表示快退
     */
    private void videoProgressOperate(int operateType) {
        float percent = operateType == 1 ? 0.15f : -0.15f;

        long deltaMax = Math.min(100 * 1000, duration - currentPostion);
        long delta = (long) (deltaMax * percent);
        long newPosition = delta + currentPostion;
        Log.i(TAG, "-new@@@@-执行newPosition=" + newPosition);
//        if (newPosition < 0) {
//            newPosition = currentPostion;
//        } else if (newPosition > duration) {
//            newPosition = duration;
//        }
        if (newPosition >= 0 && newPosition <= duration) {
            Log.i(TAG, "-快进快退-执行newPosition=" + newPosition);
            currentPostion = (int) newPosition;
            refreshCurrentTime(currentPostion);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlDeviceLightAndVolume(event);
        return super.onTouchEvent(event);
    }


    private float maxLimitValue = 100f;
    private float targetValue = 50f;
    /*触碰滑动标识， 水平滑动到一定值后，判断未快进，快退，并将该值设置为true*/
    private boolean touchSlideTag = false;

    private void controlDeviceLightAndVolume(MotionEvent event) {
        float endY;
        float endX;
        float distanceY = 0;
        float distanceX = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下
                isSpeeding = true;
                showVideoControlView();
                //1.按下时记录相关值
                startY = event.getY();
                startX = event.getX();
                mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                touchRang = Math.min(screenWidth, screenHeight);//固定为横屏 则屏幕高度小于屏幕宽度
                break;
            case MotionEvent.ACTION_MOVE://手指滑动
                //2.滑动时记录相关值
                endY = event.getY();
                endX = event.getX();
                distanceY = startY - endY;//滑动距离
                distanceX = endX - startX;
                float changedValueY = distanceY % maxLimitValue;
                float changedValueX = distanceX % maxLimitValue;
//                LogUtils.debug("scott", "MotionEvent ACTION_MOVE 快进快退  distanceX=" + Math.abs(distanceX) + ", distanceY=" + Math.abs(distanceY) + " ,changedValueX=" + changedValueX + ", endX=" + endX + ",lastX=" + lastX + "，fazhi=" + Math.abs(changedValueY % maxLimitValue));
                if (Math.abs(distanceX) > Math.abs(distanceY)) {//快进快退控制
//                    LogUtils.info("scott","----执行快进快退控制");
                    int progress = sb_video_progress.getProgress();
                    if (lastY != 0 && Math.abs(distanceY) < 100 && lastX != 0 && Math.abs(distanceX) > 8) {
                        LogUtils.info("scott", "------快进快退操作：" + (Math.abs(changedValueX % maxLimitValue)) + ",changedValueX=" + changedValueX);
                        if (lastX > endX && Math.abs(changedValueX % maxLimitValue) >= 80) {
                            //快退
                            if (progress - Math.abs(changedValueX) <= 0) {
                                refreshCurrentTime(0);
                            } else {
                                LogUtils.debug("scott", "------MotionEvent ACTION_MOVE  快退：" + (progress - Math.abs(changedValueX)) + ",progress=" + progress);
                                float changeProgress = progress - Math.abs(changedValueX);
                                long newPosition = (long) ((changeProgress * video_view.getDuration()) / SEEKBAR_MAX_VALUE);
                                refreshCurrentTime(newPosition);
                            }
                            touchSlideTag = true;
                        } else if (lastX < endX && Math.abs(changedValueX % maxLimitValue) >= 80) {
                            //快进
                            if (progress + Math.abs(changedValueX) >= video_view.getDuration()) {
                                refreshCurrentTime(video_view.getDuration());
                            } else {
                                LogUtils.debug("scott", "-------MotionEvent ACTION_MOVE  快进：" + (progress + Math.abs(changedValueX)) + ",progress=" + progress);
                                float changeProgress = progress + Math.abs(changedValueX);
                                long newPosition = (long) ((changeProgress * video_view.getDuration()) / SEEKBAR_MAX_VALUE);
                                refreshCurrentTime(newPosition);
                            }
                            touchSlideTag = true;
                        }
                    }
                } else {//声音控制
                    LogUtils.info("scott", "----声音控制");
                    if (endX < screenWidth / 2) {
                        //左边屏幕 调节亮度
                        final double FLING_MIN_DISTANCE = 0.5;
                        final double FLING_MIN_VELOCITY = 0.5;
                        if (distanceY > FLING_MIN_DISTANCE
                                && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                            LogUtils.warn(TAG, "MotionEvent up  增加。。。。distanceY=" + distanceY + " ,touchRang=" + touchRang);
                            setBrightness(20);
                        }
                        if (distanceY < FLING_MIN_DISTANCE
                                && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                            LogUtils.warn(TAG, "MotionEvent down 减少。。。。distanceY=" + distanceY + " ,touchRang=" + touchRang);
                            setBrightness(-20);
                        }
                    } else {
                        layout_voice_seekbar.setVisibility(View.GONE);
                        //右边屏幕  调节声音
                        //改变的音量= （滑动屏幕的距离/屏幕宽度 ）* 音量的最大值
                        if (Math.abs(distanceY) < touchRang / 7) {
                            return;
                        }
                        //最终音量= 原来的音量 + 改变的音量
//                    int volume = (int) Math.min(Math.max(mVolume + changedVolume, 0), maxVolume);
//                    LogUtils.warn(TAG, "---MotionEvent changedVolume=" + changedVolume + " ,distanceY=" + distanceY + ",endY" + endY + ",lastY=" + lastY + " ,touchRang=" + touchRang + "  ,maxVolume=" + maxVolume + ", quyu=" + (Math.abs(changedVolume % maxLimitValue)));
                        if (changedValueY != 0) {
                            if (lastY >= endY && Math.abs(changedValueY % maxLimitValue) >= 30) {
                                //加声音
                                controlVudio(AudioManager.ADJUST_RAISE);
                            } else if (lastY < endY && Math.abs(changedValueY % maxLimitValue) >= 30) {
                                controlVudio(AudioManager.ADJUST_LOWER);
                            }
                            lastY = endY;
                            //更新声音
//                       mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                        }
                    }
                }
                lastX = endX;
                lastY = endY;
                break;
            case MotionEvent.ACTION_UP://手指离开
                isSpeeding = false;
                endY = event.getY();
                endX = event.getX();
                distanceY = startY - endY;//滑动距离
                distanceX = endX - startX;
                LogUtils.debug("scott", "--MotionEvent.ACTION_UP distanceX=" + Math.abs(distanceX) + ",distanceY=" + Math.abs(distanceY));
                if (touchSlideTag && Math.abs(distanceX) > Math.abs(distanceY)) {//快进快退控制
                    LogUtils.debug("scott", "--MotionEvent.ACTION_UP progress=" + sb_video_progress.getProgress());
                    progressSlide(sb_video_progress.getProgress());
                    touchSlideTag = false;
                }
                if (video_view.isPlaying()) {
                    //如果显示底部控制视图， 并且视频处于播放种，则刷新时间
                    mHandler.removeMessages(REFRESH_PLAY_TIME);
                    mHandler.sendEmptyMessage(REFRESH_PLAY_TIME);
                }
                touchScreenShowView();//隐藏控制面板的消息
                break;
        }
    }

    /**
     * 更改屏幕亮度
     *
     * @param brightness
     */
    private void setBrightness(float brightness) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = layoutParams.screenBrightness + brightness / 255.0f;
        if (layoutParams.screenBrightness > 1) {
            layoutParams.screenBrightness = 1;
        } else if (layoutParams.screenBrightness < 0.2) {
            layoutParams.screenBrightness = 0.2f;
        }
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {10, 200};
        vibrator.vibrate(pattern, -1);
        LogUtils.warn(TAG, "layoutParamsBrightness=" + layoutParams.screenBrightness);
        getWindow().setAttributes(layoutParams);
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        //加载so文件
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            playerSupport = true;
        } catch (Throwable e) {
            isFirstLoadingResource = false;
            LogUtils.info(TAG, "加载视频播放的so库异常");
            e.printStackTrace();
        }
        if (!playerSupport) {
            ToastUtils.show(getApplicationContext(), getString(R.string.not_support));
            isFirstLoadingResource = false;
            return;
        }

        video_view.setTrailer(false);
        //播放完成
        video_view.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                isFirstLoadingResource = false;
                if (movieBean.type == 1) {//电视剧
                    VideoBean video = getNextVideo(mVideoBean);
                    if (video != null) {
                        //保存已经播放过的
                        PlayRecordUtil.savePlayRecord(PlayerActivity.this, movieBean, video);
                        PlayerActivity.this.finish();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(MovieBean.KEY, movieBean);
                        bundle.putSerializable(VideoBean.KEY, video);
                        changeView(PlayerActivity.class, bundle);

                    }
                } else {
                    PlayRecordUtil.clearPlayRecordTimeByMovieId(PlayerActivity.this, movieBean.id);
                    PlayerActivity.this.finish();
                }
            }
        });

        //播放错误
        video_view.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                LogUtils.info(TAG, "播放错误:what=" + what + ",extra=" + extra);
                isFirstLoadingResource = false;
                layout_title.setVisibility(View.VISIBLE);
                if (movieBean.payState == 1) {
                    rl_tip.setVisibility(View.GONE);
                }
                layout_video_control.setVisibility(View.GONE);
                setBackVisible(View.VISIBLE);
                rl_center.setVisibility(View.GONE);
                ToastUtils.show(getApplicationContext(), "请检查您的网络是否正常!");
                return false;
            }
        });

        //加载信息
        video_view.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START://开始缓冲
                        LogUtils.info(TAG, "--- IMediaPlayer.MEDIA_INFO_BUFFERING_START");
                        loadingVideoShowView(true);
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END://缓冲结束
                        LogUtils.info(TAG, "--- IMediaPlayer.MEDIA_INFO_BUFFERING_END");
                        //显示底部视频控制视图
                        mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
                        mHandler.sendEmptyMessageDelayed(Hide_VIDEO_CONTROL_VIEW, SHOW_TIME);
                        loadingVideoShowView(false);
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:  //显示 下载速度
                        //显示 下载速度
                        LogUtils.info(TAG, "--- IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:///五秒后隐藏影片加载图片
                        LogUtils.info(TAG, "--- IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START");
                        isFirstLoadingResource = false;
                        mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
                        mHandler.removeMessages(SHOW_VIDEO_CONTROL_VIEW);
                        mHandler.sendEmptyMessageDelayed(SHOW_VIDEO_CONTROL_VIEW, 1500);
                        mHandler.sendEmptyMessage(REFRESH_PLAY_TIME);
                        //开启轮训，间隔5秒判断是否已经付费
                        if (movieBean.payState == 0) {
                            mHandler.removeMessages(CHECK_PAY);
                            mHandler.sendEmptyMessageDelayed(CHECK_PAY, 5000);
                        }
                        //修改界面中央部的背景颜色。［解决视频已经加载成功，但initFirstLoadingResource视图还显示的bug］
                        rl_center.setBackgroundColor(Color.TRANSPARENT);
                        showPlayRecordAndPay();
                        //设置视频的缩放方式，这里设置为全屏
                        //648的设备需要软解码，并且时使用AR_ASPECT_WRAP_CONTENT模式
                        if ((Build.BOARD.equals("PRE") || Build.BOARD.equals("almond"))) {
                            video_view.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);

                        } else if (Build.BOARD.equals("bigfish") || Build.BOARD.equals("p201")) {//长虹和905盒子硬解码 AR_ASPECT_FIT_PARENT
                            video_view.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
                        } else {//其他硬解码 AR_MATCH_PARENT 模式
                            video_view.setAspectRatio(IRenderView.AR_MATCH_PARENT);
                        }

                        break;
                }
                return false;
            }
        });


    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFirstLoadingResource) {
                return;
            }
            LogUtils.debug(TAG, "－－－－－－－handler msg.what=" + msg.what);
            switch (msg.what) {
                case SHOW_VIDEO_CONTROL_VIEW://显示控制视图
                    LogUtils.info(TAG, "----!!---显示控制视图，what" + msg.what);
                    showVideoControlView();
                    if (video_view.isPlaying()) {
                        //如果显示底部控制视图， 并且视频处于播放种，则刷新时间
                        mHandler.removeMessages(REFRESH_PLAY_TIME);
                        mHandler.sendEmptyMessage(REFRESH_PLAY_TIME);
                    }
                    break;
                case Hide_VIDEO_CONTROL_VIEW://隐藏控制视图
                    LogUtils.info(TAG, "----!!---隐藏控制视图，what" + msg.what);
                    if (video_view.isPlaying()) {
                        HideVideoControlView();
                        mHandler.removeMessages(REFRESH_PLAY_TIME);
                    }
                    break;
                case REFRESH_PLAY_TIME://显示控制视图，刷新当前播放时间
                    LogUtils.info(TAG, "----!!---显示控制视图，刷新当前播放时间-!!--isSpeeding=" + isSpeeding);
                    if (!isSpeeding) {//11-14 处理快进过程中，光标乱跳的效果
                        LogUtils.info(TAG, "---------显示控制视图，刷新当前播放时间---currentPostiont=" + video_view.getCurrentPosition() + ",progess=" + sb_video_progress.getProgress() + ",fz=" + (sb_video_progress.getProgress() * video_view.getDuration() / SEEKBAR_MAX_VALUE));
                        if (sb_video_progress.getProgress() != 0) {
                            int currentProgress = sb_video_progress.getProgress() * video_view.getDuration() / SEEKBAR_MAX_VALUE;
                            if (video_view.getCurrentPosition() < currentProgress) {
                                LogUtils.info(TAG, "使用seekbar的值:" + currentProgress);
                                refreshCurrentTime(video_view.getCurrentPosition(), currentProgress);
                            } else {
                                LogUtils.info(TAG, "当前播放的值:" + currentProgress);
                                refreshCurrentTime(video_view.getCurrentPosition());
                            }

                        } else {
                            refreshCurrentTime(video_view.getCurrentPosition());
                        }
                        mHandler.removeMessages(REFRESH_PLAY_TIME);
                        //如果playing ，需要更新time
                        if (video_view.isPlaying()) {
                            mHandler.sendEmptyMessageDelayed(REFRESH_PLAY_TIME, 1000);
                        }
                    }
                    break;
                case CHECK_PAY://检查是否付费
                    checkPay();
                    break;
                case HIDE_LAST_PLAY_RECORD_VIEW://隐藏从头播放视图
                    isShowRestartGuideView = false;
                    if (movieBean.payState == 0) {
                        isShowPayGuideView = true;
                        tipShowView("可免费试看5分钟，点击付费观看完整版", "付费看完整版");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 检查是否支付
     * [如果视频未支付，播放到5分钟时，需要暂停视频，弹出支付选择对话框，如果未到5分钟，则间隔5秒检查一次]
     */
    private void checkPay() {
        int position = video_view.getCurrentPosition();
        LogUtils.debug(TAG, "checkPay（）" + position + "  paystate=" + movieBean.payState);
        if (movieBean.payState == 0) {
            if (position >= TESTPLAY_VIDEO_TIME) {
                Log.i(TAG, "时间到了，请付费");
                pause();
                mHandler.removeMessages(CHECK_PAY);
                return;
            }
            mHandler.removeMessages(CHECK_PAY);
            mHandler.sendEmptyMessageDelayed(CHECK_PAY, 5000);
        } else {
            isShowPayGuideView = false;
            mHandler.removeMessages(CHECK_PAY);
        }
    }

    private void init() {
        movieBean = (MovieBean) getIntent().getBundleExtra(BUNDLE).getSerializable(MovieBean.KEY);
        mVideoBean = (VideoBean) getIntent().getBundleExtra(BUNDLE).getSerializable(VideoBean.KEY);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        fl_main = (RelativeLayout) findViewById(R.id.fl_main);
        video_view = (IjkVideoView) findViewById(R.id.play_video_view);
        rl_main_control = (RelativeLayout) findViewById(R.id.rl_main_control);
        layout_title = (LinearLayout) findViewById(R.id.layout_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_videoname = (TextView) findViewById(R.id.tv_videoname);
        rl_tip = (RelativeLayout) findViewById(R.id.rl_tip);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        rl_center = (RelativeLayout) findViewById(R.id.rl_center);
        iv_center_status = (ImageView) findViewById(R.id.iv_center_status);
        tv_center_tip = (TextView) findViewById(R.id.tv_center_tip);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        layout_video_control = (LinearLayout) findViewById(R.id.layout_video_control);
        iv_video_status = (ImageView) findViewById(R.id.iv_video_status);
        sb_video_progress = (SeekBar) findViewById(R.id.sb_video_progress);
        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
        tv_total_time = (TextView) findViewById(R.id.tv_total_time);
        image_back = (ImageView) findViewById(R.id.image_back);
        layout_voice = (LinearLayout) findViewById(R.id.layout_voice);
        layout_voice_seekbar = (LinearLayout) findViewById(R.id.layout_voice_seekbar);
        vsb_voice = (VerticalSeekBar) findViewById(R.id.vsb_voice);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
        vsb_voice.setMax(VOICE_SEEKBAR_MAX_VALUE);
        sb_video_progress.setMax(SEEKBAR_MAX_VALUE);
        vsb_voice.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * VOICE_SEEKBAR_MAX_VALUE / maxVolume);
        tv_voice.setText(vsb_voice.getProgress() + "%");
        image_back.setVisibility(View.VISIBLE);
        layout_voice.setVisibility(View.VISIBLE);
        //type=0 表示电影 1表示连续剧
        if (movieBean.type == 0) {
            tv_videoname.setText(movieBean.title);
        } else {
            tv_videoname.setText(movieBean.title + " " + mVideoBean.name);
        }

        layout_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vsb_voice.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * VOICE_SEEKBAR_MAX_VALUE / maxVolume);
                tv_voice.setText(vsb_voice.getProgress() + "%");
                layout_voice_seekbar.setVisibility(View.VISIBLE);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框
                showExistDailog();
            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出对话框
                showExistDailog();
            }
        });

        findViewById(R.id.iv_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出对话框
                showExistDailog();
            }
        });

        iv_video_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePlayerStatus();
            }
        });

        iv_center_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePlayerStatus();
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guideOperate();
            }
        });

        sb_video_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                LogUtils.debug(TAG, "拖动中=" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogUtils.debug(TAG, "拖动开始=" + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtils.debug(TAG, "--------拖动停止=" + seekBar.getProgress());
                progressSlide(seekBar.getProgress());
                if (movieBean.payState == 0) {
                    mHandler.removeMessages(CHECK_PAY);
                    mHandler.sendEmptyMessage(CHECK_PAY);
                }
            }
        });

        vsb_voice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (mAudioManager != null) {
                    tv_voice.setText(progress + "%");
                    int voiceValue = progress * maxVolume / VOICE_SEEKBAR_MAX_VALUE;
                    LogUtils.debug(TAG, "-------声音值=" + voiceValue + "，maxVolume=" + maxVolume + ",progress=" + progress);
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, voiceValue, 0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    /**
     * 引导操作
     * ［如果引导视图显示的是从头播放， 则快进到从头播放， 如果引导视图显示的是支付视图，则弹出支付对话框］
     */
    private void guideOperate() {
        if (btn_pay.getText().toString().trim().equals("付费看完整版")) {
            if (movieBean.payState == 0) {
//                pay();
//                pingMovieUrl();

                playVideo();

            }
        } else {
            seekToPosition(0);
        }
    }

    private void seekToPosition(int time) {
        long duration = video_view.getDuration();
        LogUtils.info(TAG, "广播对于的time=" + time + "，duration=" + duration);
        isShowRestartGuideView = false;
        if (time == 0) {
            sb_video_progress.setProgress(time);
        } else {
            long pos = SEEKBAR_MAX_VALUE * time / duration;
            LogUtils.info(TAG, "广播对于的pos=" + pos);
            sb_video_progress.setProgress((int) pos);
        }
        video_view.seekTo(time);
        video_view.start();
        mHandler.removeMessages(HIDE_LAST_PLAY_RECORD_VIEW);
        mHandler.sendEmptyMessage(HIDE_LAST_PLAY_RECORD_VIEW);
        showViewControlTime(SHOW_TIME);
    }

    /**
     * 属性拖动进度
     *
     * @param progress
     */
    private void progressSlide(int progress) {
        long duration = video_view.getDuration();
        long newPosition = (progress * duration) / SEEKBAR_MAX_VALUE;
        LogUtils.debug("scott", "--current newPosition=" + newPosition);
        //没按一次，快进一点，如果一直按，则松开按钮才会设置对应到播放点
        video_view.seekTo((int) newPosition);
        //解决进度条，快进，快退松开是， 进度条进度跳动问题
        refreshCurrentTime(video_view.getCurrentPosition());
        showViewControlTime(SHOW_TIME);
    }


    private void pause() {
        if (video_view == null) {
            return;
        }
        video_view.pause();
        //切换图标
        iv_video_status.setImageResource(R.drawable.icon_stop);
        //需要一直显示视频控制面板和title
        layout_title.setVisibility(View.VISIBLE);
        rl_center.setVisibility(View.VISIBLE);
        iv_center_status.setVisibility(View.VISIBLE);
        tv_center_tip.setVisibility(View.GONE);
        pb_loading.setVisibility(View.GONE);
        mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
        mHandler.removeMessages(SHOW_VIDEO_CONTROL_VIEW);
        showViewControlTime(Integer.MAX_VALUE);

    }

    private void start() {
        if (video_view == null) {
            return;
        }
        video_view.start();
        //切换图标
        iv_video_status.setImageResource(R.drawable.icon_start);
        iv_center_status.setVisibility(View.GONE);
        //5秒后隐藏视频控制面板和title
        mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
        mHandler.sendEmptyMessageDelayed(Hide_VIDEO_CONTROL_VIEW, SHOW_TIME);
        mHandler.removeMessages(REFRESH_PLAY_TIME);
        mHandler.sendEmptyMessage(REFRESH_PLAY_TIME);
    }


    /**
     * 退出播放对话框
     */
    private void showExistDailog() {
        CommonDialog dialog = new CommonDialog(this);
        dialog.setOnBtnClickListener(new CommonDialog.BtnClickListener() {
            @Override
            public void onClickSure() {
                //保存当前的播放时间
                PlayRecordUtil.savePlayRecordTime(PlayerActivity.this, movieBean, mVideoBean, video_view.getCurrentPosition());
                finish();
            }

            @Override
            public void onClickCancel() {
                if (video_view != null) {
                    start();
                }

            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (video_view != null) {
                    start();
                }
            }
        });
        dialog.show();
    }


    private void refreshCurrentTime(long currentPostion) {
        refreshCurrentTime(currentPostion, currentPostion);
    }

    /**
     * 刷新控制视图底部的视频播放时间
     */
    private void refreshCurrentTime(long currentPostion, long sbProgress) {
        long duration = video_view.getDuration();
        if (sb_video_progress != null) {
            long pos = SEEKBAR_MAX_VALUE * sbProgress / duration;
            //设置缓冲位置
            if (video_view.isPlaying()) {
                if (duration > 0) {
                    sb_video_progress.setProgress((int) pos);
                    LogUtils.info(TAG, "设置sb_video_progress进度" + pos + ", progerss=" + sb_video_progress.getProgress() + ",currentPOsition=" + currentPostion + ",sbProgreess=" + sbProgress);
                }
            } else {
                LogUtils.info(TAG, "设置sb_video_progress进度" + pos);
                sb_video_progress.setProgress((int) pos);
            }
        }
        LogUtils.info(TAG, "---刷新时间:" + generateTime(currentPostion));
        tv_current_time.setText(generateTime(currentPostion));
        tv_total_time.setText(generateTime(duration));

    }

    /**
     * 格式化时间
     *
     * @param time 毫秒
     * @return
     */
    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }


    //获取连续剧的下一集对象
    private VideoBean getNextVideo(VideoBean videoBean) {
        VideoBean video = null;
        for (int i = 0; i < movieBean.video.size(); i++) {
            if (movieBean.video.get(i).id == videoBean.id) {
                if (i != movieBean.video.size() - 1) {
                    video = movieBean.video.get(i + 1);
                }
                break;
            }
        }
        return video;
    }


    /**************视图显示控制*********************/

    /**
     * 播放记录和付费引导视图
     * ［隐藏title视图，隐藏界面中间视图，显示支付引导视图］
     *
     * @param tipStr:引导视图中的提示文字
     * @param btnStr:引导视图中btn上的文字
     */
    private void tipShowView(String tipStr, String btnStr) {
        if (isFirstLoadingResource) {
            return;
        }
        rl_main_control.setVisibility(View.VISIBLE);
        setBackVisible(View.VISIBLE);
        layout_title.setVisibility(View.GONE);
        rl_tip.setVisibility(View.VISIBLE);
        tv_tip.setText(tipStr);
        btn_pay.setText(btnStr);
        rl_center.setVisibility(View.GONE);
    }

    /**
     * 触控屏幕显示视图
     * [显示title视图，底部控制视频视图]
     */
    private void touchScreenShowView() {
        if (isFirstLoadingResource) {
            return;
        }
        rl_main_control.setVisibility(View.VISIBLE);
        setBackVisible(View.VISIBLE);
        layout_title.setVisibility(View.VISIBLE);
        rl_center.setVisibility(View.GONE);
        layout_video_control.setVisibility(View.VISIBLE);

        if (video_view.isPlaying()) {
            mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
            mHandler.sendEmptyMessageDelayed(Hide_VIDEO_CONTROL_VIEW, SHOW_TIME);
        }
    }

    /**
     * 开始播放时，加载视频资源显示视图
     */
    private void initloadingVideoShowView() {
        isFirstLoadingResource = true;
        firstLoadingVideoResourceView();
        String title;
        if (movieBean.type == 0) {
            title = movieBean.title;
        } else {
            title = movieBean.title + " " + mVideoBean.name;
        }
        tv_videoname.setText(title);
        rl_center.setBackgroundColor(Color.parseColor("#000000"));
        tv_center_tip.setText("《" + title + "》视频加载中\n祝您观影愉快");
        tv_center_tip.setTextSize(40);

    }

    /**
     * 隐藏 支付引导，底部视频控制视图，显示视频title部分和加载部分视图
     */
    private void firstLoadingVideoResourceView() {
        rl_main_control.setVisibility(View.VISIBLE);
        layout_title.setVisibility(View.VISIBLE);
        rl_tip.setVisibility(View.GONE);
        rl_center.setVisibility(View.VISIBLE);
        iv_center_status.setVisibility(View.GONE);
        layout_video_control.setVisibility(View.GONE);
        setBackVisible(View.GONE);
        pb_loading.setVisibility(View.GONE);
        tv_center_tip.setVisibility(View.VISIBLE);
    }


    /**
     * 加载视频，拖动进度条，显示的视图
     * ［isLoading=true:显示界面中央视频加载视图，显示中央加载进度框，隐藏视频title视图］
     * ［isLoading=false:隐藏界面中央视频加载视图，显示中央加载进度框，隐藏视频title视图］
     * 如果已支付，则隐藏，支付引导视图
     *
     * @param isLoading：是否加载视频
     */
    private void loadingVideoShowView(boolean isLoading) {
        rl_center.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        pb_loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        layout_title.setVisibility(View.GONE);
        if (movieBean.payState == 1) {
            rl_tip.setVisibility(View.GONE);
        }
        iv_center_status.setVisibility(View.GONE);
        tv_center_tip.setVisibility(View.GONE);


        if (!video_view.isPlaying()) {
            start();
        }

    }

    /**
     * 隐藏视频控制视图
     * [隐藏title视图，界面中间加载视图，底部视频控制视图；如果视频已支付，则隐藏支付引导视图]
     */
    private void HideVideoControlView() {
        layout_voice_seekbar.setVisibility(View.GONE);
        if (isSpeeding) {
            showViewControlTime(SHOW_TIME);
        } else {
            layout_title.setVisibility(View.GONE);
            if (movieBean.payState == 1) {
                rl_tip.setVisibility(View.GONE);
            }
            rl_center.setVisibility(View.GONE);
            layout_video_control.setVisibility(View.GONE);
            setBackVisible(View.GONE);
        }

    }

    /**
     * 显示视频控制视图，更新播放视频
     * [显示title部分，底部控制部分，隐藏界面中间视频加载部分]
     * ［如果视频已支付，isShowRestartGuideView＝false，则隐藏引导视图］
     */
    private void showVideoControlView() {
        layout_voice_seekbar.setVisibility(View.VISIBLE);
        layout_title.setVisibility(View.VISIBLE);
        if (movieBean.payState == 1 && !isShowRestartGuideView) {
            rl_tip.setVisibility(View.GONE);
        }
        rl_center.setVisibility(View.GONE);
        layout_video_control.setVisibility(View.VISIBLE);
        setBackVisible(View.VISIBLE);
        tv_current_time.setText(generateTime(video_view.getCurrentPosition()));
        tv_total_time.setText(generateTime(video_view.getDuration()));
        mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
        mHandler.sendEmptyMessageDelayed(Hide_VIDEO_CONTROL_VIEW, SHOW_TIME);
    }

    /**
     * 显示控制视图
     * ［如果视频处于播放中， 则需要刷新播放时间］
     *
     * @param showTime：显示时间
     */
    private void showViewControlTime(long showTime) {
        mHandler.removeMessages(Hide_VIDEO_CONTROL_VIEW);
        mHandler.sendEmptyMessage(SHOW_VIDEO_CONTROL_VIEW);
        mHandler.sendEmptyMessageDelayed(Hide_VIDEO_CONTROL_VIEW, showTime);
        if (video_view.isPlaying()) {
            mHandler.removeMessages(REFRESH_PLAY_TIME);
            mHandler.sendEmptyMessageDelayed(REFRESH_PLAY_TIME, 1000);
        }

    }

    private void setBackVisible(int visible) {
        image_back.setVisibility(visible);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (video_view != null) {
            video_view.stopPlayback();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
