package com.scott.vp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanjian on 17/10/10.
 */

public class IndexBean implements Serializable {

    public static final String KEY = "channelbean";


    public List<BannerBean> banner;


    public List<CategoryBean> subType;

    public List<ChannelEntity> channel;

    public RecommendBean recommend;


    public AdvertisingBean ad;

    @Override
    public String toString() {
        return "IndexBean{" +
                "banner=" + banner +
                ", subType=" + subType +
                ", channel=" + channel +
                ", recommend=" + recommend +
                ", ad=" + ad +
                '}';
    }
}
