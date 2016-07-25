package com.dev_fdm.gdouapp.spider;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 执行网络访问，模拟浏览器访问请求服务器资源
 * Created by Dev_fdm on 2015.
 */

public class DataUtil {
    public DataUtil() {
    }

    public static String doGet(String urlStr) throws CommonException {
        StringBuffer sb = new StringBuffer();

        try {
            URL e = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) e.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
            if (conn.getResponseCode() != 200) {
                throw new CommonException("访问网络失败！");
            } else {
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "gbk");
                boolean len = false;
                char[] buf = new char[1024];

                int len1;
                while ((len1 = isr.read(buf)) != -1) {
                    sb.append(new String(buf, 0, len1));
                }

                is.close();
                isr.close();
                conn.disconnect();
                return sb.toString();
            }
        } catch (Exception var8) {
            throw new CommonException("访问网络失败！");
        }
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();

        for (int i = 0; i < c.length; ++i) {
            if (c[i] == 12288) {
                c[i] = 32;
            } else if (c[i] > '\uff00' && c[i] < '｟') {
                c[i] -= 'ﻠ';
            }
        }

        return new String(c);
    }
}

