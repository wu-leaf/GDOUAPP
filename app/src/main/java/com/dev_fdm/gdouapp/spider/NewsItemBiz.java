package com.dev_fdm.gdouapp.spider;

import com.dev_fdm.gdouapp.app.GetSettings;
import com.dev_fdm.gdouapp.app.MyApplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理NewItem的业务类
 * Created by Dev_fdm on 2015.
 */
public class NewsItemBiz {
    /**
     * 综合要闻、校务公开、科教动态、校园快讯
     */
    public List<NewsItem> getNewsItems(int newsType)
            throws CommonException {
        String urlStr = URLUtil.generateUrl(newsType);

        String htmlStr = DataUtil.doGet(urlStr);

        List<NewsItem> newsItems = new ArrayList<>();
        NewsItem newsItem = null;

        Document doc = Jsoup.parse(htmlStr);
        Elements ai = doc.select("div.mar_10");
        Elements units = ai.select("li");
        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);

            Element unit_ele = units.get(i);
            // 获取新闻发布时间
            String date = unit_ele.getElementsByTag("span").text();
            newsItem.setDate(date);

            //获取新闻当前阅读数
            String readNo = unit_ele.getElementsByTag("font").text();
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(readNo);
            String number = m.replaceAll("").trim();
            newsItem.setReadNo(number);

            // 获取新闻标题及文章链接
            Element newstl = unit_ele.select("a[href]").get(0);
            String title = newstl.text();
            String href = newstl.attr("abs:href");

            newsItem.setTitle(title);
            newsItem.setLink(href);

            // 获取新闻图片链接
            Document doc1 = null;

            boolean option = GetSettings.getCheckboxall(MyApplication.getContext());
            boolean network = NetWorkUtil.IsNetAvailable(MyApplication.getContext());

            if (network != option) {
                try {
                    doc1 = Jsoup.connect(href).get();
                    Elements img = doc1.select("div#endtext img[src]");
                    String imgLink = img.attr("abs:src");
                    if (imgLink.equals("")) {
                        imgLink = null;
                    }
                    newsItem.setImgLink(imgLink);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                newsItem.setImgLink(null);
            }

            newsItems.add(newsItem);
        }


        return newsItems;

    }


    public NewsItem getNewsContent(String url) throws Exception {

        NewsItem newsItem = new NewsItem();

        // 获取新闻图片链接
        Document doc1 = null;
        try {
            doc1 = Jsoup.connect(url).get();
            Elements img = doc1.select("div#endtext img[src]");
            String imgLink = img.attr("abs:src");
            if (imgLink.equals("")) {
                imgLink = null;
            }
            newsItem.setImgLink(imgLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取正文内容
        assert doc1 != null;
        Elements article = doc1.select("div#endtext");
        Elements articles = article.select("p");
        String content = "";
        for (Element content_ele : articles) {
            content += ("\u3000\u3000" + content_ele.text() + "\n\n");
            newsItem.setContent(content);
        }
        return newsItem;
    }

}
