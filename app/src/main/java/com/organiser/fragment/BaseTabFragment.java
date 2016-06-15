package com.organiser.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.organiser.activity.MainActivity;
import com.organiser.interfaces.ITabNavigation;

public class BaseTabFragment extends Fragment {

    protected ITabNavigation mTabNavigationInterface;

    public String getTitle() {
        return "";
    }

    public void refresh() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mTabNavigationInterface = (ITabNavigation) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTabNavigationInterface = null;
    }
}
