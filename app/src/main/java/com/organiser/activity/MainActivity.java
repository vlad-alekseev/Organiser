package com.organiser.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.organiser.Constants;
import com.organiser.MainTabPagerAdapter;
import com.organiser.fragment.BaseTabFragment;
import com.organiser.fragment.RemainderFragment;
import com.organiser.fragment.WeatherFragment;
import com.organiser.R;
import com.organiser.fragment.CalendarFragment;
import com.organiser.interfaces.ITabNavigation;


public class MainActivity extends AppCompatActivity implements ITabNavigation {

    private MainTabPagerAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        String[] titleArray = new String[]{getString(R.string.calendar), getString(R.string.forecast)};
        BaseTabFragment[] baseTabFragmentsArray = new BaseTabFragment[]{new CalendarFragment(), new WeatherFragment()};
        mTabAdapter = new MainTabPagerAdapter(getSupportFragmentManager(), baseTabFragmentsArray, titleArray);
        viewPager.setAdapter(mTabAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void openReminderFragment(String date) {
        Bundle bundles = new Bundle();
        bundles.putString(Constants.KEY_CURRENT_DATE, date);
        RemainderFragment fragment = new RemainderFragment();
        fragment.setArguments(bundles);

        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.custom_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void refreshWeatherFragment() {
        if (mTabAdapter != null) {
            BaseTabFragment frgm = mTabAdapter.getItem(0);
            if (frgm != null)
                frgm.refresh();
        }
    }
}