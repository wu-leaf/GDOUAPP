package com.dev_fdm.gdouapp.app;

import android.content.Context;
import android.content.SharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * 获取设置信息
 * Created by Bruce on 2015/11/25.
 */
public class GetSettings {

    public static boolean getCheckboxall(Context context){

        SharedPreferences settings = getDefaultSharedPreferences(context);
         settings.getBoolean("checkbox_all",false);
        return (settings.getBoolean("checkbox_all",false));
    }

    public static boolean getCheckboxpart(Context context){

        SharedPreferences settings = getDefaultSharedPreferences(context);
        settings.getBoolean("checkbox_part",true);
        return (settings.getBoolean("checkbox_part",true));
    }
}
