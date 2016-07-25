package com.dev_fdm.gdouapp.spider;

/**
 * 获取新闻栏目链接工具类
 * Created by Dev_fdm on 2015.
 */

public class URLUtil {
    public static String GDOU_ZHYW = "http://news.gdou.edu.cn/list.php?catid=18";// 综合要闻
    public static String GDOU_XWGK = "http://news.gdou.edu.cn/list.php?catid=63";// 校务公开
    public static String GDOU_KJDT = "http://news.gdou.edu.cn/list.php?catid=19";// 科教动态
    public static String GDOU_XYKX = "http://news.gdou.edu.cn/list.php?catid=53";// 校园快讯

    public static String generateUrl(int newsType) {
        String urlStr = "";
        switch (newsType) {
            case Constance.GDOU_ZHYW:
                urlStr = GDOU_ZHYW;
                break;
            case Constance.GDOU_XWGK:
                urlStr = GDOU_XWGK;
                break;
            case Constance.GDOU_KJDT:
                urlStr = GDOU_KJDT;
                break;
            default:
                urlStr = GDOU_XYKX;
                break;
        }
        return urlStr;
    }
}