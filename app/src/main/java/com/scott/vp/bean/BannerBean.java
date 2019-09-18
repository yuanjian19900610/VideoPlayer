package com.scott.vp.bean;

import java.io.Serializable;

/**
 * Created by yuanjian on 17/10/10.
 */

public class BannerBean implements Serializable {

    public int id;
    public String title;
    public String image;
    public int movieId;
    public int type;
    public int isNew;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BannerBean{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", movieId=").append(movieId);
        sb.append(", type=").append(type);
        sb.append(", isNew=").append(isNew);
        sb.append('}');
        return sb.toString();
    }
}
