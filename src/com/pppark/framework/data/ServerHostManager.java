package com.pppark.framework.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;

public class ServerHostManager {

    private static final String SERVERHOST_CONFIG = "http://dns.jsq.gigaget.com/dns?domain=";

    private static Map<String, String> hostMaps = new HashMap<String, String>();

    public static boolean isInit = false;

    public static String getHostIp(String host) {
        if (hostMaps.containsKey(host)) {
            return hostMaps.get(host);
        } else {
            String ip = getServerHostIp(host);
            hostMaps.put(host, ip);
            return ip;
        }
    }

    public static String getServerHostIp(String host) {
        DataTask dt = new DataTask();
        dt.setUrl(SERVERHOST_CONFIG + host);
        if (dt.request().getResponseCode() == HttpStatus.SC_OK) {
            try {
                JSONArray jsonArray = new JSONArray(dt.getJson());
                if (jsonArray != null && jsonArray.length() > 0) {
                    int size = jsonArray.length();
                    Random r = new Random();
                    String ip = jsonArray.getString(r.nextInt(size));
                    return ip;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
        
        
//        HttpGet request = new HttpGet(SERVERHOST_CONFIG + host);
//        BasicHttpParams httpParams = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
//        HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
//        HttpClient httpclient = new DefaultHttpClient(httpParams);
//        HttpResponse httpResponse = null;
//        try {
//            httpResponse = httpclient.execute(request);
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (httpResponse == null) {
//            return null;
//        }
//        int response_code = httpResponse.getStatusLine().getStatusCode();
//        String strResult = null;
//        if (response_code == HttpStatus.SC_OK) {
//            try {
//                strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//                JSONArray jsonArray = new JSONArray(strResult);
//                if (jsonArray != null && jsonArray.length() > 0) {
//                    int size = jsonArray.length();
//                    Random r = new Random();
//                    String ip = jsonArray.getString(r.nextInt(size));
//                    return ip;
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

}
