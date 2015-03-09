package com.pppark.support.manager;

/**
 * 全局Constant管理类
 * 
 * @Package com.xunlei.video.support.manager
 * @ClassName: ConstantManager
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 29, 2014 3:07:17 PM
 */
public class ConstantManager {
    // 产品ID product ID
    public static int PRODUCT_ID = 39;
    // 产品BUSSINESS_TYPE 这个是值帐号体系用来用来区分各个产品的，相同id的会互踢，不同id的就可以同时在线
    public static int BUSSINESS_TYPE = 20;

    public static boolean isUpdate = false;// 是否有新版本
}
