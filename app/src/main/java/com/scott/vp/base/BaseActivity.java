package com.scott.vp.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

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
 * Created by scott on 2019-08-28.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class BaseActivity extends AppCompatActivity {

    protected static final String BUNDLE = "bundle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 界面跳转
     *
     * @param targetClass ：跳转的目标界面
     * @param bundle      :传入的参数
     */
    protected void changeView(Class targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        if (null != bundle) {
            intent.putExtra(BUNDLE, bundle);
        }
        startActivity(intent);
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
        Intent intent = new Intent(this, targetClass);
        if (null != bundle) {
            intent.putExtra(BUNDLE, bundle);
        }
        startActivityForResult(intent, requestCode);
        changeAnimation();
    }

    /**
     * 界面跳转
     *
     * @param targetClass ：跳转的目标界面
     * @param bundle      :传入的参数
     * @param isFinish    ：是否关闭当前的activity
     */
    protected void changeView(Class targetClass, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, targetClass);
        if (null != bundle) {
            intent.putExtra(BUNDLE, bundle);
        }
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
        // changeAnimation();
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

    protected void changeAnimation() {
        // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
