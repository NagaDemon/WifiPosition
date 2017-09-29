package com.uet.wifiposition.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.uet.wifiposition.R;
import com.uet.wifiposition.ui.base.BaseActivity;
import com.uet.wifiposition.ui.main.scanwifi.ScanWifiInfoFragment;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanAndUpdateActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager vpPosition;
    private ScanAndUpdateAdapter mAdpater;
    private FloatingActionButton btnReload;

    @Override
    public int getLayoutMain() {
        return R.layout.activity_scan_and_update;
    }

    @Override
    public void findViewByIds() {
        btnReload = (FloatingActionButton) findViewById(R.id.btn_reload);
        vpPosition = (ViewPager) findViewById(R.id.vp_position);
    }

    @Override
    public void setEvents() {
        vpPosition.addOnPageChangeListener(this);

        btnReload.setOnClickListener(this);
    }

    @Override
    public void initComponents() {
        setRootView(findViewById(R.id.view_root));
        mAdpater = new ScanAndUpdateAdapter(getSupportFragmentManager());
        vpPosition.setAdapter(mAdpater);
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(vpPosition);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            btnReload.show();
        } else {
            btnReload.hide();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reload:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp_position + ":" + 0);
                ((ScanWifiInfoFragment)fragment).reload();
                break;
            default:
                break;
        }
    }
}
