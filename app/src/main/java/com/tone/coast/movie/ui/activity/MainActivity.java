package com.tone.coast.movie.ui.activity;

import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tone.coast.movie.R;
import com.tone.coast.movie.model.entity.ChannelEntity;
import com.tone.coast.movie.model.entity.DataResult;
import com.tone.coast.movie.model.entity.HomeEntity;
import com.tone.coast.movie.ui.adapter.ChannelPagerAdapter;
import com.tone.coast.movie.util.DataProvide;
import com.tone.coast.movie.util.StatusBarUtil;
import com.tone.coast.movie.util.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private ChannelPagerAdapter adapter;

    private HomeEntity homeEntity;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        StatusBarUtil.setGradientColor(this, toolbar);
        toolbar.inflateMenu(R.menu.menu_search);

//        tabLayout.addTab(tabLayout.newTab().setText("电影"));
//        tabLayout.addTab(tabLayout.newTab().setText("首页"));
//        tabLayout.addTab(tabLayout.newTab().setText("电视剧"));
//        tabLayout.addTab(tabLayout.newTab().setText("专题"));


        adapter = new ChannelPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        loadData();
    }


    private void loadData() {
        DataProvide.getHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.<DataResult<HomeEntity>>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Consumer<DataResult<HomeEntity>>() {
                    @Override
                    public void accept(DataResult<HomeEntity> result) throws Exception {
                        if (result.code == 200) {
                            homeEntity = result.data;
                            List<ChannelEntity> channels = result.data.channels;
                            if (channels != null) {
                                adapter.setChannelList(channels);
                            }
                        } else {
                            ToastUtil.getInstance().showToast(result.msg);
                        }
                    }
                });
    }


    public HomeEntity getHomeEntity() {
        return homeEntity;
    }
}
