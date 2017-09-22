package com.example.huyhoang.wifiposition.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.huyhoang.wifiposition.ui.main.publicwifiinfo.PublicWifiInfoFragment;
import com.example.huyhoang.wifiposition.ui.main.scanwifi.ScanWifiInfoFragment;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanAndUpdateAdapter extends FragmentPagerAdapter {
    public ScanAndUpdateAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ScanWifiInfoFragment();
        } else {
            return new PublicWifiInfoFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Wifi info";
        } else {
            return "Update database";
        }
    }
}
