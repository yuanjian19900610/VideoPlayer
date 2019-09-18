package com.scott.vp.utils;

/**
 * @author SCOTT
 * @version 1.0.3
 * @description:
 * @createTime 17/5/9
 */

public class Contants {


    /**
     * 上次播放纪录
     */
//    public static final String PLAY_RECORD = "play_record";

    /**
     * 主机地址
     **/
//    public static final String HTTP_HOST = "http://api.uvsnake.com/api/v1/";

    public static String HOST = "http://tv.uvsnake.com";

    public static String HTTP_HOST = "/api/v3/";

    /**
     * 图片远程域名
     */
    public static String IMAGE_HOST = "http://img.qiniu.uvsnake.com";

    public static String ALL_MOVIE_IMAGE_URL = HOST + "/static/img/app/all.png";

    public static String SECOND_MENU_ALL_MOVIE_IMAGE_URL = HOST + "/static/img/app/channel/all.png";

    public static String DETAIL_ALL_MOVIE_IMAGE_URL = HOST + "/static/img/app/more.png";

    public static String SEARCH_IMAGE_URL = HOST + "/static/img/app/search.png";

    public static String DIANBO_IMAGE_URL = HOST + "/static/img/app/top.png";

    public static String WELCOME_IMAGE_URL = HOST + "/static/img/app/launch.png";

    //视频解码方式【酒店信息接口返回】
    public static final String VIDEO_DECODE_TYPE = "video_decode_type";

    //设备信息
    public static final String DEVICE_INFO = "deviceinfo";
    //是否买断
    public static final String BUYOUT = "buyOut";
    //酒店名称
    public static final String HOTEL_NAME = "hotel_name";
    /**
     * 离线ip
     */
    public static final String OFFLINE_IP_KEY = "offline_ip";

    /**
     * 最新启动图地址
     */
    public static final String APP_LAUNCH_URL = "appLaunch";

    //是否注册
    public static final String ISREGISTER = "isRegister";

    public static final String MAC_ADDRESS = "mac_address";

    public static final String IP_ADDRESS = "IP_address";
    //是否是触控屏幕
    public static final String IS_TOUCH_SCREEN = "is_touch_screen";

    public static final String MD5_URL = "matchMd5Url";

    public static final String LOCAL_MD5_URL = "localMatchMd5Url";
    //本地影片是否存在验证
    public static final String ipSet = "local_video_exist+check";

    //探索传媒  APPID
    public static final String VMATCH_APPID = "428";
    //探索传媒  APPKEY
    public static final String VMATCH_APPKEY = "62f8f6f21474682d270cb99cf355ff1a";

//    public static final String VMATCH_APPID = "15";//
//     public static final String VMATCH_APPKEY= "6c244a74efc1256fd3d615cfb398c15b";


    /*当前域名key*/
    public static final String CURRENT_DOMAIN_KEY = "domain";

    public static final String PRE_SERVER_IP_KEY = "server_ip";

    public static final String PRE_SERVER_PORT_KEY = "server_port";

    /**
     * 最新启动图地址
     */
//    public static final String APP_LAUNCH_URL = "appLaunch";
    /*
     * 上次启动图地址（老的启动图地址，当APP_OLD_LAUNCH_URL==APP_LAUNCH_URL 表示不用加载启动图）
     */
    public static final String APP_OLD_LAUNCH_URL = "old_appLaunch";

    //本地影片是否存在验证
    public static final String LOCAL_VIDEO_EXIST_CHECK_URL = "local_video_exist_check";

    // 涂鸦语音指令广播action
    public static final String TUYA_COMMAND_ACTION = "com.uvsnake.action.itv_instruction";
    //涂鸦影片搜索广播action
    public static final String TUYA_VIDEO_ACTION = "com.uvsnake.action.itv_video";

    /**
     * 获取电视分类
     */
    public static String GET_MOVIE_TYPE = "type";

    /**
     * 获取电视列表
     */
    public static String GET_MOVIE = "movie";

    public static String GET_WEATHERINFO = "weather/ip";

    public static String GET_SEARCH_KEYWORDS = "search";

