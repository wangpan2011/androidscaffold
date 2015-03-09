package com.pppark.framework.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;
import android.util.Pair;

public class Util {

    static final String JOIN_SIGN_AND = "&";
    static final String JOIN_SIGN_SEMICOLON = ";";

    public static String inputStream2String(InputStream in) throws IOException {
        return new String(inputStream2Bytes(in));
    }

    public static byte[] inputStream2Bytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1;) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    /**
     * 封装get请求url
     * 
     * @Title: packageGetParams
     * @param url
     * @param params
     * @return String
     * @date Apr 23, 2014 4:23:21 PM
     */
    static String packageGetUrl(String url, List<Pair<String, String>> params) {

        assert params != null : "params can't be null";

        StringBuffer sb = new StringBuffer(url);

        if (url.indexOf("?") == -1) {
            sb.append("?");
        } else {
            sb.append("&");
        }

        sb.append(joinParams(params, JOIN_SIGN_AND));

        return sb.toString();
    }

    /**
     * 封装post请求参数
     * 
     * @Title: packagePostParams
     * @param params
     * @return String
     * @date Apr 23, 2014 4:23:47 PM
     */
    static String packagePostParams(List<Pair<String, String>> params) {
        return joinParams(params, JOIN_SIGN_AND);
    }

    static String joinParams(List<Pair<String, String>> params, final String joinSign) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (Pair<String, String> param : params) {

            sb.append(param.first);
            sb.append("=");
            sb.append(param.second);

            if (i != params.size() - 1) {
                sb.append(joinSign);
            }

            i++;
        }
        return sb.toString();
    }

    /**
     * 根据某些业务需求，对json做后期处理
     * 
     * @Title: trimJson
     * @return void
     * @date Apr 18, 2014 1:50:22 AM
     */
    static String trimJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return json;
        }

        Pattern pattern = Pattern.compile(REGEX1);
        Matcher m = pattern.matcher(json);
        if (m.find() && m.groupCount() > 0) {
            json = m.group(1);
        }

        // 兼容处理 远程下载接口返回JSON多加的()问题
        pattern = Pattern.compile(REGEX2);
        m = pattern.matcher(json);
        if (m.find() && m.groupCount() > 0) {
            json = m.group(1);
        }
        //兼容处理，支付订单接口返回JSON多加*()问题
        pattern = Pattern.compile(REGEX3);
        m=pattern.matcher(json);
        if (m.find() && m.groupCount() > 0) {
            json = m.group(1);
        }
        return json;
    }

    private static String REGEX1 = "^\\s*callback\\s*\\((.*)\\s*\\)\\s*$";
    private static String REGEX2 = "^\\s*\\((.*)\\s*\\)\\s*$";
    private static String REGEX3 = "^\\s*\\*\\s*\\((.*)\\s*\\)\\s*$";
}
