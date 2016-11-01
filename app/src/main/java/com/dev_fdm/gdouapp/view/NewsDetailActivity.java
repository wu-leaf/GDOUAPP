package com.dev_fdm.gdouapp.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dev_fdm.gdouapp.R;
import com.dev_fdm.gdouapp.model.NewsItem;
import com.dev_fdm.gdouapp.model.NewsItemBiz;

/**
 * 新闻详细内容
 * Created by Dev_fdm on 2015
 */
public class NewsDetailActivity extends AppCompatActivity {

    private String mNewsUrl;   //传过来新闻的URL
    private String date;
    private String readNo;
    private String title;
    private NewsItemBiz mNewsItemBiz;
    private NewsItem mNewsItem;
    private TextView detail_title; //文章标题
    private TextView detail_read; //文章阅读数
    private TextView detail_date; //文章时间
    private ImageView detail_img; //文章图片
    private TextView detail_article; //文章内容
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        init();
        mNewsItemBiz = new NewsItemBiz();
        mNewsUrl = this.getIntent().getBundleExtra("key").getString("url");
        date = this.getIntent().getBundleExtra("key1").getString("date");
        readNo = this.getIntent().getBundleExtra("key2").getString("readNo");
        title = this.getIntent().getBundleExtra("key3").getString("title");
        InitNewsTask initNewsTask = new InitNewsTask();
        initNewsTask.execute(mNewsUrl);
    }

    public void init() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_read = (TextView) findViewById(R.id.detail_read);
        detail_date = (TextView) findViewById(R.id.detail_date);
        detail_img = (ImageView) findViewById(R.id.detail_img);
        detail_article = (TextView) findViewById(R.id.detail_article);

    }

    class InitNewsTask extends AsyncTask<String, Integer, NewsItem> {

        @Override
        protected NewsItem doInBackground(String... params) {
            try {
                return mNewsItemBiz.getNewsContent(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("ASD", "Content错误： " + e);
                return null;
            }
        }

        /**
         * 得到新闻内容后将其加载
         *
         * @param newsItem 得到的新闻内容
         */
        @Override
        protected void onPostExecute(NewsItem newsItem) {
            if (newsItem == null) {
                Toast.makeText(NewsDetailActivity.this, getResources().getString(R.string.connect), Toast.LENGTH_LONG).show();
                return;
            }
            //处理信息缓存
            mNewsItem = newsItem;
            //将内容载入界面
            detail_title.setText(title);
            detail_date.setText(date);
            detail_read.setText(readNo);
            detail_article.setText(newsItem.getContent());
            if (newsItem.getImgLink() == null) {
                detail_img.setVisibility(View.GONE);
            } else {
                Glide.with(NewsDetailActivity.this)
                        .load(newsItem.getImgLink())
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .into(detail_img);
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_about:
                Intent intent = new Intent(NewsDetailActivity.this,AboutActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_actions, menu);
        return true;
    }
}
