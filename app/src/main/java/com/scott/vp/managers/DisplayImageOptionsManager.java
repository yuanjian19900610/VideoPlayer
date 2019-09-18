package com.scott.vp.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.scott.vp.R;
import com.scott.vp.utils.ACache;

/**
 * <pre>
 * com.uvsnake.cibn.manager
 *  默认图生成管理类
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
 * Created by scott on 2019/4/3.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class DisplayImageOptionsManager {

    private static DisplayImageOptionsManager manager = new DisplayImageOptionsManager();
    private DisplayImageOptions options450x604;
    private DisplayImageOptions options556x375;
    private DisplayImageOptions options260x120;
    private DisplayImageOptions options;

    private DisplayImageOptionsManager() {
    }

    public static synchronized DisplayImageOptionsManager getInstance() {
        if (manager == null) {
            manager = new DisplayImageOptionsManager();
        }
        return manager;
    }

    private void buildOptions450x604() {
        options450x604 = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(false)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.icon_loading_default_450_604)
                .showImageOnFail(R.drawable.icon_loading_default_450_604)
                .showImageForEmptyUri(R.drawable.icon_loading_default_450_604)
                .build();
    }

    private void buildOptions556x375() {
        options556x375 = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(false)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.icon_loading_default_556_375)
                .showImageOnFail(R.drawable.icon_loading_default_556_375)
                .showImageForEmptyUri(R.drawable.icon_loading_default_556_375)
                .build();

    }

    private void buildOptions260x120() {
        options260x120 = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(false)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.icon_loading_default_260_120)
                .showImageOnFail(R.drawable.icon_loading_default_260_120)
                .showImageForEmptyUri(R.drawable.icon_loading_default_260_120)
                .build();

    }


    public Drawable getDrawable(Context context, String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    public DisplayImageOptions getOptions450x604() {
        if (options450x604 == null) {
            buildOptions450x604();
        }
        return options450x604;
    }

    public Drawable getImageDrawable450x604(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_loading_default_450_604);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);

        return drawable;
    }

    public DisplayImageOptions getOptions556x375() {
        if (options556x375 == null) {
            buildOptions556x375();
        }
        return options556x375;
    }

    public Drawable getImageDrawable556x375(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_loading_default_556_375);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    public DisplayImageOptions getOptions260x120() {
        if (options260x120 == null) {
            buildOptions260x120();
        }
        return options260x120;
    }

    public Drawable getImageDrawable260x120(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_loading_default_260_120);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }


    public DisplayImageOptions getOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
        }
        return options;
    }
}
