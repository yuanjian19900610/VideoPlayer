package com.scott.vp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanjian on 17/12/19.
 */

public class RecommendBean implements Serializable {

    public String title;
    public List<ChannelBean> data;

    @Override
    public String toString() {
        return "RecommendBean{" +
                "title='" + title + '\'' +
                ", data=" + data +
                '}';
    }
}