    public static String HOTEL_ROOM_URL = "hotel-room";

    public static String CREATE_QR = "qr";

    public static String ORDER_URL = "order";

    //管理员密码
    public static String ADMIN_PWD = "app/admin-password";

    /**
     * 检查版本
     */
    public static String CHECK_VERSION_URL = "app/version";

    /**
     * 通过系统传的参数注册
     */
    public static String RGISTER_URL = "hotel-room/register";


    public static String GET_CATEGROY_URL = "type";


    public static String GET_CHANNEL_INFO_URL = "channel";


    public static String GET_RANK_INFO = "ranking";


    public static String GET_MAIN_INFO = "index";


    /**
     * 普瑞尔支付创建订单
     */
//    public static String PRE_CREATE_ORDER = "pre/order";
    public static String PRE_CREATE_ORDER = "touch/order";


    /**
     * 上传服务器ip地址
     */
    public static String UPLOAD_SERVICE_IP = "hotel/ipSet";

    /**
     * 上传服务器所有url地址
     */
    public static String GET_APIS_URL = "app/config";

    //呼叫代付地址
    public static String CALL_PAY_URL = "/api/call.json";


    //根据语音传递的关键字，进行搜索
    public static final String SEARCH_MOVIE_INFO = "asr/search";

    //视频地址校验
    public static final String MATCH_MDR5_URL = HTTP_HOST + "video/matchMd5";

    //判断视频是否存在
    public static final String VIDEO_EXIST_URL="video/exist";


    //banner广告
    public static final String BANNER_ADV = "ad/page-banner";

    //上传设备信息
    public static final String UPLOAD_DEVICE_INFO = "app/info";

    //涂鸦语音搜索
    public static final String SPEECH_SEARCH_TUYA = "speech";

    //vmatch广告
    public static final String AD_VMATCH = "vmatch";
    //粉丝广告
    public static final String AD_FS = "粉丝互动";

    //设备默认的缩放因子
    public static final String DENSITY_DEFAULT="density_default";

    //设置的目标缩放因子
    public static final String DENSITY_TARGET="density_target";


    public static class log {
        /**
         * 预告片
         */
        public static final String LOG_HOME_TRAILER = "home_trailer";
        /**
         * 搜索
         */
        public static final String LOG_HOME_SEARCH_MOVIE = "home_search_movie";
        /**
         * 排名
         */
        public static final String LOG_HOME_RANKING_MOVIE = "home_ranking_movie";
        /**
         * 全部影片
         */
        public static final String LOG_HOME_ALL_MOVIE = "home_all_movie";

        /**
         * 底部推荐位
         */
        public static final String LOG_HOME_BOTTOM_RECOMMEND = "home_bottom_recommend";

        /**
         * 右侧推荐位
         */
        public static final String LOG_HOME_RIGHT_RECOMMEND = "home_right_recommend";

        /**
         * 大图推荐
         */
        public static final String LOG_IMAGE_RECOMMEND = "image_recommend";
        /**
         * 二级菜单
         */
        public static final String LOG_SMALL_MENU = "small_menu";

        /**
         * 电影详情立即播放
         */
        public static final String LOG_MOVIE_DETAIL_PLAY = "movie_detail_play";
        /**
         * 电影详情立即支付
         */
        public static final String LOG_MOVIE_DETAIL_PAY = "movie_detail_pay";
        /**
         * 电影详情扫码换机
         */
        public static final String LOG_MOVIE_DETAIL_CHANGE_TV = "movie_detail_change_tv";
        /**
         * 电影详情免费攻略
         */
        public static final String LOG_MOVIE_DETAIL_FREE = "movie_detail_free";
        /**
         * 电影详情推荐影片
         */
        public static final String LOG_MOVIE_DETAIL_RECOMMEND_MOVIE = "movie_detail_recommend_movie";
        /**
         * 电影详情更多影片
         */
        public static final String LOG_MOVIE_DETAIL_MORE_MOVIE = "movie_detail_more_movie";
        /**
         * 播放界面推荐影片
         */
        public static final String LOG_MOVIE_PLAY_RECOMMEND_MOVIE = "movie_play_recommend_movie";
    }
}
