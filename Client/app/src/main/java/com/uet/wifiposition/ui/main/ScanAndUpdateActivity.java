package com.uet.wifiposition.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.uet.wifiposition.R;
import com.uet.wifiposition.remote.interact.main.Interactor;
import com.uet.wifiposition.remote.model.WifiInfoModel;
import com.uet.wifiposition.remote.model.getbuilding.InfoReferencePointInput;
import com.uet.wifiposition.remote.model.getposition.GetLocationResponse;
import com.uet.wifiposition.ui.base.BaseActivity;
import com.uet.wifiposition.ui.base.BaseMvpActivity;
import com.uet.wifiposition.ui.main.publicwifiinfo.PublicWifiInfoFragment;
import com.uet.wifiposition.ui.main.scanwifi.ScanWifiInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 9/22/17.
 */

public class ScanAndUpdateActivity extends BaseMvpActivity<ScanAndUpdateContract.Presenter> implements ViewPager.OnPageChangeListener, View.OnClickListener, ScanAndUpdateContract.View {
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

        findViewById(R.id.btn_location).setOnClickListener(this);
    }

    @Override
    public void initComponents() {
        Interactor.getInstance().init();
        setRootView(findViewById(R.id.view_root));
        mAdpater = new ScanAndUpdateAdapter(getSupportFragmentManager());
        vpPosition.setAdapter(mAdpater);
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(vpPosition);

        presenter = new ScanAndUpdatePresenter(this);


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
                ((ScanWifiInfoFragment) fragment).reload();
                break;
            case R.id.btn_location:
                ScanWifiInfoFragment scan = (ScanWifiInfoFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp_position + ":" + 0);
                List<WifiInfoModel> wifiInfoModels = scan.getListWifiInfoChoose();
                if (wifiInfoModels == null || wifiInfoModels.size() == 0) {
                    showMessage(R.string.Loading);
                    return;
                }
                PublicWifiInfoFragment publicInfo = (PublicWifiInfoFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp_position + ":" + 1);
                int buildingId = publicInfo.getBuildingId();
                int roomId = publicInfo.getRoomId();
                if (buildingId == -1 || roomId == -1) {
                    showMessage(R.string.Loading);
                    return;
                }
                List<InfoReferencePointInput> infoReferencePointInputs = new ArrayList<>();
                for (WifiInfoModel wifiInfoModel : wifiInfoModels) {
                    infoReferencePointInputs.add(new InfoReferencePointInput(wifiInfoModel.getMacAddress(), wifiInfoModel.getName(), wifiInfoModel.getLevel()));
                }
                presenter.getLocation(buildingId, roomId,infoReferencePointInputs );
                break;
            default:
                break;
        }
    }

    @Override
    public void finishGetLocaiton(GetLocationResponse response) {
        showMessage("x: " + response.getLocationModel().getX() + ", y: " + response.getLocationModel().getY());
    }

    @Override
    public void errorGetLocation(Throwable error) {
        showMessage(error.getMessage());
    }
}
