package com.scott.vp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scott.vp.base.BaseActivity;

/**
 *<pre>
 * MainActivity
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
 * Created by scott on 2019-08-28 16:44.
 *
 * *-------------------------------------------------------------------*
 *  </pre>
 */
public class MainActivity extends BaseActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        if(mainFragment==null){
            mainFragment=new MainFragment();
        }
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.fl_fragment, mainFragment,"MainFragment");
        beginTransaction.commit();
    }
}
