package com.scott.vp.bean;

import java.io.Serializable;

/**
 * Created by yuanjian on 17/10/10.
 */

public class CategoryBean implements Serializable {

    public int id;
    public String name;
    public String image;
    public int parentId;
    public int type;
    public int isNew;

    public boolean isSelected;

    @Override
    public String toString() {
        return "CategoryBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                ", isNew=" + isNew +
                ", isSelected=" + isSelected +
                '}';
    }
}
