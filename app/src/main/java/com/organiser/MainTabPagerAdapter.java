package com.organiser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.organiser.fragment.BaseTabFragment;

import java.util.ArrayList;
import java.util.List;


public class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    private BaseTabFragment[] mFragmentList;
    private String[] mTitleList;

    public MainTabPagerAdapter(FragmentManager fm, BaseTabFragment[] fragments, String[] titleList) {
        super(fm);
        mFragmentList = fragments;
        mTitleList = titleList;
    }

    @Override
    public BaseTabFragment getItem(int position) {
        return mFragmentList[position];
    }

    @Override
    public int getCount() {
        return mFragmentList.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList[position];
    }
}