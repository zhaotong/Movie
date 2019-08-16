package com.tone.coast.movie.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tone.coast.movie.model.entity.ChannelEntity;

import java.util.ArrayList;
import java.util.List;


public class ChannelPagerAdapter extends StatePagerAdapter<ChannelEntity> {
    protected List<ChannelEntity> channelList = new ArrayList<>();

    protected Context context;

    public ChannelPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    public void setChannelList(List<ChannelEntity> channelList) {
        //使用 addAll
        //PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged!
        this.channelList.clear();
        this.channelList.addAll(channelList);
        notifyDataSetChanged();
    }


    public List<ChannelEntity> getDataList() {
        return channelList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= getCount())
            return "";
        ChannelEntity entity = channelList.get(position);
        return entity.name;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= getCount())
            return null;
        return FragmentTypeHelper.getFragment(channelList.get(position));
    }

    @Override
    public ChannelEntity getItemData(int position) {
        if (position >= getCount())
            return null;
        return channelList.get(position);
    }

    @Override
    public boolean dataEquals(ChannelEntity oldData, ChannelEntity newData) {
        if (oldData == null || newData == null)
            return false;
        return oldData.equals(newData);
    }

    @Override
    public int getDataPosition(ChannelEntity data) {
        return channelList.indexOf(data);
    }

    @Override
    public int getCount() {
        return channelList.size();
    }


}
