package com.example.huyhoang.wifiposition.ui.main.scanwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.huyhoang.wifiposition.R;
import com.example.huyhoang.wifiposition.remote.model.WifiInfoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanWifiInfoFragment extends Fragment implements ScanWifiInfoAdapter.IScanWifiInfoAdapter, View.OnClickListener {

    private static final String TAG = ScanWifiInfoFragment.class.getSimpleName();
    private ScanWifiInfoAdapter mAdapter;
    private List<WifiInfoModel> mWifiInfoModels;
    private ProgressBar progress;
    private ScanBroadCast mBroadcast;
    private WifiManager wifiManager;
    private Disposable mDisposeScan;
    private boolean isClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_wifi_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        initData();
        initViews();
        initBroadcast();
        setEvents();
    }

    private void initData() {
        mWifiInfoModels = new ArrayList<>();
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    private void initViews() {
        RecyclerView rcWifi = (RecyclerView) getView().findViewById(R.id.rc_wifi_info);
        rcWifi.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ScanWifiInfoAdapter(this);
        rcWifi.setAdapter(mAdapter);
    }

    private void initBroadcast() {
        mBroadcast = new ScanBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getContext().registerReceiver(mBroadcast, filter);
    }

    private void setEvents() {
//        getView().findViewById(R.id.btn_reload).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        wifiManager.setWifiEnabled(true);
        mDisposeScan = Observable.just("scan")
                .repeatWhen(delay -> delay.delay(15, TimeUnit.SECONDS))
                .subscribe(response -> {
                    wifiManager.startScan();
                });

    }

    @Override
    public void onPause() {
        mDisposeScan.dispose();
        wifiManager.setWifiEnabled(false);
        super.onPause();
    }

    @Override
    public int getCount() {
        if (mWifiInfoModels.size() == 0) {
            return 0;
        }
        return mWifiInfoModels.size() + 1;
    }

    @Override
    public WifiInfoModel getData(int position) {
        return mWifiInfoModels.get(position);
    }

    private class ScanBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getScanWifi();
        }
    }

    private void getScanWifi() {
        Observable.create((ObservableOnSubscribe<List<WifiInfoModel>>) emitter -> {
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (scanResults != null) {
                if (isClear) {
                    mWifiInfoModels.clear();
                }
                List<WifiInfoModel> newList = new ArrayList<>();
                for (ScanResult scanResult : scanResults) {
                    if (isClear) {
                        WifiInfoModel model = new WifiInfoModel();
                        model.setCount(1);
                        model.setLevel(scanResult.level);
                        model.setFrequency(scanResult.frequency);
                        model.setMacAddress(scanResult.BSSID);
                        model.setName(scanResult.SSID);
                        newList.add(model);
                    } else {
                        boolean isSame = false;
                        for (WifiInfoModel wifiInfoModel : mWifiInfoModels) {
                            if (wifiInfoModel.getMacAddress().equals(scanResult.BSSID)) {
                                wifiInfoModel.setLevel(wifiInfoModel.getLevel() * 0.9f + scanResult.level * .01f);
                                wifiInfoModel.setCount(wifiInfoModel.getCount() + 1);
                                isSame = true;
                                break;
                            }
                        }
                        if (!isSame) {
                            WifiInfoModel model = new WifiInfoModel();
                            model.setCount(1);
                            model.setLevel(scanResult.level);
                            model.setFrequency(scanResult.frequency);
                            model.setMacAddress(scanResult.BSSID);
                            model.setName(scanResult.SSID);
                            newList.add(model);
                        }
                    }

                }
                isClear = false;
                emitter.onNext(newList);

            } else {
                Log.d(TAG, "clickWifi null.......");
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progress.setVisibility(View.GONE);
                    mWifiInfoModels.addAll(response);
                    mAdapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);


    }

    @Override
    public void onClick(View v) {

    }

    public void reload(){
        isClear = true;
        progress.setVisibility(View.VISIBLE);
    }
}
