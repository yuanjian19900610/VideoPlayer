package com.scott.vp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author SCOTT
 * @version 1.0.3
 * @description:
 * @createTime 17/5/9
 */

public class MovieTypeBean implements Serializable {

    public static final String KEY = "MovieTypeBean";
    public int id;
    public String name;
    public List<MovieTypeBean> sub;//二级菜单
    public int secondTypeId;//二级菜单id
    public boolean isSelected;
    public int flag = -1;//是否是精选

    public boolean isCurrentSelected;

    public MovieTypeBean(int id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public MovieTypeBean() {
    }

    @Override
    public String toString() {
        return "MovieTypeBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sub=" + sub +
                ", secondTypeId=" + secondTypeId +
                ", isSelected=" + isSelected +
                ", flag=" + flag +
                ", isCurrentSelected=" + isCurrentSelected +
                '}';
    }
}
