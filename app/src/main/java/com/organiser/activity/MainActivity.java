package com.organiser.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.organiser.adapter.pagerAdapters.MainTabPagerAdapter;
import com.organiser.fragment.WeatherFragment;
import com.organiser.R;
import com.organiser.fragment.CalendarFragment;


public class MainActivity extends AppCompatActivity{

    private MainTabPagerAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mTabAdapter = new MainTabPagerAdapter(getSupportFragmentManager());
        mTabAdapter.addTabPagerAdapter(new CalendarFragment(), getString(R.string.calendar));
        mTabAdapter.addTabPagerAdapter(new WeatherFragment(), getString(R.string.forecast));
        viewPager.setAdapter(mTabAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

}