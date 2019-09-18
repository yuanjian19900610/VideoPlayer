package com.scott.vp.base;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * <pre>
 * com.scott.vp.base
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
 * Created by scott on 2019-08-29.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class BaseFragment extends Fragment implements View.OnClickListener {


    protected static final String BUNDLE = "bundle";
    protected DisplayImageOptions options;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .bitmapConfig(Bitmap.Config.ALPHA_8)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 界面跳转
     *
     * @param targetClass ：跳转的目标界面
     * @param bundle      :传入的参数
     */
    protected void changeView(Class targetClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), targetClass);
        if (null != bundle) {
            intent.putExtra(BUNDLE, bundle);
        }
        getActivity().startActivity(intent);
        changeAnimation();
    }


    /**
     * 界面跳转
     *
     * @param targetClass 跳转class
     * @param bundle      数据参数
     * @param requestCode
     */
    protected void changeViewForResult(Class targetClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), targetClass);
        if (null != bundle) {
            intent.putExtra(BUNDLE, bundle);
        }
        BaseFragment.this.startActivityForResult(intent, requestCode);
        changeAnimation();
    }


    protected void changeView(String targetStr, Bundle bundle, boolean isFinish) {
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        changeView(targetClass, bundle, isFinish);
    }

    /**
     * 界面跳转
     *
     * @param targetClass ：跳转的目标界面
     * @param bundle      :传入的参数
     * @param isFinish    ：是否关闭当前的activity
     */
    protected void changeView(Class targetClass, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(getActivity(), targetClass);
        if (null != bundle) {
            intent.putExtra(BUNDLE, bundle);
        }
        getActivity().startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
        // changeAnimation();

    }

    protected void changeAnimation() {
        // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }


}
