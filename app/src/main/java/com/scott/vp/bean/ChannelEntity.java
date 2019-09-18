package com.scott.vp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanjian on 17/12/26.
 */

public class ChannelEntity implements Serializable {

    public String title;
    public List<MovieBean> data;


    @Override
    public String toString() {
        return "ChannelEntity{" +
                "title='" + title + '\'' +
                ", data=" + data +
                '}';
    }
}
