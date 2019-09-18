/*
 * Copyright (C) 2016 hejunlin <hejunlin2013@gmail.com>
 * Github:https://github.com/hejunlin2013/TVSample
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.scott.vp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.scott.vp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hejunlin on 2015/10/16.
 * blog: http://blog.csdn.net/hejjunlin
 */
public class ImageUtils {

    public static final float VER_POSTER_RATIO = 0.73f;
    public static final float HOR_POSTER_RATIO = 1.5f;


    public static void fixHorPosterRatio(final ImageView image) {
        image.post(new Runnable() {
            @Override
            public void run() {
                int width = image.getWidth();
                int height = Math.round((float) width / HOR_POSTER_RATIO);
                image.getLayoutParams().height = height;
                image.setVisibility(View.VISIBLE);
            }
        });
    }


    public static void fixVerPosterRatio(final ImageView image) {
        image.post(new Runnable() {
            @Override
            public void run() {
                int width = image.getWidth();
                int height = Math.round((float) width / VER_POSTER_RATIO);
                image.getLayoutParams().height = height;
                image.setVisibility(View.VISIBLE);
            }
        });
    }

    public static Point getGridVerPosterSize(Context mContext, int columns) {
        int width = getScreenWidthPixels(mContext) / columns;
        width = width - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.margin_small);
        int height = Math.round((float) width / VER_POSTER_RATIO);
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }

    public static Point getGridHorPosterSize(Context mContext, int columns) {
        int width = getScreenWidthPixels(mContext) / columns;
        width = width - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.margin_small);
        int height = Math.round((float) width / HOR_POSTER_RATIO);
        Point point = new Point();
        point.x = width;
        point.y = height;
        return point;
    }

    public static int getScreenWidthPixels(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    public static int getScreenHeightPixels(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }


    /**
     * 保存图片
     *
     * @param mContext
     * @param bmp
     * @param fileName
     * @return
     */
    public static String saveBitmap(Context mContext, Bitmap bmp, String fileName) {
        File fileDir = mContext.getCacheDir();
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
//        String fileName = "uvsnake_welcome_" + System.currentTimeMillis() + ".jpg";
        File file = new File(fileDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    // "badgeType":0 角标类型 -1、什么都不是 0、新品 1、免费 2、推荐 3、独播 4、精选

    /**
     * 根据影片类型，获取对应的图标
     * @param movieType
     * @return
     */
    public static int resIdFromMovieType(int movieType) {
        switch (movieType) {
            case 0://新品
                return R.drawable.icon_new_movie;
            case 1://免费
                return R.drawable.icon_free_movie;
            case 2://推荐
                return R.drawable.icon_recommend_movie;
            case 3://独播
                return R.drawable.icon_dubo_movie;
            case 4://精选
                return R.drawable.icon_choice_movie;
            default:
                return R.drawable.icon_new_movie;
        }
    }

}
