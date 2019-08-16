package com.tone.coast.movie.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tone.coast.movie.R;
import com.tone.coast.movie.model.entity.ChannelEntity;
import com.tone.coast.movie.model.entity.HomeEntity;
import com.tone.coast.movie.model.entity.HomeItemEntity;
import com.tone.coast.movie.model.entity.ListItemEntity;
import com.tone.coast.movie.ui.activity.MainActivity;
import com.tone.coast.movie.ui.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListFragment extends BaseFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ChannelEntity channel;

    private ListAdapter adapter;

    public static ListFragment newInstance(ChannelEntity channel) {
        ListFragment fragment = newInstance();
        fragment.setChannel(channel);
        return fragment;
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    private void setChannel(ChannelEntity channel) {
        this.channel = channel;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void initView(Bundle bundle) {
        super.initView(bundle);


        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

        adapter = new ListAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (((ListItemEntity) adapter.getItems().get(position)).itemType == 0) {
                    return 3;
                }
                if (((ListItemEntity) adapter.getItems().get(position)).itemType == 1) {
                    return 3;
                }
                return 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        if (channel.name.equals("首页")) {
            HomeEntity homeEntity = ((MainActivity) getActivity()).getHomeEntity();
            if (homeEntity != null) {
                List list = new ArrayList();
                list.add(new ListItemEntity<>(0, homeEntity.banners));
                for (HomeItemEntity entity : homeEntity.items) {
                    list.add(new ListItemEntity<>(1, entity));
                }
                setData(list);
            }
        }
    }


    private void setData(List list) {
        adapter.setItems(list);
    }

    @Override
    public void visibleToUser(boolean isVisibleToUser) {
        super.visibleToUser(isVisibleToUser);

    }
}
