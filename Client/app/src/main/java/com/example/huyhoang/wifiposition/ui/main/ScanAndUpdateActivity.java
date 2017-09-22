package com.example.huyhoang.wifiposition.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.huyhoang.wifiposition.R;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanAndUpdateActivity extends AppCompatActivity {
    private ViewPager vpPosition;
    private ScanAndUpdateAdapter mAdpater;

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
    }
}
