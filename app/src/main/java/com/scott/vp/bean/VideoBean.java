package com.scott.vp.bean;

import java.io.Serializable;

/**
 *<pre>
 * VideoBean
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
 * Created by scott on 2019-08-28 20:32.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class VideoBean implements Serializable {

    public static final String KEY = "videoBean";

    public int id;
    public String name;
    public String url;
    public boolean isSelected;

    public int quality;//0表示普通， 1：高清

    public boolean isPlay;

    public String md5;// 本地视频MD5 非空字符串才做验证


    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", isSelected=" + isSelected +
                ", quality=" + quality +
                ", isPlay=" + isPlay +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
