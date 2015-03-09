package com.pppark.support.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import com.pppark.MyApplication;
import com.pppark.support.util.PhoneUtil;

/**
 * 接口地址类
 * 
 * @Package com.xunlei.video.support.manager
 * @ClassName: UrlManager
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-5-5 下午6:06:12
 */
public class UrlManager {

    public static final String CLIENT_ID = "android_vod";

    /**
     * 查看vip信息（wap页面地址）
     */
    public static final String VIP_PRIVILEGE_URL = "http://vip.xunlei.com/vip/";
    /**
     * encrypt=1加密，不填则默认加密 gzip=1加压，不填则默认加压
     */
    public static final String VIP_SERVER_URL = "http://wireless.yun.vip.xunlei.com/xlCloudShare?encrypt=1&gzip=1";
    /**
     * 头像地址
     */
    public static final String AVATAR_TEMPLATE = "http://img.ucenter.xunlei.com/usrimg/%s/100x100";
    /**
     * 找回密码
     */
    public static final String PASSWORD_FIND = "http://aq.xunlei.com/password_find.html";
    /**
     * [获取价格接口] 迅雷白金
     */
    public static final String GET_PRICE = "http://dypay.vip.xunlei.com/phonepay/getprice/";

    /**
     * [提交订单接口-安全支付] 迅雷白金 ?userid=*&payway=safe&month=*&ext1=*&ext2=*&vastype=3&callback=*
     */
    public static final String XUNLEI_PAY = "http://dypay.vip.xunlei.com/phonepay/order/";

    /**
     * [升级订单接口-安全支付]
     * http://dypay.vip.xunlei.com/phonepay/upgrade/?userid=*&payway=safe&ndays=*&ext1=*&ext2=*&vastype=3&callback=*
     */
    public static final String XUNLEI_UPDATE_PAY = "http://dypay.vip.xunlei.com/phonepay/upgrade/";

    /**
     * [获取视频详情接口]
     */
    public static final String MOVIE_DETAIL_URL = "http://media.v.xunlei.com/tv/info?movieid=%s";
    public static final String MOVIE_DETAIL_URL_OLD = "http://wireless.yun.vip.xunlei.com/cloudPlay?XL_LocationProtocol=1.1&Command_id=search_detail_req&resource_id=%s";

    /**
     * [获取剧集列表接口]
     */
    public static final String MOVIE_EPISODE_LIST_URL = "http://media.v.xunlei.com/tv/site_sublist?movieid=%s&site=%s";
    /**
     * [获取相关推荐接口]
     */
    public static final String MOVIE_RECOMMEND_LIST_URL = "http://media.v.xunlei.com/tv/relations?movieid=%s&size=%d";
    /**
     * [附近热播接口]
     */
    public static final String NEIGHBOUR_HOT_URL = "http://wireless.yun.vip.xunlei.com/cloudPlay?XL_LocationProtocol=2.0";
    /**
     * 视频字幕列表 gcid,cid,userid
     */
    public static final String VIDEO_SUBTITLE_URL = "http://i.vod.xunlei.com/subtitle/list?gcid=%s&cid=%s&userid=%s";
    /**
     * [启动图片地址]
     */
    public static final String LOADING_IMG_START_UP = "http://i0.media.geilijiasu.com/cms/mobile/start.png";
    /**
     * [启动图片地址]
     */
    public static final String SCRATCH_CARD_LOTTERY_URL = "http://wireless.yun.vip.xunlei.com/lottery?encrypt=1";
    /**
     * =====================搜索相关Url, Begin=======================
     */
    public final static String SEARCH_ADVICE_ENGINE_URL = //后台的搜索引擎
            "http://i3.yun.kanimg.com/search_custom_site_rule.html";
    public final static String SEARCH_TOP_ENGINE_URL = //一些常用的搜素的url，暂时用不到
            "http://wireless.yun.vip.xunlei.com/custom/search_engines?protocol=2.0";
    public final static String SEARCH_RECOMMAND_KEYWORD_URL = //热搜
            "http://media.v.xunlei.com/mobile/search_recommend_jsonarray";
    private static final String SEARCH_KEYWORDS_ASSOCIATION_URL =
            "http://so.v.xunlei.com/ac/suggest?wd=%s&size=5&client=android_vod&version=%s";
    /**
     * 搜索联想词
     */
    public static String getSearchAssociationUrl(String keyword, int count) {
        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(Locale.US, SEARCH_KEYWORDS_ASSOCIATION_URL, keyword, PhoneUtil.getVerName(MyApplication.context));
    }
    private static final String SERVER_URL = "http://i.vod.xunlei.com/";
    private static String urlValidateUrl;
    public static String getVodUrlValidateUrl() {
        if (urlValidateUrl == null) {
            StringBuffer sb = new StringBuffer(SERVER_URL);
            sb.append("req_video_name?from=").append("vlist");
            sb.append("&platform=").append("0");
            urlValidateUrl = sb.toString();
        }
        return urlValidateUrl;
    }
    /**
     * 隐私设置
     */
    public static final String PRIVACY_PSW_URL="http://privacy.i.vod.xunlei.com:80";
}
