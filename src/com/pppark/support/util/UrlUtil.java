package com.pppark.support.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import android.util.Base64;
import android.webkit.URLUtil;

/**
 * URL工具
 * @Package com.xunlei.video.support.util
 * @ClassName: UrlUtil
 * @author Carl Li
 * @mail huabeiyipilang@gmail.com
 * @date 2014-5-8 上午10:33:32
 */
public class UrlUtil {
    
    /**
     * 检查是否可下载链接
     * @Title: isUrlDownloadable
     * @param url
     * @return
     * @return boolean
     * @date 2014-5-8 上午10:33:59
     */
    public static boolean isUrlDownloadable(String url) {
        if (url == null) {
            return false;
        }
        if (isHttpurl(url) || isFtpurl(url) || isEmuleurl(url)
                || isThunderurl(url) || isMagneturl(url)) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否是HTTP链接
     * @Title: isHttpurl
     * @param fileName
     * @return
     * @return boolean
     * @date 2014-5-8 上午10:34:25
     */
    public static boolean isHttpurl(String fileName) {
        return URLUtil.isHttpUrl(fileName);
    }
    
    /**
     * 是否是http、https链接
     * @Title: isNetworkUrl
     * @param url
     * @return
     * @return boolean
     * @date 2014-5-8 上午11:13:42
     */
    public static boolean isNetworkUrl(String url){
        return URLUtil.isNetworkUrl(url);
    }
    
    /**
     * 是否是FTP链接
     * @Title: isFtpurl
     * @param fileName
     * @return
     * @return boolean
     * @date 2014-5-8 上午10:36:36
     */
    public static boolean isFtpurl(String fileName) {
        if (fileName == null) {
            return false;
        }
        if (fileName.toLowerCase().startsWith("ftp://")) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否是电驴链接
     * @Title: isEmuleurl
     * @param fileName
     * @return
     * @return boolean
     * @date 2014-5-8 上午10:37:08
     */
    public static boolean isEmuleurl(String fileName) {
        if (fileName == null) {
            return false;
        }
        if (fileName.toLowerCase().startsWith("ed2k:")) {
            return true;
        }
        return false;
    }

    /**
     * 是否是迅雷链接
     * @Title: isThunderurl
     * @param fileName
     * @return
     * @return boolean
     * @date 2014-5-8 上午10:37:42
     */
    public static boolean isThunderurl(String fileName) {
        if (fileName == null) {
            return false;
        }
        if (fileName.toLowerCase().startsWith("thunder://")) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否是磁力链接
     * @Title: isMagneturl
     * @param fileName
     * @return
     * @return boolean
     * @date 2014-5-8 上午10:38:17
     */
    public static boolean isMagneturl(String fileName) {
        if (fileName == null) {
            return false;
        }
        if (fileName.toLowerCase().startsWith("magnet:?") || fileName.toLowerCase().startsWith("bt://")) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否是合法的网址
     * @Title: isWebSiteUrl
     * @param url
     * @return
     * @return boolean
     * @date 2014-5-8 上午11:06:43
     */
    public static boolean isWebSiteUrl(String url){
        return Pattern.matches("^(\\w*...)(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))+.*(\\?\\S*)?$", url);
    }

    /**
     * 是否是种子文件
     * @Title: isTorrentFile
     * @param path
     * @return
     * @return boolean
     * @date 2014-5-9 下午3:59:49
     */
    public static boolean isTorrentFile(String path) {
        if (path == null) {
            return false;
        }
        if (path.endsWith(".torrent")) {
            return true;
        }
        return false;
    }
    
    /**
     * 解码迅雷链接
     * @Title: decodeThunderUrl
     * @param url
     * @return
     * @return String
     * @date 2014-5-9 下午4:01:04
     */
    public static String decodeThunderUrl(String url) {
        if ((url != null) && isThunderurl(url)) {
            String value = url.substring(url.length() - 1, url.length());
            if (value.equals("/")) {
                url = url.substring(0, url.length() - 1);
            }
            String decodeUrl = url.substring(10, url.length());
            String base64Url = null;
            String originalUrl = "";
            byte[] base64Bytes = null;
            try {
                base64Bytes = Base64.decode(decodeUrl, Base64.DEFAULT);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            if (base64Bytes != null) {
                try {
                    base64Url = new String(base64Bytes, "gbk");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                originalUrl = base64Url.substring(2, base64Url.length() - 2);
            }
            return originalUrl;
        } else {
            return "";
        }
    }
    
    /**
     * 
     * 检查是否为视频地址
     * @Title: isVideoUrl
     * @param turl
     * @return
     * @return boolean
     * @date 2014-5-13 下午2:41:14
     */
    public static boolean isVideoUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".rmvb") || url.endsWith(".mkv")
                || url.endsWith(".rm") || url.endsWith(".avi")
                || url.endsWith(".mp4") || url.endsWith(".3gp")
                || url.endsWith(".flv") || url.endsWith(".wmv")
                || url.endsWith(".mpg") || url.endsWith(".swf")
                || url.endsWith(".xv") || url.endsWith(".xlmv")
                || url.endsWith(".mov") || url.endsWith(".mpeg")
                || url.endsWith(".ts") || url.endsWith(".asf")
                || url.endsWith(".mpga") || url.endsWith(".m3u")
                || url.endsWith(".dat")) {// 所有视频格式
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isMp3Url(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".mp3") || url.endsWith(".wma")
                || url.endsWith(".aac") || url.endsWith(".mid")
                || url.endsWith(".ape") || url.endsWith(".flac")
                || url.endsWith(".wav")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isApkUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".apk")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isExeUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".exe")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPdfFile(String fileName) {
        if (fileName == null)
            return false;
        if (fileName.endsWith(".pdf") || fileName.endsWith(".doc")
                || fileName.endsWith(".docx") || fileName.endsWith(".txt")) {
            return true;
        }
        return false;
    }

    public static boolean isPptFile(String fileName) {
        if (fileName.endsWith(".ppt")) {
            return true;
        }
        return false;

    }

    public static boolean isTxtFile(String fileName) {
        if (fileName.endsWith(".txt")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isXslFile(String fileName) {
        if (fileName.endsWith(".xsl")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isPdfUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".pdf")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPPTUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".ppt") || url.endsWith(".pptx")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTxtUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".txt")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isXlsUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".xls") || url.endsWith(".xlsx")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isImageUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".jpg") || url.endsWith(".jpeg")
                || url.endsWith(".bmp") || url.endsWith(".png")
                || url.endsWith(".tiff") || url.endsWith(".exif")) {// 所有图片格式
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param url
     * @return 判斷給定網址是否是压缩文件地址
     */
    public static boolean isRarUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".rar") || url.endsWith(".zip") || url.endsWith(".7z")
                || url.endsWith(".jar")) {// 所有压缩文件格式
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDocUrl(String turl) {
        if (turl == null) {
            return false;
        }
        String url = turl.toLowerCase();
        if (url.endsWith(".doc") || url.endsWith(".docx")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 从磁力链接中取得hashinfo
     * @Title: getHashInfoByMagnetUrl
     * @param url
     * @return
     * @return String
     * @date 2014-6-9 下午5:32:56
     */
    public static String getHashInfoByMagnetUrl(String url) {
        String newUrl = null;
        // magnet:?xt=urn:btih:6A08A76CE821E01D713D0F0D0ECF58F1C178D6CC
        if (url.contains("&")) {
            int beginIndex = url.indexOf("btih:");
            int endIndex = url.indexOf("&");
            if (beginIndex > 0 && endIndex > 0 && ((beginIndex + 5) < endIndex)) {
                newUrl = url.substring(beginIndex + 5, endIndex);
            }
        } else {
            int beginIndex = url.indexOf("btih:");
            int endIndex = url.length();
            if (beginIndex > 0 && endIndex > 0 && ((beginIndex + 5) < endIndex)) {
                newUrl = url.substring(beginIndex + 5, endIndex);
            }
        }
        if (newUrl != null && newUrl.length() > 0) {
            newUrl = newUrl.toUpperCase();
        }
        return newUrl;
    }
}
