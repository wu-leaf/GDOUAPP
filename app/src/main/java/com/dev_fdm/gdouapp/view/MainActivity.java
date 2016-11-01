package com.dev_fdm.gdouapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev_fdm.gdouapp.R;
import com.dev_fdm.gdouapp.spider_utils.Constance;

import java.util.ArrayList;
import java.util.List;

/**
 * 广东海洋大学新闻客户端
 * Created by Dev_fdm on 2015/8/3.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);//加上 左上角的  三横  图片
        ab.setDisplayHomeAsUpEnabled(true);         //设置允许显示

        //整个主布局 由左侧侧滑菜单+中间TabLayout+ViewPager组成
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //NavigationView是左侧侧滑菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);//设置左侧导航抽屉
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);         //设置ViewPager
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);  //获取TabLayout
        assert viewPager != null;
        tabLayout.setupWithViewPager(viewPager);//根据viewPager设置tablayout
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_about:
                Intent intent_about = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent_about);
                return true;
            case R.id.action_settings:
                Intent intent_settings = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent_settings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());

        adapter.addFragment(NewsListFragment.newInstance(Constance.GDOU_ZHYW), "综合要闻");
        adapter.addFragment(NewsListFragment.newInstance(Constance.GDOU_KJDT), "科教动态");
        adapter.addFragment(NewsListFragment.newInstance(Constance.GDOU_XWGK), "校务公开");
        adapter.addFragment(NewsListFragment.newInstance(Constance.GDOU_XYKX), "校园快讯");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.nav_home:
                                mDrawerLayout.closeDrawers();
                                return true;
                            case R.id.action_settings:
                                Intent intent_settings = new Intent(MainActivity.this,SettingsActivity.class);
                                startActivity(intent_settings);
                                return true;
                            case R.id.action_about:
                                Intent intent_about = new Intent(MainActivity.this,AboutActivity.class);
                                startActivity(intent_about);
                                return true;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);//设置Fragment的标签
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}