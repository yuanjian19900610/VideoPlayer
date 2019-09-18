package com.scott.vp.bean;

import java.io.Serializable;

/**
 * Created by yuanjian on 17/10/10.
 */

public class ChannelBean implements Serializable {

    public static final String KEY = "channelbean";

    public int id;
    public String title;
    public String image;
    public int type;
    public int isNew;
    public int isFree;
    public int badgeType; // 角标类型 -1、什么都不是 0、新品 1、免费 2、推荐 3、独播 5、精选

    @Override
    public String toString() {
        return "ChannelBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                ", isNew=" + isNew +
                ", isFree=" + isFree +
                ", badgeType=" + badgeType +
                '}';
    }
}
