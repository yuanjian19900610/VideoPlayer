package com.scott.vp.managers;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.scott.vp.R;
import com.scott.vp.bean.IndexBean;
import com.scott.vp.bean.MovieBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <pre>
 * com.scott.vp.managers
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
 * Created by scott on 2019-09-03.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class DataManager {

    private static DataManager INSTANCE;

    private DataManager() {
    }

    private IndexBean mIndexBean;

    private List<MovieBean> mRecommends;

    public static synchronized DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }


    public IndexBean getIndexBean() {
        return mIndexBean;
    }

    public List<MovieBean> getRecommends() {
        return mRecommends;
    }


    public void loadData(Context context){
        if (context == null) {
            return;
        }
        getChannelData(context);
        getRecommends(context);
    }

    private void getChannelData(final Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String data = readAssetText(context, "channel.json");
                try {
                    mIndexBean = JSON.parseObject(data, IndexBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getRecommends(Context context) {
        String data = readAssetText(context, "recommend.json");
        try {
            mRecommends = JSONArray.parseArray(data, MovieBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 读取assets中的文本文件到string
     *
     * @param context  context
     * @param fileName assets下的文件名
     * @return string ,"" if empty
     */
    public String readAssetText(Context context, String fileName) {
        StringBuilder json = new StringBuilder();
        InputStreamReader streamReader = null;
        BufferedReader bufferedReader = null;
        try {
            String line = "";
            streamReader = new InputStreamReader(context.getAssets().open(fileName));
            bufferedReader = new BufferedReader(streamReader);
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json.toString();
    }
}
