package com.organiser.adapter.pagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public MainTabPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public void addTabPagerAdapter(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mTitleList.add(title);
    }

    public MainTabPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleList) {
        super(fm);
        mFragmentList = fragments;
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}