package com.dev_fdm.gdouapp.spider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接工具类
 * Created by Bruce on 2015
 */
public class NetWorkUtil {

    public static boolean IsNetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.getTypeName().equals( "WIFI"));
    }
}
