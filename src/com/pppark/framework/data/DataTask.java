package com.pppark.framework.data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.http.HttpStatus;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;

import com.google.xlgson.Gson;
import com.google.xlgson.GsonBuilder;
import com.pppark.BuildConfig;
import com.pppark.framework.cache.ADataDiskCache;
import com.pppark.framework.cache.DataDiskCacheImpl;
import com.pppark.framework.logging.Log;
import com.pppark.support.util.PhoneUtil;

/**
 * 全局数据请求类，默认执行http get请求
 * 
 * @Package com.xunlei.video.framework.data
 * @ClassName: DataTask
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 18, 2014 12:15:03 AM
 */
public class DataTask implements Observer {

    /**
     * http超时设置
     */
    public static final int HTTP_TIMEOUT = 10000;

    private final static String HTTP_HEADER_COOKIE = "Cookie";
    private final static String HTTP_HEADER_UA = "User-agent";
    private final static String HTTP_HEADER_REFERER = "Referer";
    private final static String HTTP_HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    private final static String HTTP_HEADER_LAST_MODIFIED = "Last-Modified";
    private final static String HTTP_HEADER_HOST = "Host";
    private final static String METHOD_GET = "GET";
    private final static String METHOD_POST = "POST";
    private final static String CHARSET = "utf-8";

    private AsyncTask<Void, Void, Void> task;
    private DataTaskListener dataTaskListener;
    private DataTaskDoInBackground doInBackground;

    private String url;
    private boolean isMethodPost = false;
    private String postParams;
    private List<Pair<String, String>> params;
    private List<Pair<String, String>> headers;
    private List<Pair<String, String>> cookies;
    private String userAgent;
    private String referer;
    private String ifModifiedSince;

    private int responseCode;
    private Map<String, List<String>> responseHeaders;
    private InputStream inputStream;
    private String json;

    private Gson gson;
    private List<GsonTypeAdapterPair> gsonTypeAdapterPairs;

    private boolean needCache;// 是否缓存
    private ADataDiskCache mCache;
    private int timeout = HTTP_TIMEOUT;
    
    /**
     * 保存DataTaskDoInBackground的返回值，通过getDoInBackgroundResult()获取
     */
    private Object doingBackgroudTaskResult;
    private boolean needEncrypt = false;// 是否加密
    private Object needEncryptObject;// 需要加密的数据
    private EncryptType encryptType = EncryptType.COMMON;// 从下载库中获取,用于加密

    public enum EncryptType {// 加密类型
        COMMON,        // 普通
        UNICOM          // 联通
    }

    /**
     * 上报使用，数据开始加载的起始时间戳
     */
    private long startRequestTime;
    /**
     * 上报使用，数据加载完成的结束时间戳
     */
    private long endRequestTime;

    private boolean hosted = false;
    private String needSetHost;
    
    /**
     * 创建一个非阻塞请求任务
     * 
     * @Title: DataTask
     * @param dataTaskListener
     * @param callerLifeObservable
     *            如果调用者为Activity或Fragment，则需传递此参数
     * @date Apr 17, 2014 11:39:40 PM
     */
    public DataTask(DataTaskListener dataTaskListener, Observable callerLifeObservable) {
        this.dataTaskListener = dataTaskListener;
        if (callerLifeObservable != null) {
            callerLifeObservable.addObserver(this);
        }
    }

    /**
     * 创建一个非阻塞请求任务
     * 
     * @Title: DataTask
     * @param dataTaskListener
     * @date Apr 18, 2014 1:59:27 AM
     */
    public DataTask(DataTaskListener dataTaskListener) {
        this.dataTaskListener = dataTaskListener;
    }

    /**
     * 创建一个阻塞或非阻塞请求任务
     * 
     * @Title: DataTask
     * @date Apr 18, 2014 1:59:35 AM
     */
    public DataTask() {

    }

    public void cancel() {
        if (task != null) {
            task.cancel(true);
        }
    }

    public boolean isCancelled() {
        return task != null ? task.isCancelled() : false;
    }

