package com.scott.vp.bean;

import java.io.Serializable;

/**
 * <pre>
 * PrevueBean
 *  预告片实体对象
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
 * Created by scott on 2019/4/30 10:41 AM.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class PrevueBean implements Serializable {

    public static final String KEY = "PrevueBean";
    //影片id
    public int movieId;
    //影片图片
    public String image;
    //type 数据类型 0、预告片 1、分类 2、影片 3、网页广告 4、视频广告
    public int type;
    public String title;
    //远程地址
    public String videoUrl;
    // 电影类型 0、电影 1、电视剧
    public int movieType;
    public String link;
    //类型id
    public int typeId;
    //父类id
    public int parentTypeId;
    // 视频md5值
    public String videoMd5;
    //最终的视频播放地址
    public String realVideoUrl;
    //当前预告片是否被选中【本地业务逻辑需要】
    public boolean isSelected;
    //广告间隔时长
    public int duration;
    //日志统计id
    public int id;
    @Override
    public String toString() {
        return "PrevueBean{" +
                "movieId=" + movieId +
                ", image='" + image + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", movieType=" + movieType +
                ", link='" + link + '\'' +
                ", typeId=" + typeId +
                ", parentTypeId=" + parentTypeId +
                ", videoMd5='" + videoMd5 + '\'' +
                ", realVideoUrl='" + realVideoUrl + '\'' +
                ", isSelected=" + isSelected +
                ", duration=" + duration +
                '}';
    }
}
