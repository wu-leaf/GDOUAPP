package com.dev_fdm.gdouapp.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev_fdm.gdouapp.R;
import com.dev_fdm.gdouapp.adapter.NewsItemAdapter;
import com.dev_fdm.gdouapp.listener.RecyclerItemClickListener;
import com.dev_fdm.gdouapp.model.NewsItem;
import com.dev_fdm.gdouapp.model.NewsItemBiz;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻列表Fragment
 * Created by Dev_fdm on 2015
 */
public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private int mNewsType;
    private NewsItemBiz mNewsItemBiz;
    private NewsItemAdapter mAdapter;
    private static final String ARG_NEWS_TYPE = "newsType";
    private List<NewsItem> mNewsItems = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private TextView footer_text;

    public static NewsListFragment newInstance(int newsType) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NEWS_TYPE, newsType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsItemBiz = new NewsItemBiz();
        if (getArguments() != null) {
            mNewsType = getArguments().getInt(ARG_NEWS_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_news_list, container, false);
        init(rv);
        return rv;
    }

    private void init(final View view) {
        Activity parentActivity = getActivity();

        mSwipeRefreshWidget = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorScheme(R.color.light_blue_600, R.color.green_300, R.color.orange_600);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        //这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //mSwipeRefreshWidget.setRefreshing(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(parentActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewsItemAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        getNewsList(mAdapter, false);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItem = manager.getItemCount();
                    if (lastVisibleItem == (totalItem - 1)) {
                        footer_text = (TextView)view.findViewById(R.id.footer_text);
                        footer_text.setVisibility(View.VISIBLE);
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                        new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        NewsItem item = mAdapter.getmNewsList().get(position);
                        //打开显示新闻内容的Activity,把新闻的url作为参数传过去
                        Intent startActivityIntent = new Intent(getActivity(), NewsDetailActivity.class);

                        //设置专场动画
                        view.setDrawingCacheEnabled(true);
                        view.setPressed(false);
                        view.refreshDrawableState();
                        Bitmap bitmap = view.getDrawingCache();
                        ActivityOptions options = ActivityOptions.makeThumbnailScaleUpAnimation(
                                view, bitmap, 0, 0);

                        Bundle urlBundle = new Bundle();
                        urlBundle.putString("url", item.getLink());
                        startActivityIntent.putExtra("key", urlBundle);
                        urlBundle.putString("date", item.getDate());
                        startActivityIntent.putExtra("key1", urlBundle);
                        urlBundle.putString("readNo", item.getReadNo());
                        startActivityIntent.putExtra("key2", urlBundle);
                        urlBundle.putString("title", item.getTitle());
                        startActivityIntent.putExtra("key3", urlBundle);
                        ActivityCompat.startActivity(getActivity(), startActivityIntent, options.toBundle());

                    }
                })
        );
    }


    private void getNewsList(NewsItemAdapter adapter, boolean forced) {
        int total = mNewsItems.size();
        //不强制刷新时，如果此页已存在则直接从内存中加载
        if (!forced && total > 0) {
            mAdapter.addNews(mNewsItems);
            mAdapter.notifyDataSetChanged();
            return;
        }

        if (forced && mNewsItems.size() > 0) {
            mNewsItems.clear();
        }
        LoadNewsListTask loadDataTask = new LoadNewsListTask(adapter, mNewsType, forced);
        loadDataTask.execute();
    }

    @Override
    public void onRefresh() {
        mNewsItems.clear();
        getNewsList(mAdapter, false);
        Log.i("onRefresh","refresh" );
    }

    class LoadNewsListTask extends AsyncTask<Integer, Integer, List<NewsItem>> {

        private NewsItemAdapter mAdapter;
        private boolean mIsForced;
        private int mNewsType;

        public LoadNewsListTask(NewsItemAdapter adapter, int newsType, boolean forced) {
            super();
            mAdapter = adapter;
            mIsForced = forced;
            mNewsType = newsType;
        }

        /**
         * 得到当前页码的新闻列表
         *
         * @return 当前页码的新闻列表, 出错返回null
         */
        @Override
        protected List<NewsItem> doInBackground(Integer... currentPage) {
            try {
                return mNewsItemBiz.getNewsItems(mNewsType);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("GDOUNET", "neterror :" + e);
                return null;
            }
        }

        /**
         * 得到新闻列表后将其加载
         */
        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            if (newsItems == null) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.connect), Toast.LENGTH_LONG).show();
                return;
            }
            //处理强制刷新
            if (mIsForced) {
                mAdapter.getmNewsList().clear();
            }
            mNewsItems.addAll(newsItems);
            mAdapter.addNews(newsItems);
            mAdapter.notifyDataSetChanged();
            mSwipeRefreshWidget.setRefreshing(false);
            Log.i("onRefresh", "stop_refresh");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        mSwipeRefreshWidget.setRefreshing(true);
        }
    }

}