    /**
     * 异步执行请求
     * 
     * @Title: execute
     * @return void
     * @date Apr 18, 2014 1:57:10 AM
     */
    @SuppressLint("NewApi")
    public DataTask execute() {
        task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                if (dataTaskListener != null)
                    dataTaskListener.onPreExecute(DataTask.this);
                
                startRequestTime = System.currentTimeMillis();
            }

            @Override
            protected void onPostExecute(Void result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);

                endRequestTime = System.currentTimeMillis();

                if (needCache) {
                    switch (responseCode) {
                    case HttpStatus.SC_OK:
                        
                        if (TextUtils.isEmpty(json)) {
                            json = mCache.getFromCache();
                        } else {
                            
                            List<String> lastModifyHeader = responseHeaders.get(HTTP_HEADER_LAST_MODIFIED);
                            String tLastModified = lastModifyHeader != null ? lastModifyHeader.get(0) : null;
                            
                            if (!TextUtils.isEmpty(tLastModified)) {
                                mCache.saveLastModified(tLastModified);
                            }

                            mCache.saveToCache(json);
                        }
                        
                        break;
                    case HttpStatus.SC_NOT_MODIFIED:
                        json = mCache.getFromCache();
                        break;
                    }
                }

                if (dataTaskListener != null)
                    dataTaskListener.onPostExecute(responseCode, json, DataTask.this);
            }

            @Override
            protected Void doInBackground(Void... params) {
                if (doInBackground != null) {
                    doingBackgroudTaskResult = doInBackground.doInBackground();
                    return null;
                }

                if (needCache) {
                    if(mCache == null)mCache = new DataDiskCacheImpl(url);
                    if (mCache.isLoaded()) {
                        json = mCache.getFromCache();
                    } else {
                        String lastModified = mCache.getLastModified();
                        if (!TextUtils.isEmpty(lastModified)) {
                            DataTask.this.ifModifiedSince = lastModified;
                        }
                        request();
                    }
                } else {
                    request();
                }
                return null;
            }

        };

        if (Build.VERSION.SDK_INT < 11) {
            task.execute();
        } else {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        return this;
    }

    /**
     * 将接口返回的json转换为对象
     * 
     * @Title: loadFromJson
     * @param typeToken
     * @return Object
     * @date Apr 20, 2014 7:43:46 PM
     */
    public Object loadFromJson(Type typeToken) {
        initGson();
        try{
            return json != null ? gson.fromJson(json, typeToken) : null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        if (this.gsonTypeAdapterPairs != null) {
            for (GsonTypeAdapterPair pair : this.gsonTypeAdapterPairs) {
                builder.registerTypeAdapter(pair.type, pair.typeAdapter);
            }
        }
        gson = builder.create();
    }

    /**
     * 添加gson数据类型适配器
     * @Title: addGsonTypeAdapterPair
     * @param gsonTypeAdapterPair
     * @return void
     * @date Jun 24, 2014 2:11:18 PM
     */
    public void addGsonTypeAdapterPair(GsonTypeAdapterPair gsonTypeAdapterPair) {
        if(this.gsonTypeAdapterPairs == null){
            this.gsonTypeAdapterPairs = new ArrayList<GsonTypeAdapterPair>();
        }
        this.gsonTypeAdapterPairs.add(gsonTypeAdapterPair);
    }

    /**
     * 将接口返回的json转换为对象
     * 
     * @Title: loadFromJson
     * @param clazz
     * @return T
     * @date Apr 20, 2014 7:43:53 PM
     */
    public <T> T loadFromJson(Class<T> clazz) {
        initGson();
        try {
            return json != null ? gson.fromJson(json, clazz) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * (non-Javadoc) 当Activity或Fragment销毁时，取消未执行完的task
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        // TODO Auto-generated method stub
        cancel();
    }

    /**
     * 添加http请求参数，所添加参数受http request method[GET|POST]影响
     * 
     * @Title: addParam
     * @param key
     * @param value
     * @return DataTask
     * @date Apr 18, 2014 12:10:50 AM
     */
    public DataTask addParam(String key, String value) {
        if (params == null) {
            params = new ArrayList<Pair<String, String>>();
        }
        params.add(new Pair<String, String>(key, value));
        return this;
    }

    /**
     * 添加http request header
     * 
     * @Title: addHeader
     * @param key
     * @param value
     * @return DataTask
     * @date Apr 18, 2014 12:09:49 AM
     */
    public DataTask addHeader(String key, String value) {
        if (headers == null) {
            headers = new ArrayList<Pair<String, String>>();
        }
        headers.add(new Pair<String, String>(key, value));
        return this;
    }

    /**
     * 添加cookie
     * 
     * @Title: addCookie
     * @param key
     * @param value
     * @return DataTask
     * @date Apr 23, 2014 3:47:13 PM
     */
    public DataTask addCookie(String key, String value) {
        if (cookies == null) {
            cookies = new ArrayList<Pair<String, String>>();
        }
        cookies.add(new Pair<String, String>(key, value));
        return this;
    }
    
    /**
     * 
     * 拼接url，用于既有params，也有postParams
     * @Title: packageUrl
     * @param url
     * @return
     * @return String
     * @date 2014-5-7 下午4:52:55
     */
    public String packageUrl(String url) {
        if(params!=null && params.size() > 0){
            return Util.packageGetUrl(url, params);
        }
        return url;
    }

    /**
     * 设置请求的url
     * 
     * @Title: setUrl
     * @param url
     * @return DataTask
     * @date May 4, 2014 1:48:49 PM
     */
    public DataTask setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置是否http post请求，默认值false
     * 
     * @Title: setMethodPost
     * @param isMethodPost
     * @return DataTask
     * @date Apr 18, 2014 12:14:26 AM
     */
    public DataTask setMethodPost(boolean isMethodPost) {
        this.isMethodPost = isMethodPost;
        return this;
    }

    /**
     * 设置user-agent
     * 
     * @Title: setUserAgent
     * @return DataTask
     * @date Apr 18, 2014 12:30:42 AM
     */
    public DataTask setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * 设置referer
     * 
     * @Title: setReferer
     * @param referer
     * @return DataTask
     * @date Apr 18, 2014 2:03:53 AM
     */
    public DataTask setReferer(String referer) {
        this.referer = referer;
        return this;
    }

    /**
     * 是否需要缓存接口数据
     * 
     * @Title: setNeedCache
     * @param needCache
     * @return DataTask
     * @date May 4, 2014 1:51:49 PM
     */
    public DataTask setNeedCache(boolean needCache) {
        this.needCache = needCache;
        return this;
    }

    /**
     * 设置监听器
     * 
     * @Title: setDataTaskListener
     * @param dataTaskListener
     * @return DataTask
     * @date May 4, 2014 1:52:01 PM
     */
    public DataTask setDataTaskListener(DataTaskListener dataTaskListener) {
        this.dataTaskListener = dataTaskListener;
        return this;
    }

    /**
     * 获得http response code
     * 
     * @Title: getResponseCode
     * @return int
     * @date Apr 18, 2014 1:04:37 AM
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 获得http response header
     * 
     * @Title: getResponseHeaders
     * @return Map<String,List<String>>
     * @date Apr 18, 2014 1:05:01 AM
     */
    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * 获得接口返回数据
     * 
     * @Title: getJson
     * @return String
     * @date Apr 18, 2014 1:23:09 AM
     */
    public String getJson() {
        return json;
    }

    /**
     * 设置自定义doInBackground
     * 
     * @Title: setDoInBackground
     * @param doInBackground
     * @return DataTask
     * @date Apr 25, 2014 4:48:11 PM
     */
    public DataTask setDoInBackground(DataTaskDoInBackground doInBackground) {
        this.doInBackground = doInBackground;
        return this;
    }

    /**
     * 设置是否需要加密接口数据
     * 
     * @Title: setNeedEncrypt
     * @param needEncrypt
     * @return void
     * @date May 4, 2014 1:55:39 PM
     */
    private void setNeedEncrypt(boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
        if (this.needEncrypt) {
            addHeader("Accept-Charset", "UTF-8");
            addHeader("Content-Type", "text/html; charset=utf-8");

            addHeader("Accept-Encoding", "gzip");
            addHeader("Content-Encoding", "gzip");
            addHeader("User-Agent", "Mozilla/4.0");
        }
    }

    /**
     * 设置请求加密对象
     * 
     * @Title: setNeedEncryptObject
     * @param needEncryptObject
     * @return void
     * @date May 4, 2014 1:52:37 PM
     */
    private void setNeedEncryptObject(Object needEncryptObject) {
        this.needEncryptObject = needEncryptObject;
    }

    /**
     * 设置加密类型
     * 
     * @Title: setEncryptType
     * @param encryptType
     * @return void
     * @date May 4, 2014 1:53:15 PM
     */
    private void setEncryptType(EncryptType encryptType) {
        this.encryptType = encryptType;
    }

    /**
     * 设置需要加密的数据和加密类型
     * 
     * @Title: setNeedEncryptObject
     * @param encryptType
     * @param needEncryptObject
     * @return DataTask
     * @date 2014-5-5 上午9:54:20
     */
    public DataTask setNeedEncryptObject(EncryptType encryptType, Object needEncryptObject) {
        setNeedEncrypt(true);
        setEncryptType(encryptType);
        setNeedEncryptObject(needEncryptObject);
        return this;
    }

    private String getEncryptKey() {
        switch (encryptType) {
        default:
            return "";
        }
    }

    /**
     * 设置postParams，优先级高于addParams
     * 
     * @Title: setPostParams
     * @param postParams
     * @return DataTask
     * @date May 4, 2014 5:09:31 PM
     */
    public DataTask setPostParams(String postParams) {
        this.postParams = postParams;
        return this;
    }
    
    /**
     * 需要设置host请求
     * @Title: setNeedSetHost
     * @param host
     * @return void
     * @date May 26, 2014 6:18:01 PM
     */
    public void setNeedSetHost(String host) {
        this.needSetHost = host;
    }
    
    /**
     * 设置请求timeout，默认为{@link DataTask#HTTP_TIMEOUT}
     * @Title: setTimeout
     * @param timeout
     * @return DataTask
     * @date May 28, 2014 11:42:12 AM
     */
    public DataTask setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * 获得请求的输入流，用完需自行调用close方法关闭
     * @Title: getInputStream
     * @return InputStream
     * @date Jun 30, 2014 10:26:13 AM
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 立即执行请求（阻塞）
     * 
     * @Title: request
     * @return DataTask
     * @date Apr 18, 2014 1:57:39 AM
     */
    public DataTask request() {
        responseCode = 0;
        responseHeaders = null;
        json = null;
        
        HttpURLConnection conn = null;
        URL url = null;
        try {
            if (isMethodPost) {
                url = new URL(this.url);
            } else {
                url = new URL(params != null ? Util.packageGetUrl(this.url, params) : this.url);
            }
            if (BuildConfig.DEBUG)
                Log.d("[ DataTask ] --> {}", url.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.setRequestMethod(isMethodPost ? METHOD_POST : METHOD_GET);

            if (!TextUtils.isEmpty(userAgent)) {
                conn.setRequestProperty(HTTP_HEADER_UA, userAgent);
            }

            if (!TextUtils.isEmpty(referer)) {
                conn.setRequestProperty(HTTP_HEADER_REFERER, referer);
            }

            String cookiesString = "";
            boolean needReportException = REQUEST_EXCEOTION_REPORT_URLS.contains(this.url);
            if (cookies != null) {
                cookiesString = Util.joinParams(cookies, Util.JOIN_SIGN_SEMICOLON);
                conn.setRequestProperty(HTTP_HEADER_COOKIE, cookiesString);
            }

            if (!TextUtils.isEmpty(ifModifiedSince)) {
                conn.setRequestProperty(HTTP_HEADER_IF_MODIFIED_SINCE, ifModifiedSince);
            }

            if (headers != null) {
                for (Pair<String, String> header : headers) {
                    conn.setRequestProperty(header.first, header.second);
                }
            }
            byte[] asedata = null;
            byte[] md5byte = null;
            try{//java.net.SocketTimeoutException: failed to connect to wireless.yun.vip.xunlei.com/123.150.185.165 (port 80) after 8000ms
                if (needEncrypt && needEncryptObject != null) {
                    initGson();
                    String jsonReq = gson.toJson(needEncryptObject).toString();
                    byte[] compressdata = PhoneUtil.compress(jsonReq.getBytes(CHARSET));
                    md5byte = PhoneUtil.getMD5Byte(getEncryptKey());
                    //asedata = AESEncryptor.encrypt(md5byte, compressdata);
                    conn.setRequestProperty("Content-Length",
                            Integer.toString(asedata.length));
                    conn.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.write(asedata);
                    out.flush();
                    out.close();
                } else if (isMethodPost && (params != null || this.postParams != null)) {
                    conn.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    String postParams = this.postParams != null ? this.postParams : Util.packagePostParams(params);
                    out.writeBytes(postParams);
                    out.flush();
                    out.close();
                    if (BuildConfig.DEBUG)
                        Log.d("[ DataTask ] --> postParams = {}", postParams);
                }
            }catch(UnknownHostException e){
            }catch(IOException e) {
            }catch(Exception e){
                Log.e(e);
            }

            try{
                responseCode = conn.getResponseCode();
            }catch(UnknownHostException e){
            }catch(IOException e){
            }
            responseHeaders = conn.getHeaderFields();
            
            if(responseCode == HttpStatus.SC_FORBIDDEN && needSetHost != null && !hosted){
                String host = ServerHostManager.getHostIp(needSetHost);
                System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
                addHeader(HTTP_HEADER_HOST, host);
                hosted = true;
                if (BuildConfig.DEBUG)
                    Log.d("[ DataTask ] --> SetHostHeader {} = {}", needSetHost, host);
                return request();
            }
            
            inputStream = conn.getInputStream();
            try {
                if (needEncrypt) {
                    asedata = Util.inputStream2Bytes(inputStream);
                    //asedata = AESEncryptor.decrypt(md5byte, asedata);
                    json = PhoneUtil.decompress(asedata);
                } else {
                    json = Util.inputStream2String(inputStream);
                    json = Util.trimJson(json);
                }
            }catch(IOException e) {//SocketTimeoutException,FileNotFoundException
            }catch(Exception e) {
                Log.e(e);
            }
            
            conn.disconnect();
            if (BuildConfig.DEBUG)
                Log.d("[ DataTask ] <-- request_api = {}, responseCode = {}, json = {}", this.url, responseCode, json);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(conn != null) conn.disconnect();
        }
        return this;
    }

    /**
     * 获取DataTaskDoInBackground返回的数据
     * 
     * @Title: getDoInBackgroundResult
     * @return
     * @return Object
     * @date 2014-4-25 下午6:10:38
     */
    public Object getDoInBackgroundResult() {
        return doingBackgroudTaskResult;
    }

    public interface DataTaskListener {

        void onPreExecute(DataTask dataTask);

        /**
         * @Title: onPostExecute
         * @param responseCode
         *            http响应状态码
         * @param json
         *            接口返回数据
         * @param dataTask
         * @return void
         * @date Apr 20, 2014 7:11:21 PM
         */
        void onPostExecute(int responseCode, String json, DataTask dataTask);

    }

    public interface DataTaskDoInBackground {

        Object doInBackground();

    }

    public long getRequestDuration() {
        long duration = endRequestTime - startRequestTime;
        if (duration > 0) {
            return duration;
        }
        return -1;
    }
    
    public void clear(){
        if(params!=null) params.clear();
        if(headers!=null) headers.clear();
        if(cookies!=null) cookies.clear();
    }
    
    
    /**
     * 需要上报异常请求的接口
     */
    private static final List<String> REQUEST_EXCEOTION_REPORT_URLS;
    static{
        REQUEST_EXCEOTION_REPORT_URLS = new ArrayList<String>();
        REQUEST_EXCEOTION_REPORT_URLS.add("http://wireless.yun.vip.xunlei.com/lottery?encrypt=1");
        REQUEST_EXCEOTION_REPORT_URLS.add("http://wireless.yun.vip.xunlei.com/xlCloudShare?encrypt=1&gzip=1");
    }
}
