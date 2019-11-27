package com.example.fisrtapplication.adapters;

import android.content.Context;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.fragments.BlankFragment;
import com.example.fisrtapplication.fragments.DataListFragment;
import com.example.fisrtapplication.fragments.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public PagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case (0):
                return new DataListFragment();
            case (1):
                return new BlankFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case (0):
                return mContext.getString(R.string.vendings);
            case (1):
                return mContext.getString(R.string.blank);
            default:
                return mContext.getString(R.string.profile);
        }
    }
}
