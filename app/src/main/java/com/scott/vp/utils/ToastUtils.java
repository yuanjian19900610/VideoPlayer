package com.scott.vp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * <pre>
 * com.scott.vp.utils
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
public class ToastUtils {

    private static Toast mToast;

    public ToastUtils() {
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }

    public static void show(Context context, CharSequence text) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        if (text != null && text.length() != 0) {
            mToast = Toast.makeText(context, text, 0);
            mToast.show();
        }
    }
}
