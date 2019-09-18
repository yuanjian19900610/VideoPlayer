package com.scott.vp.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.scott.vp.bean.MovieBean;
import com.scott.vp.bean.PlayRecordBean;
import com.scott.vp.bean.VideoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanjian on 17/10/16.
 */

public class PlayRecordUtil {

    public static final String RECORD = "play_record";

    public static final String PLAY_TIME_RECORD = "play_time_record";

    /**
     * 获取播放记录
     *
     * @param context
     * @param movieId
     * @return
     */
    public static List<Integer> getRecords(Context context, int movieId) {
        List<Integer> mList = new ArrayList<>();
        try {
            ACache mCache = ACache.get(context);
            String record = mCache.getAsString(RECORD);
            if (!TextUtils.isEmpty(record)) {
                Map<Integer, List<Integer>> map = JSON.parseObject(record, new TypeReference<Map<Integer, List<Integer>>>() {
                });
                if (map != null && map.size() > 0) {
                    mList = map.get(movieId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mList;
    }

    /**
     * 检查播放记录
     *
     * @return
     */
    public static List<VideoBean> checkIsPlay(Context context, MovieBean movieBean, List<VideoBean> listVideo) {
        List<Integer> recordList = getRecords(context, movieBean.id);
        if (recordList != null && recordList.size() > 0) {
            for (int i = 0; i < listVideo.size(); i++) {
                for (int j = 0; j < recordList.size(); j++) {
                    if (listVideo.get(i).id == recordList.get(j)) {
                        listVideo.get(i).isPlay = true;
                        break;
                    }
                }
            }
        }
        return listVideo;
    }

    /**
     * 保存播放记录
     *
     * @param context
     * @param movieBean
     * @param video
     */
    public static void savePlayRecord(Context context, MovieBean movieBean, VideoBean video) {
        try {
            ACache mCache = ACache.get(context);
            String record = mCache.getAsString(RECORD);
            if (TextUtils.isEmpty(record)) {
                Map<Integer, List<Integer>> map = new HashMap<>();
                List<Integer> listVideo = new ArrayList<>();
                listVideo.add(video.id);
                map.put(movieBean.id, listVideo);
                mCache.put(RECORD, JSON.toJSONString(map));
            } else {
                Map<Integer, List<Integer>> map = JSON.parseObject(record, new TypeReference<Map<Integer, List<Integer>>>() {
                });
                if (map != null && map.size() > 0) {
                    List<Integer> mList = map.get(movieBean.id);
                    if (mList != null) {
                        boolean isExist = false;
                        int existIndex = -1;
                        for (int i = 0; i < mList.size(); i++) {
                            LogUtils.info("smarhit004", "已经添加的listitem value=" + mList.get(i) + " ,current video.id=" + video.id);
                            if (mList.get(i).intValue() == video.id) {
                                existIndex = i;
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            LogUtils.info("smarhit004", "添加播放集数old list=" + mList.size());
                            mList.add(video.id);
                            map.put(movieBean.id, mList);
                            LogUtils.info("smarhit004", "添加播放集数list=" + mList.size());
                            mCache.put(RECORD, JSON.toJSONString(map));
                        } else {
                            if (existIndex != -1) {
                                mList.remove(existIndex);
                                mList.add(video.id);
                                map.put(movieBean.id, mList);
                                mCache.put(RECORD, JSON.toJSONString(map));
                            }
                        }
                    } else {
                        List<Integer> listVideo = new ArrayList<>();
                        listVideo.add(video.id);
                        map.put(movieBean.id, listVideo);
                        mCache.put(RECORD, JSON.toJSONString(map));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取播放记录
     *
     * @param context
     * @param movieId
     * @return
     */
    public static List<PlayRecordBean> getRecords1(Context context, int movieId) {
        List<PlayRecordBean> mList = new ArrayList<>();
        try {
            ACache mCache = ACache.get(context);
            String record = mCache.getAsString(RECORD);
            if (!TextUtils.isEmpty(record)) {
                Map<Integer, List<PlayRecordBean>> map = JSON.parseObject(record, new TypeReference<Map<Integer, List<PlayRecordBean>>>() {
                });
                if (map != null && map.size() > 0) {
                    mList = map.get(movieId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mList;
    }

    /**
     * 检查播放记录
     *
     * @return
     */
    public static List<VideoBean> checkIsPlay1(Context context, MovieBean movieBean, List<VideoBean> listVideo) {
//        List<PlayRecordBean> recordList = getRecords(context, movieBean.id);
//        if (recordList != null && recordList.size() > 0) {
//            for (int i = 0; i < listVideo.size(); i++) {
//                for (int j = 0; j < recordList.size(); j++) {
//                    if (listVideo.get(i).id == recordList.get(j).videoId) {
//                        listVideo.get(i).isPlay = true;
//                        break;
//                    }
//                }
//            }
//        }
        return listVideo;
    }


    /**
     * 保存播放记录［连续剧只保存最后一次播放的］
     *
     * @param context
     * @param movieBean
     * @param videoBean
     * @param currentPosition：当前播放的时间
     */
    public static void savePlayRecordTime(Context context, MovieBean movieBean, VideoBean videoBean, long currentPosition) {
        try {
            ACache mAcache = ACache.get(context);
            String playrecord = mAcache.getAsString(PLAY_TIME_RECORD);
            if (!TextUtils.isEmpty(playrecord)) {
                List<PlayRecordBean> listRecords = JSON.parseObject(playrecord, new TypeReference<List<PlayRecordBean>>() {
                });
                LogUtils.info("scott", "总定的播放纪录size＝" + listRecords.size());
                if (listRecords != null && listRecords.size() > 0) {
                    //先找出已经存在的记录
                    int existIndex = -1;
                    for (int i = 0; i < listRecords.size(); i++) {
                        LogUtils.info("scott", "---------播放纪录＝" + listRecords.get(i).toString());
                        if (listRecords.get(i).movieId == movieBean.id) {
                            LogUtils.info("scott", "-----＃＃＃----当前播放定播放纪录＝" + listRecords.get(i).toString());
                            existIndex = i;
                        }
                    }
                    //移除已经存在的记录
                    if (existIndex != -1) {
                        listRecords.remove(existIndex);
                    }

                    //添加新的记录
                    PlayRecordBean pr = new PlayRecordBean();
                    pr.movieId = movieBean.id;
                    pr.lastPosition = currentPosition;
                    if (movieBean.type == 1) {//连续剧
                        pr.videoId = videoBean.id;
                        pr.videoName = videoBean.name;
                    }
                    listRecords.add(pr);
                    LogUtils.info("scott", "------添加后的---播放纪录size=" + listRecords.size());
                    mAcache.put(PLAY_TIME_RECORD, JSON.toJSONString(listRecords));

                } else {

                    List<PlayRecordBean> lists = new ArrayList<>();
                    PlayRecordBean pr = new PlayRecordBean();
                    pr.movieId = movieBean.id;
                    pr.lastPosition = currentPosition;
                    if (movieBean.type == 1) {//连续剧
                        pr.videoId = videoBean.id;
                        pr.videoName = videoBean.name;
                    }
                    lists.add(pr);
                    LogUtils.info("scott", "---没有任何记录---添加后的---播放纪录size=" + lists.size());
                    mAcache.put(PLAY_TIME_RECORD, JSON.toJSONString(lists));
                }
            } else {
                List<PlayRecordBean> lists = new ArrayList<>();
                PlayRecordBean pr = new PlayRecordBean();
                pr.movieId = movieBean.id;
                pr.lastPosition = currentPosition;
                if (movieBean.type == 1) {//连续剧
                    pr.videoId = videoBean.id;
                    pr.videoName = videoBean.name;
                }
                lists.add(pr);
                LogUtils.info("scott", "---第一次---添加后的---播放纪录size=" + lists.size());
                mAcache.put(PLAY_TIME_RECORD, JSON.toJSONString(lists));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据电影id和videoid获取播放时间
     *
     * @param context
     * @param movieId
     * @param videoId
     * @return
     */
    public static long getPlayRecordTime(Context context, int movieId, int videoId) {

        try {
            ACache mAcache = ACache.get(context);
            String playrecord = mAcache.getAsString(PLAY_TIME_RECORD);
            LogUtils.info("scott", movieId + "的播放纪录＝" + playrecord);
            if (!TextUtils.isEmpty(playrecord)) {
                List<PlayRecordBean> listRecords = JSON.parseObject(playrecord, new TypeReference<List<PlayRecordBean>>() {
                });
                if (listRecords != null && listRecords.size() > 0) {
                    //先找出已经存在的记录
                    int existIndex = -1;
                    for (int i = 0; i < listRecords.size(); i++) {
                        if (listRecords.get(i).movieId == movieId) {
                            existIndex = i;
                        }
                    }
                    //已经存在的记录
                    if (existIndex != -1) {
                        if (videoId < 0) {
                            return listRecords.get(existIndex).lastPosition;
                        } else {
                            if (videoId == listRecords.get(existIndex).videoId) {
                                return listRecords.get(existIndex).lastPosition;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 根据movieid清空记录
     *
     * @param context
     * @param movieId
     */
    public static void clearPlayRecordTimeByMovieId(Context context, int movieId) {
        try {
            ACache mAcache = ACache.get(context);
            String playrecord = mAcache.getAsString(PLAY_TIME_RECORD);
            LogUtils.info("scott", movieId + "的播放纪录＝" + playrecord);
            if (!TextUtils.isEmpty(playrecord)) {
                List<PlayRecordBean> listRecords = JSON.parseObject(playrecord, new TypeReference<List<PlayRecordBean>>() {
                });
                if (listRecords != null && listRecords.size() > 0) {
                    //先找出已经存在的记录
                    int existIndex = -1;
                    for (int i = 0; i < listRecords.size(); i++) {
                        if (listRecords.get(i).movieId == movieId) {
                            existIndex = i;
                        }
                    }
                    //已经存在的记录
                    if (existIndex != -1) {
                        listRecords.remove(existIndex);
                        mAcache.put(PLAY_TIME_RECORD, JSON.toJSONString(listRecords));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
