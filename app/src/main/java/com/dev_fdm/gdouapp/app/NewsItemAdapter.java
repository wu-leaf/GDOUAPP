package com.dev_fdm.gdouapp.app;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev_fdm.gdouapp.R;
import com.dev_fdm.gdouapp.spider.NewsItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表适配器
 * Created by Dev_fdm on 2015/8/2.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private List<NewsItem> mNewsList = new ArrayList<>();

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public List<NewsItem> getmNewsList() {
        return mNewsList;
    }

    public NewsItemAdapter(Context context) {

        this(context, null);
    }

    public NewsItemAdapter(Context context, List<NewsItem> myDataset) {
        mNewsList = myDataset != null ? myDataset : new ArrayList<NewsItem>();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.downloading)
                .showImageOnFail(R.drawable.load_error).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    //添加新闻列表
    public void addNews(List<NewsItem> news) {
        mNewsList.addAll(news);
        Log.i("加载数目", mNewsList.size() + "条");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType==TYPE_ITEM){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);

            ImageView mImg = (ImageView) view.findViewById(R.id.news_item_img);
            TextView mTitle = (TextView) view.findViewById(R.id.news_item_title);
            TextView mRead = (TextView) view.findViewById(R.id.news_item_read);
            TextView mDate = (TextView) view.findViewById(R.id.news_item_date);

            return new ItemViewHolder(view, mImg, mTitle, mRead, mDate);
        }else if (viewType == TYPE_FOOTER ){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footerview, viewGroup, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setClickable(false);
            return new FooterViewHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).mTitle.setText(mNewsList.get(position).getTitle());
            ((ItemViewHolder)holder).mDate.setText(mNewsList.get(position).getDate());
            ((ItemViewHolder)holder).mRead.setText(mNewsList.get(position).getReadNo());

            if (mNewsList.get(position).getImgLink() != null) {
                ((ItemViewHolder)holder).mImg.setVisibility(View.VISIBLE);
                imageLoader.displayImage(mNewsList.get(position).getImgLink(),((ItemViewHolder)holder).mImg, options);
            } else {
                ((ItemViewHolder)holder).mImg.setVisibility(View.GONE);
            }

            ((ItemViewHolder)holder).bindData(mNewsList.get(position));
        }

    }

    @Override
    public int getItemCount() {
//        if (mNewsList != null) {
//            return mNewsList.size()+1;
//        }
//        return 0;
        return mNewsList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View view) {
            super(view);
            view.setClickable(false);
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        private View mView;
        private ImageView mImg;
        private TextView mTitle;
        private TextView mRead;
        private TextView mDate;

//        private NewsItem mNewsItem;

        public ItemViewHolder(View view) {
            super(view);
        }

        public ItemViewHolder(View view, ImageView imageView, TextView titleTextView, TextView readTextView, TextView dateTextView) {
            this(view);
            view.setOnClickListener(this);
            mImg = imageView;
            mTitle = titleTextView;
            mRead = readTextView;
            mDate = dateTextView;
//            mView = view;
        }

        public void bindData(NewsItem newsItem) {

            mTitle.setText(newsItem.getTitle());
            mDate.setText(newsItem.getDate());
            mRead.setText(newsItem.getReadNo());
//            mNewsItem = newsItem;
        }

        @Override
        public void onClick(View v) {
        }
    }

}

