package com.youshibi.app.presentation.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.youshibi.app.R;
import com.youshibi.app.mvp.MvpActivity;
import com.youshibi.app.util.ToastUtil;

/**
 * Created by Chu on 2016/11/30.
 */

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View, OnTabSelectListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        getPresenter().start();
        getPresenter().initContentContainer(getSupportFragmentManager(), R.id.content_view);
        bottomBar.setOnTabSelectListener(this);
    }

    @NonNull
    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
        getPresenter().dispatchTabSelectedTabId(tabId);

    }

    @Override
    protected boolean isEnableSlideFinish() {
        return false;
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isCountingPage() {
        return false;
    }

    @Override
    public void switchBookcase() {
        bottomBar.setCanUpdateShy(false);
    }

    @Override
    public void switchExplore() {
        bottomBar.setCanUpdateShy(true);
    }

    @Override
    public void switchMine() {
        bottomBar.setCanUpdateShy(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return getPresenter().dispatchTabSelectedTabId(item.getItemId());
    }
}
