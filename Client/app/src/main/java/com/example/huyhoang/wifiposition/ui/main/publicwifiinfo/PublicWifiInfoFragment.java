package com.example.huyhoang.wifiposition.ui.main.publicwifiinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huyhoang.wifiposition.R;

/**
 * Created by ducnd on 9/22/17.
 */

public class PublicWifiInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public_wifi_info, container, false);
        return view;
    }
}
