package com.scott.vp.bean;

import java.io.Serializable;

/**
 * Created by yuanjian on 17/12/12.
 */

public class AdvertisingBean implements Serializable {

    public static final String KEY="AdvertisingBean";

    public String image;
    public String url;
    public int index;

    @Override
    public String toString() {
        return "AdvertisingBean{" +
                "image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", index=" + index +
                '}';
    }
}
