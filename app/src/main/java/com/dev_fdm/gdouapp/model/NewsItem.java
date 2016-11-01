package com.dev_fdm.gdouapp.model;

/**
 *                新闻实体类
 *Created by Dev_fdm on 2015.
 */
public class NewsItem {

    private int id;
    private String link;          //文章链接
    private String imgLink;       // 图片链接
    private String title;         //标题
    private String date;          //发布时间
    private String readNo;        //阅读数
    private String content;       //文章内容
    private int newsType;         //文章类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = "时间:  " + date;
    }

    public String getReadNo() {
        return readNo;
    }

    public void setReadNo(String readNo) {
        this.readNo = "阅读:  " + readNo;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsItem [id：" + id + ", title：" + title + "date：" + date
                + "newsType：" + newsType + "readNo：" + readNo + "link：" + link + "imgLink：" + imgLink + "content：" + content + "]";
    }

}
