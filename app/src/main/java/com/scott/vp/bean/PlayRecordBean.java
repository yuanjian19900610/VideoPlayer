package com.scott.vp.bean;

import java.io.Serializable;


/**
 *<pre>
 * PlayRecordBean
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
 * Created by scott on 2019-08-28 20:35.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class PlayRecordBean implements Serializable {

    public int movieId;//电影的id
    public String videoName;//连续剧的集数
    public long lastPosition;//上次播放的时间
    public int videoId;

    @Override
    public String toString() {
        return "PlayRecordBean{" +
                "movieId=" + movieId +
                ", videoName='" + videoName + '\'' +
                ", lastPosition=" + lastPosition +
                ", videoId=" + videoId +
                '}';
    }
}
