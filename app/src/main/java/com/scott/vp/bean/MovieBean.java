package com.scott.vp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *<pre>
 * MovieBean
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
 * Created by scott on 2019-08-28 20:31.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class MovieBean implements Serializable {

    public static final String KEY = "movieBean";

    public String name;
    public int id;
    public String title;
    public String description;
    public String image;
    public String typeId;
    public Long createTime;
    public Long updateTime;
    public String videoUrl;
    public String releaseTime;
    public String typeName;
    public String director;
    public String cast;
    public String duration;
    public String country;
    // 显示播放广告 1、显示 0、不显示
    public int showPlayAd;
    public float score;
    public boolean isSelected;
    public float money;
    public List<VideoBean> video;
    public int payState;
    public String copyright;
    public int playLimitTime;
    public String playLimitText;
    public int type;// 电影类型 0、电影 1、电视剧
    public String parentTypeName;
    public int parentTypeId;
    public int hotelPay;// 酒店挂账支付 0、禁止 1、允许
    public int quality;//0表示普通， 1：高清

    public String trailer;//预告片地址
    public String trailerMd5;//预告片md5值
    public int isNew;

    public int isFree;// // 免费电影 0、否 1、是

    public int singleVideoPlay; // 支持单片点播 0、否 1、是
    public int timePlay;// 支持时段收费 0、否 1、是

    public String cpid;//vmatch使用的cpid

    public String singleVideoPlayDesc;//"单片点播描述"
    public String timePlayDesc;//时段点播描述"

    //换机二维码
    public String orderChangeQr;
    //换机说明
    public ArrayList<String> orderChangeDesc;

    public String payUrl;

    public String source;

    public String sourceIcon;//来源图标

    public int badgeType;// 角标类型 -1、什么都不是 0、新品 1、免费 2、推荐 3、独播 5、精选

    // 免费看片活动URL(有数据才展示相关按钮)
    public String freeActivityUrl;

    @Override
    public String toString() {
        return "MovieBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", typeId='" + typeId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", videoUrl='" + videoUrl + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", typeName='" + typeName + '\'' +
                ", director='" + director + '\'' +
                ", cast='" + cast + '\'' +
                ", duration='" + duration + '\'' +
                ", country='" + country + '\'' +
                ", showPlayAd=" + showPlayAd +
                ", score=" + score +
                ", isSelected=" + isSelected +
                ", money=" + money +
                ", video=" + video +
                ", payState=" + payState +
                ", copyright='" + copyright + '\'' +
                ", playLimitTime=" + playLimitTime +
                ", playLimitText='" + playLimitText + '\'' +
                ", type=" + type +
                ", parentTypeName='" + parentTypeName + '\'' +
                ", parentTypeId=" + parentTypeId +
                ", hotelPay=" + hotelPay +
                ", quality=" + quality +
                ", trailer='" + trailer + '\'' +
                ", trailerMd5='" + trailerMd5 + '\'' +
                ", isNew=" + isNew +
                ", isFree=" + isFree +
                ", singleVideoPlay=" + singleVideoPlay +
                ", timePlay=" + timePlay +
                ", cpid='" + cpid + '\'' +
                ", singleVideoPlayDesc='" + singleVideoPlayDesc + '\'' +
                ", timePlayDesc='" + timePlayDesc + '\'' +
                ", orderChangeQr='" + orderChangeQr + '\'' +
                ", orderChangeDesc=" + orderChangeDesc +
                ", payUrl='" + payUrl + '\'' +
                ", source='" + source + '\'' +
                ", sourceIcon='" + sourceIcon + '\'' +
                ", badgeType=" + badgeType +
                ", freeActivityUrl='" + freeActivityUrl + '\'' +
                '}';
    }
}
