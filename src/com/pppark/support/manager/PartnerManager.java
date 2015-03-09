package com.pppark.support.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import com.pppark.framework.logging.Logger;

import android.content.Context;


// TODO: 检查与Core项目重复
public class PartnerManager {
    private static final Logger LOG = Logger.getLogger(PartnerManager.class);

    public static int partnerID = -1;
    public static String partnerName = null;
    
    public static int getPartnerID(Context ctx){
        if(partnerID == -1){
            fetchPartnerId(ctx);
        }
        return partnerID;
    }
    
    public static String getPartnerName(Context ctx){
        if(partnerID == -1){
            fetchPartnerId(ctx);
        }
        return partnerName;
    }

    public static void fetchPartnerId(Context ctx) {
        String fileName = "appconfig/PartnerInfo.txt";
        InputStream in = null;
        BufferedReader br = null;

        try {
            in = ctx.getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(in));
            String firstLine = br.readLine();
            String[] items = firstLine.split(",");
            if (items != null && items.length == 2) {
                partnerName = items[0];
                if (items[1] != null && !items[1].equals("")) {
                    String hexString = items[1].toUpperCase(Locale.US);
                    hexString = hexString.substring(hexString.indexOf("0X") + 2);
                    partnerID = Integer.parseInt(hexString, 16);
                }
            }
        } catch (IOException e) {
            LOG.warn(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.warn(e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.warn(e);
                }
            }
        }
    }
}
