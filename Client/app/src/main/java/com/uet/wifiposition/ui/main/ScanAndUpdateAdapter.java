package com.uet.wifiposition.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.uet.wifiposition.ui.main.publicwifiinfo.PublicWifiInfoFragment;
import com.uet.wifiposition.ui.main.scanwifi.ScanWifiInfoFragment;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanAndUpdateAdapter extends FragmentPagerAdapter {
    private final ScanWifiInfoFragment fragment1;
    private final PublicWifiInfoFragment fragment2;

    public ScanAndUpdateAdapter(FragmentManager fm) {
        super(fm);
        fragment1 = new ScanWifiInfoFragment();
        fragment2 = new PublicWifiInfoFragment();
        fragment2.setInterf(fragment1);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return fragment1;
        } else {
            return fragment2;
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
