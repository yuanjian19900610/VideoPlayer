package com.scott.vp.utils;

import android.util.Log;

import java.util.logging.Logger;

//import com.orhanobut.logger.Logger;

public class LogUtils {

    private static final boolean isDebug = true;
    private static final boolean isSystemLog = true;
    private static final String DEFAULT_TAG = "haohelper_service";

    /**
     * 使用默认的TAG:private static final String DEFAULT_TAG = "haohelper";
     *
     * @param msg 需要打印输出的内my
     */
    public static void error(String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.e(DEFAULT_TAG, msg);
            } else {
//                Logger.e(msg);
            }
        }
    }

    public static void error(String tag, String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.e(tag, msg);
            } else {
//                Logger.t(tag).e(msg);
            }
        }
    }

    /**
     * 使用默认的TAG:private static final String DEFAULT_TAG = "haohelper";
     *
     * @param msg 需要打印输出的内容
     */
    public static void warn(String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.w(DEFAULT_TAG, msg);
            } else {
//                Logger.w(msg);
            }
        }
    }

    public static void warn(String tag, String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.e(tag, msg);
            } else {
//                Logger.t(tag).w(msg);
            }
        }
    }


    /**
     * 使用默认的TAG:private static final String DEFAULT_TAG = "haohelper";
     *
     * @param msg 需要打印输出的内容
     */
    public static void info(String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.i(DEFAULT_TAG, msg);
            } else {
//                Logger.i(msg);
            }
        }
    }

    public static void info(String tag, String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.i(tag, msg);
            } else {
//                Logger.t(tag).i(msg);
            }
        }
    }

    public static void json(String tag, String json) {
        if (isDebug) {
            if (isSystemLog) {
                Log.i(tag, json);
            } else {
//                Logger.t(tag).json(json);
            }
        }
    }

    public static void json(String json) {
        if (isDebug) {
            if (isSystemLog) {
                Log.i(DEFAULT_TAG, json);
            } else {
//                Logger.json(json);
            }
        }
    }


    /**
     * 使用默认的TAG:private static final String DEFAULT_TAG = "haohelper";
     *
     * @param msg 需要打印输出的内容
     */
    public static void debug(String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.d(DEFAULT_TAG, msg);
            } else {
//                Logger.d(msg);
            }
        }
    }

    public static void debug(String tag, String msg) {
        if (isDebug) {
            if (isSystemLog) {
                Log.d(tag, msg);
            } else {
//                Logger.t(tag).d(msg);
            }
        }
    }


    public static void print(String msg) {
        if (isDebug) {
            System.out.println(msg);
        }
    }

}
