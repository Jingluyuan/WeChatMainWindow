package com.wechat.mainwindow;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private String[] mTitles = new String[]
            {"First Fragment","Second Fragment","Third Fragment","Fourth Fragment"};
    private FragmentPagerAdapter mAdapter;

    private List<ChangeColorWithText> mTabIndicators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowButtonAlways();
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        initView();
        initDatas();
        mViewPager.setAdapter(mAdapter);
        initEvent();



    }

    private void initEvent()
    {
        mViewPager.setOnPageChangeListener(this);
    }

    private void initDatas() {
        for (String title : mTitles)
        {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE,title);
            tabFragment.setArguments(bundle);
            mTabs.add(tabFragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);

        ChangeColorWithText one = findViewById(R.id.indicator_message);
        mTabIndicators.add(one);
        ChangeColorWithText two = findViewById(R.id.indicator_contect);
        mTabIndicators.add(two);
        ChangeColorWithText three = findViewById(R.id.indicator_show);
        mTabIndicators.add(three);
        ChangeColorWithText four = findViewById(R.id.indicator_me);
        mTabIndicators.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void setOverflowButtonAlways()
    {

        try {
            ViewConfiguration configuration = ViewConfiguration.get(this);
            Field field = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            field.setAccessible(true);
            field.setBoolean(configuration,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR&&menu!=null)
        {
            if(menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu,true);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu!=null)
        {
            if(menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu,true);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch (v.getId())
        {
            case R.id.indicator_message:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.indicator_contect:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.indicator_show:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.indicator_me:
                mTabIndicators.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3,false);
                break;
        }

    }

    private void resetOtherTabs() {
        for (int i=0;i<mTabIndicators.size();i++)
        {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset>0)
        {
            ChangeColorWithText left = mTabIndicators.get(position);
            ChangeColorWithText right = mTabIndicators.get(position+1);

            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
