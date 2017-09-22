package com.example.huyhoang.wifiposition.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.huyhoang.wifiposition.R;
import com.example.huyhoang.wifiposition.ui.main.scanwifi.ScanWifiInfoFragment;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanAndUpdateActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager vpPosition;
    private ScanAndUpdateAdapter mAdpater;
    private FloatingActionButton btnReload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_and_update);
        initViews();
    }

    private void initViews() {
        vpPosition = (ViewPager) findViewById(R.id.vp_position);
        mAdpater = new ScanAndUpdateAdapter(getSupportFragmentManager());
        vpPosition.setAdapter(mAdpater);
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(vpPosition);

        btnReload = (FloatingActionButton) findViewById(R.id.btn_reload);
        vpPosition.addOnPageChangeListener(this);

        btnReload.setOnClickListener(this);

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
